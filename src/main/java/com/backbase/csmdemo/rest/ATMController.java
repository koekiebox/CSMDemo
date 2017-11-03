package com.backbase.csmdemo.rest;

import com.backbase.csmdemo.application.AppFactory;
import com.backbase.csmdemo.application.ICSMApp;
import com.backbase.csmdemo.model.ATM;
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
//@RequestMapping("/atm")
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
            value = "/atm",
            method = RequestMethod.GET,
            consumes = "*",
            produces = ContentType.JSON)
    //TODO @Secured({ "ROLE_SYSTEM" })
    @ResponseBody
    public List<ATM> listATMsForAsJSON(@RequestParam("city") String cityParam)
    {
        this.logger.debug("In the REQUEST!");
        System.out.println("In the REQUEST!");

        //By city...
        List<ATM> returnVal = null;
        if(cityParam != null || !cityParam.trim().isEmpty())
        {
            System.out.println("FOR CITY: "+cityParam);
            returnVal = this.cmsApp.getListOfATMsForCity(cityParam);
        }

        if(returnVal == null)
        {
            return new ArrayList<>();
        }

        return returnVal;
    }
}
