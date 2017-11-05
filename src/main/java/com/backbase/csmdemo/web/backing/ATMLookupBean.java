package com.backbase.csmdemo.web.backing;

import com.backbase.csmdemo.application.AppFactory;
import com.backbase.csmdemo.application.IATMApp;
import com.backbase.csmdemo.model.ATM;
import org.primefaces.event.SelectEvent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * JSF session Bean used to lookup ATMs in the Netherlands.
 */
@SessionScoped
@ManagedBean(name = "atmLookupBean")
public class ATMLookupBean implements Serializable {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    //We make use of the atm app directly.
    //There is no point to add the REST layer as it would add unnecessary fat.
    private IATMApp atmApp;

    private List<ATM> atmListing = null;
    private String inputCity;

    /**
     * Default constructor to load applications.
     */
    public ATMLookupBean() {
        super();

        this.atmApp = AppFactory.getATMApp();
        this.atmListing = new ArrayList<>();
    }

    /**
     * Populate with all the ATMs on session start.
     */
    @PostConstruct
    public void populateATMListing()
    {
        this.atmListing = this.atmApp.getCompleteListOfATMs();
    }

    /**
     * Help the user to complete the city to select.
     *
     * We only return the top 20 results.
     *
     * @param inputTextParam The partial text used for lookup.
     * 
     * @return The valid cities from {@code inputTextParam}.
     */
    public List<String> completePossibleCity(String inputTextParam)
    {
        List<String> returnVal =
                this.atmApp.getUniqueCityNamesWhereContains(inputTextParam);

        if(returnVal.size() > 20)
        {
            return returnVal.subList(0, 20);
        }
        
        return returnVal;
    }

    /**
     * Fired when a city is selected.
     *
     * @param eventParam The JSF select event.
     */
    public void actionCitySelectedEvent(SelectEvent eventParam)
    {
        this.logger.finer("Looking for ATMs in City '"+
                eventParam.getObject().toString()+"'.");

        this.atmListing.clear();

        this.atmListing.addAll(this.atmApp.getListOfATMsForCity(
                eventParam.getObject().toString()));
        
        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage("City Selected.",
                        "Total of "+ this.atmListing.size()
                                +" ATM's at "+eventParam.getObject().toString()+"."));
    }

    /**
     * @return List of ATMs
     */
    public List<ATM> getAtmListing() {
        return this.atmListing;
    }

    /**
     * @param atmListingParam List of ATMs
     */
    public void setAtmListing(List<ATM> atmListingParam) {
        this.atmListing = atmListingParam;
    }

    /**
     * @return Input City
     */
    public String getInputCity() {
        return this.inputCity;
    }

    /**
     * @param inputCityParam Input City
     */
    public void setInputCity(String inputCityParam) {
        this.inputCity = inputCityParam;
    }
}
