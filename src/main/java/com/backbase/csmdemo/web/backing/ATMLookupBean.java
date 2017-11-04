package com.backbase.csmdemo.web.backing;

import com.backbase.csmdemo.application.AppFactory;
import com.backbase.csmdemo.application.ICSMApp;
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
 * JSF Bean used to lookup transactions.
 */
@SessionScoped
@ManagedBean(name = "atmLookupBean")
public class ATMLookupBean implements Serializable {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private ICSMApp cmsApp;
    private List<ATM> atmListing = null;
    private String inputCity;

    /**
     * Default constructor to load applications.
     */
    public ATMLookupBean() {
        super();

        this.cmsApp = AppFactory.getCMSApp();
        this.atmListing = new ArrayList<>();
    }

    /**
     * Populate with all the atms on page load...
     */
    @PostConstruct
    public void populateATMListing()
    {
        this.atmListing = this.cmsApp.getCompleteListOfATMs();
    }

    /**
     * @param inputTextParam
     * @return
     */
    public List<String> completePossibleCity(String inputTextParam)
    {
        List<String> returnVal =
                this.cmsApp.getUniqueCityNamesWhereContains(inputTextParam);

        if(returnVal.size() > 20)
        {
            return returnVal.subList(0,20);
        }
        
        return returnVal;
    }

    /**
     *
     * @param eventParam
     */
    public void actionCitySelectedEvent(SelectEvent eventParam)
    {
        this.logger.finer("Looking for ATMs in City '"+
                eventParam.getObject().toString()+"'.");

        this.atmListing.clear();

        this.atmListing.addAll(this.cmsApp.getListOfATMsForCity(
                eventParam.getObject().toString()));
        
        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage("City Selected.",
                        "Total of "+ this.atmListing.size()
                                +" ATM's at "+eventParam.getObject().toString()+"."));
    }

    /**
     *
     * @return
     */
    public List<ATM> getAtmListing() {
        return this.atmListing;
    }

    /**
     * 
     * @param atmListingParam
     */
    public void setAtmListing(List<ATM> atmListingParam) {
        this.atmListing = atmListingParam;
    }

    /**
     *
     * @return
     */
    public String getInputCity() {
        return this.inputCity;
    }

    /**
     *
     * @param inputCityParam
     */
    public void setInputCity(String inputCityParam) {
        this.inputCity = inputCityParam;
    }
}
