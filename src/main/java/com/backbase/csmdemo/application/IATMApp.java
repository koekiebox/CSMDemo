package com.backbase.csmdemo.application;

import com.backbase.csmdemo.model.ATM;

import java.util.List;

/**
 * The CSM application used for business logic functions.
 *
 * When the application grows, there will be more application classes in the
 * application layer.
 *
 * Currently we only serve at functions.
 *
 * @author jasonbruwer on 11/3/17.
 * @since 1.0
 */
public interface IATMApp {

    /**
     * Retrieve all the ATMs for {@code cityParam}
     *
     * @param cityParam The city for which to retrieve ATMs.
     * @return Listing of ATMs.
     */
    public abstract List<ATM> getListOfATMsForCity(String cityParam);

    /**
     * Retrieve all the ATMs from all possible sources.
     *
     * @return Listing of ATMs.
     */
    public abstract List<ATM> getCompleteListOfATMs();

    /**
     * Retrieve all the ATM cities where the city name {@code cityLookupParam}.
     *
     * If the name is empty, all the cities will be returned.
     *
     * @param cityLookupParam The lookup text to use.
     *
     * @return Listing of Cities.
     */
    public abstract List<String> getUniqueCityNamesWhereContains(
            String cityLookupParam);
}
