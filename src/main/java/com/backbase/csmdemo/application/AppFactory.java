package com.backbase.csmdemo.application;

import com.backbase.csmdemo.application.impl.CSMApp;

/**
 * Application factory.
 *
 * @author jasonbruwer on 11/3/17.
 * @since 1.0
 */
public class AppFactory {

    /**
     * Retrieve the production CSM App.
     *
     * @return new instance of {@code CSMApp}
     */
    public static ICSMApp getCMSApp()
    {
        return new CSMApp();
    }

    /**
     * Retrieve the test CSM App.
     * 
     * @return new instance of {@code CSMApp}
     */
    public static ICSMApp getCMSAppForTest()
    {
        return new CSMApp(true);
    }
}
