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
     *
     * @return
     */
    public static ICSMApp getCMSApp()
    {
        return new CSMApp();
    }

    /**
     * 
     * @return
     */
    public static ICSMApp getCMSAppForTest()
    {
        return new CSMApp(true);
    }
}
