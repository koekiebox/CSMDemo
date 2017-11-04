package com.backbase.csmdemo.web.rest;


import java.util.logging.Logger;

/**
 * Base class for all Rest Controllers.
 *
 * @author jasonbruwer on 11/3/17.
 * @since 1.0
 */
public abstract class ABaseController {

    protected Logger logger = Logger.getLogger(this.getClass().getName());
    
    /**
     *
     */
    protected static class Role{
        public static final String ATM_ADMIN = "ATM Admin";
    }

    /**
     * 
     */
    protected static class ContentType{

        public static final String ALL = "*";
        public static final String JSON = "application/json";
    }
}
