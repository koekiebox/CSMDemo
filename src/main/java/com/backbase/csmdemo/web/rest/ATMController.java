package com.backbase.csmdemo.web.rest;

import com.backbase.csmdemo.application.AppFactory;
import com.backbase.csmdemo.application.IATMApp;
import com.backbase.csmdemo.exception.CMSException;
import com.backbase.csmdemo.model.ATM;
import com.backbase.csmdemo.web.rest.exception.InvalidParamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * The rest controller for ATM related web service calls.
 * 
 * The API is not currently security checked.
 *
 * @author jasonbruwer on 11/3/17.
 * @since 1.0
 */
@RestController
@RequestMapping("/atm")
public class ATMController extends ABaseController{
    
    @Autowired
    private WebApplicationContext applicationContext;

    private IATMApp atmApp;

    /**
     * Default constructor
     */
    public ATMController() {

        this.atmApp = AppFactory.getATMApp();
    }

    /**
     * List ATM's as JSON for a specific city.
     *
     * @param cityParam The city to retrieve ATM's for.
     *                  If the city is not provided, an error will be generated.
     * @throws CMSException If application issues arose.
     *
     * @return A list of ATM's in the {cityParam} city.
     */
    @RequestMapping(
            value = "/",
            method = RequestMethod.GET,
            consumes = ContentType.ALL,
            produces = ContentType.JSON)
    //@Secured({ "ROLE_SYSTEM" }) - Leave for now.
    @ResponseBody
    public List<ATM> listATMsForAsJSON(@RequestParam("city") String cityParam)
    throws CMSException {

        //By city...
        if(cityParam == null || cityParam.trim().isEmpty())
        {
            //We want to prevent calls to the API when city is not provided.
            //This is harsh, but callers will learn quickly.
            throw new InvalidParamException(
                    "'city' parameter is required for lookup.");
        }
        
        //We will be able to add more in the future...
        
        List<ATM> returnVal = this.atmApp.getListOfATMsForCity(cityParam);

        if(returnVal == null)
        {
            returnVal = new ArrayList<>();
        }

        this.logger.log(
                Level.FINER,
                "Fetching ATM's for '"+
                        cityParam+"' returned '"+
                        returnVal.size()+"' results.");

        return returnVal;
    }

    /**
     * List ATM's as JSON for a specific city.
     *
     * @param cityAliasParam The city to retrieve ATM's for.
     *
     * @return A list of ATM's in the {cityParam} city.
     */
    @RequestMapping(
            value = "/city",
            method = RequestMethod.GET,
            consumes = ContentType.ALL,
            produces = ContentType.JSON)
    @ResponseBody
    public List<String> listOfATMCitiesForAsJSON(@RequestParam("city_alias") String cityAliasParam)
    {
        //By city...
        List<String> returnVal =
                this.atmApp.getUniqueCityNamesWhereContains(cityAliasParam);

        if(returnVal == null)
        {
            returnVal = new ArrayList<>();
        }

        this.logger.log(
                Level.FINER,
                "Fetching City alias for '"+
                        cityAliasParam+"' returned '"+
                        returnVal.size()+"' results.");
        
        return returnVal;
    }
}
