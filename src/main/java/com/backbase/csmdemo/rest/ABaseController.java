package com.backbase.csmdemo.rest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for all Rest Controllers.
 *
 * @author jasonbruwer on 11/3/17.
 * @since 1.0
 */
public abstract class ABaseController {

    protected Logger logger =
            LoggerFactory.getLogger(this.getClass().getName());
    
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
