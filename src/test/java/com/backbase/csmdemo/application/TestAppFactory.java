package com.backbase.csmdemo.application;

import com.backbase.csmdemo.dao.DAOFactory;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Test the {@code DAOFactory} class.
 *
 * @author jasonbruwer on 11/4/17.
 * @since 1.0
 *
 * @see DAOFactory
 */
public class TestAppFactory {

    /**
     * Test whether factory works for prod.
     */
    @Test
    public void testGetProdApp() {
        
        ICSMApp cmsApp = AppFactory.getCMSApp();

        TestCase.assertNotNull("App is not set!",cmsApp);
    }

    /**
     * Test whether factory works for test.
     */
    @Test
    public void testGetTestApp() {

        ICSMApp cmsApp = AppFactory.getCMSAppForTest();
        
        TestCase.assertNotNull("App is not set!",cmsApp);
    }
}
