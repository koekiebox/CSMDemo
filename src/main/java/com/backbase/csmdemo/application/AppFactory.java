package com.backbase.csmdemo.application;

import com.backbase.csmdemo.application.impl.CSMApp;

/**
 * Application factory.
 * We only have one application now, but we may have lots more in the future.
 *
 * @author jasonbruwer on 11/3/17.
 * @since 1.0
 */
public class AppFactory {

    /**
     * Retrieve the production CSM App.
     *
     * @return new instance of {@code ATMApp}
     */
    public static IATMApp getATMApp()
    {
        return new CSMApp();
    }

    /**
     * Retrieve the test ATM App.
     * 
     * @return new instance of {@code ATMApp}
     */
    public static IATMApp getATMAppForTest()
    {
        return new CSMApp(true);
    }
}
