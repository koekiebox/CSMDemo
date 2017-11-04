package com.backbase.csmdemo.application.impl;

import com.backbase.csmdemo.application.ICSMApp;
import com.backbase.csmdemo.dao.DAOFactory;
import com.backbase.csmdemo.dao.IATMDAO;
import com.backbase.csmdemo.exception.CMSException;
import com.backbase.csmdemo.model.ATM;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The CSM application used for business logic functions.
 *
 * @author jasonbruwer on 11/3/17.
 * @since 1.0
 */
public class CSMApp implements ICSMApp {

    //We only support ING for now, but we can add more in the future.
    private List<IATMDAO> allAtmDaos;

    private long lastFetch = 0;

    //Ideally one would make use of a caching system like;
    //MemCached or Redis...but for now we will use a local static Map.
    private static Map<String, List<ATM>> atmToCityMapping = new HashMap<>();
    private static Map<String, String> atmToCityProperFormatMapping = new HashMap<>();

    protected Logger logger = Logger.getLogger(this.getClass().getName());

    private static final int FETCH_ATM_LIST_EVERY_HOURS = 2;

    /**
     * Constructor to all for testing.
     *
     * @param isInMockModeParam Should the DAO be mocked for now.
     */
    public CSMApp(boolean isInMockModeParam) {
        super();

        if(isInMockModeParam)
        {
            this.allAtmDaos = DAOFactory.getSpecificATMDAOs(DAOFactory.ATMType.Test);
        }
        else
        {
            this.allAtmDaos = DAOFactory.getAllATMDAOs();
        }

        this.lastFetch = 0;
    }

    /**
     * New instance
     */
    public CSMApp() {
        this(false);
    }

    /**
     * Retrieve all the ATMs for {@code cityParam}
     *
     * @param cityParam The city for which to retrieve ATMs.
     *                  
     * @return Listing of ATMs.
     */
    @Override
    public List<ATM> getListOfATMsForCity(String cityParam) {

        if(cityParam == null || cityParam.trim().isEmpty())
        {
            return null;
        }

        //Should the ATM listing be refreshed...
        if(this.shouldRefreshATMListing())
        {
            //Get the list...
            this.refreshATMListing();
        }

        String key = cityParam.trim().toUpperCase();
        
        return CSMApp.atmToCityMapping.get(key);
    }

    /**
     * Retrieve all the ATMs from all possible sources.
     *
     * @return Listing of ATMs.
     */
    @Override
    public List<ATM> getCompleteListOfATMs() {

        //Should the ATM listing be refreshed...
        if(this.shouldRefreshATMListing())
        {
            //Get the list...
            this.refreshATMListing();
        }

        List<ATM> returnVal = new ArrayList<>();

        CSMApp.atmToCityMapping.values().forEach(
                toAdd -> {
                    returnVal.addAll(toAdd);
                }
            );

        //Sort the ATMs...
        Collections.sort(
                returnVal,
                new SortATMsByCityComparator());
        
        return returnVal;
    }

    /**
     * Retrieve all the ATM cities where the city name {@code cityLookupParam}.
     *
     * If the name is empty, all the cities will be returned.
     *
     * @param cityLookupParam The lookup text to use.
     *
     * @return Listing of Cities.
     */
    @Override
    public List<String> getUniqueCityNamesWhereContains(String cityLookupParam) {

        //Should the ATM listing be refreshed...
        if(this.shouldRefreshATMListing())
        {
            //Get the list...
            this.refreshATMListing();
        }

        String paramUpperCaseTrimmed =
                (cityLookupParam == null || cityLookupParam.trim().isEmpty()) ? null:
                        cityLookupParam.trim().toUpperCase();

        List<String> returnVal = new ArrayList<>();

        CSMApp.atmToCityMapping.keySet().forEach(
                toAdd -> {

                    if(paramUpperCaseTrimmed == null || toAdd.contains(paramUpperCaseTrimmed))
                    {
                        returnVal.add(atmToCityProperFormatMapping.get(toAdd));
                    }
                }
        );

        //Sort the Cities...
        Collections.sort(returnVal);

        return returnVal;
    }

    /**
     * A check is performed whether the local listing
     * should be updated.
     * 
     * @return Whether the local listing should be updated.
     */
    private boolean shouldRefreshATMListing()
    {
        long now = System.currentTimeMillis();

        //Ideally this should be a configuration...
        if((now + TimeUnit.HOURS.toMillis(FETCH_ATM_LIST_EVERY_HOURS)) > this.lastFetch)
        {
            return true;
        }

        return false;
    }

    /**
     * Fetch all the ATM information from the many financial
     * institutions.
     *
     * This could be better written if we were using the Java8 concurrency
     *
     * @see java.util.concurrent.Executor
     */
    private void refreshATMListing()
    {
        //We ensure that no thread accessing this while.
        //being modified. 
        synchronized (CSMApp.atmToCityMapping)
        {
            CSMApp.atmToCityMapping.clear();
            CSMApp.atmToCityProperFormatMapping.clear();
            
            //Use each of the DAO items...
            this.allAtmDaos.forEach((daoItm) -> {

                try
                {
                    List<ATM> atmsFromDAOImpl = daoItm.getListOfAllATMs();

                    if(atmsFromDAOImpl != null && !atmsFromDAOImpl.isEmpty())
                    {
                        for(ATM atm : atmsFromDAOImpl)
                        {
                            if(atm.getAddress() == null ||
                                    (atm.getAddress().getCity() == null ||
                                            atm.getAddress().getCity().isEmpty()))
                            {
                                continue;
                            }

                            String key = atm.getAddress().getCity().trim().toUpperCase();

                            List<ATM> atmsForCity = CSMApp.atmToCityMapping.get(key);

                            if(atmsForCity == null)
                            {
                                atmsForCity = new ArrayList<>();
                            }

                            //Ensure the ATM is not added already...
                            if(!atmsForCity.contains(atm))
                            {
                                atmsForCity.add(atm);
                            }

                            //Sort the ATMs...
                            Collections.sort(
                                    atmsForCity,
                                    new SortATMsByStreetNameComparator());
                            
                            CSMApp.atmToCityMapping.put(key, atmsForCity);

                            if(!CSMApp.atmToCityProperFormatMapping.containsKey(key))
                            {
                                synchronized (CSMApp.atmToCityProperFormatMapping)
                                {
                                    CSMApp.atmToCityProperFormatMapping.put(key, atm.getAddress().getCity().trim());
                                }
                            }
                        }
                    }
                }
                catch (CMSException problem)
                {
                    this.logger.log(
                            Level.SEVERE,
                            "Unable to retrieve for '"+
                            daoItm.getClass().getSimpleName()+"'. "+
                            problem.getMessage(),problem);
                }
            });
        }

        this.lastFetch = System.currentTimeMillis();
    }

    /**
     * Comparator used to sort ATM's by street.
     *
     * @see Comparator
     */
    private static final class SortATMsByStreetNameComparator implements Comparator<ATM>
    {
        /**
         *
         * @param o1Param
         * @param o2Param
         * @return
         */
        @Override
        public int compare(ATM o1Param, ATM o2Param) {

            if((o1Param == null || o2Param == null) ||
                    (o1Param.getAddress() == null) || (o2Param.getAddress() == null))
            {
                return 0;
            }

            if(o1Param.getAddress().getStreet() == null ||
                    o2Param.getAddress().getStreet() == null)
            {
                return 0;
            }

            return o1Param.getAddress().getStreet().compareTo(
                    o2Param.getAddress().getStreet());
        }
    }

    /**
     * Comparator used to sort ATM's by city.
     *
     * @see Comparator
     */
    private static final class SortATMsByCityComparator implements Comparator<ATM>
    {
        /**
         *
         * @param o1Param
         * @param o2Param
         * @return
         */
        @Override
        public int compare(ATM o1Param, ATM o2Param) {

            if((o1Param == null || o2Param == null) ||
                    (o1Param.getAddress() == null) || (o2Param.getAddress() == null))
            {
                return 0;
            }

            if(o1Param.getAddress().getCity() == null ||
                    o2Param.getAddress().getCity() == null)
            {
                return 0;
            }

            return o1Param.getAddress().getCity().compareTo(
                    o2Param.getAddress().getCity());
        }
    }
}
