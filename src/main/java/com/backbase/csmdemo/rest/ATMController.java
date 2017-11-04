package com.backbase.csmdemo.rest;

import com.backbase.csmdemo.application.AppFactory;
import com.backbase.csmdemo.application.ICSMApp;
import com.backbase.csmdemo.exception.CMSException;
import com.backbase.csmdemo.model.ATM;
import com.backbase.csmdemo.rest.exception.InvalidParamException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The rest controller for ATM related web service calls.
 *
 * @author jasonbruwer on 11/3/17.
 * @since 1.0
 */
@RestController
@RequestMapping("/atm")
public class ATMController extends ABaseController{
    
    //@Autowired
    //private WebApplicationContext applicationContext;

    private ICSMApp cmsApp;

    /**
     * Default constructor
     */
    public ATMController() {

        this.cmsApp = AppFactory.getCMSApp();
    }

    /**
     * List ATM's as JSON for a specific city.
     *
     * @param cityParam The city to retrieve ATM's for.
     *
     * @return A list of ATM's in the {cityParam} city.
     */
    @RequestMapping(
            value = "/",
            method = RequestMethod.GET,
            consumes = ContentType.ALL,
            produces = ContentType.JSON)
    //TODO @Secured({ "ROLE_SYSTEM" })
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
        
        List<ATM> returnVal = this.cmsApp.getListOfATMsForCity(cityParam);

        if(returnVal == null)
        {
            returnVal = new ArrayList<>();
        }

        this.logger.debug("Fetching ATM's for '{}' returned '{}' results.",
                cityParam, returnVal.size());

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
                this.cmsApp.getUniqueCityNamesWhereContains(cityAliasParam);

        if(returnVal == null)
        {
            returnVal = new ArrayList<>();
        }

        this.logger.debug("Fetching City alias for '{}' returned '{}' results.",
                cityAliasParam, returnVal.size());

        return returnVal;
    }
}
