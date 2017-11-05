package com.backbase.csmdemo;

import com.backbase.csmdemo.dao.ABaseClientWS;

/**
 * The super class 
 *
 * @author jasonbruwer on 11/4/17.
 * @since 1.0
 *
 * @see ABaseClientWS
 */
public class ABaseTestCase {

    /**
     * Storage class for all the api endpoints.
     */
    protected static class URLEndpoint
    {
        public static String API_BASE = "http://localhost:8080/csmdemo/api/";

        /**
         * URL postfix statics.
         */
        public static class PostFix
        {
            public static String ATM_CITY = "atm/city/";
            public static String ATM = "atm/";
        }

        /**
         * Web service params.
         */
        public static class Param
        {
            public static String CITY_ALIAS = "city_alias";
            public static String CITY = "city";
        }

    }

    /**
     * A rest client use for testing local rest web service.
     */
    protected static class RestClient extends ABaseClientWS
    {
        /**
         * @param endpointBaseUrlParam
         */
        public RestClient(String endpointBaseUrlParam) {
            super(endpointBaseUrlParam);
        }
    }
}
