package com.backbase.csmdemo.application;

import com.backbase.csmdemo.dao.DAOFactory;
import com.backbase.csmdemo.model.ATM;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.List;

/**
 * Test the {@code DAOFactory} class.
 *
 * @author jasonbruwer on 11/4/17.
 * @since 1.0
 *
 * @see DAOFactory
 */
public class TestApplication {

    /**
     * Test whether factory works for prod.
     */
    @Test
    public void testCompleteListOfATMs() {
        
        ICSMApp cmsApp = AppFactory.getCMSAppForTest();

        List<ATM> allATMS = cmsApp.getCompleteListOfATMs();

        TestCase.assertNotNull(allATMS);
        TestCase.assertEquals(6,allATMS.size());

        //Test that the results are sorted by City...
        TestCase.assertEquals("Amsterdam",allATMS.get(0).getAddress().getCity());
        TestCase.assertEquals("Amsterdam",allATMS.get(1).getAddress().getCity());
        TestCase.assertEquals("Amsterdam",allATMS.get(2).getAddress().getCity());
        TestCase.assertEquals("Den Bosch",allATMS.get(3).getAddress().getCity());
        TestCase.assertEquals("Den Bosch",allATMS.get(4).getAddress().getCity());
        TestCase.assertEquals("van Nijvenheimstraat",allATMS.get(5).getAddress().getCity());
    }

    /**
     * Test whether factory works for test.
     */
    @Test
    public void testGetTestApp() {

        ICSMApp cmsApp = AppFactory.getCMSAppForTest();

        //All of them...
        {
            List<String> allATMCities = cmsApp.getUniqueCityNamesWhereContains(null);
            TestCase.assertNotNull(allATMCities);
            TestCase.assertEquals(3, allATMCities.size());

            TestCase.assertEquals("Amsterdam", allATMCities.get(0));
            TestCase.assertEquals("Den Bosch", allATMCities.get(1));
            TestCase.assertEquals("van Nijvenheimstraat", allATMCities.get(2));

            allATMCities = cmsApp.getUniqueCityNamesWhereContains("");
            TestCase.assertNotNull(allATMCities);
            TestCase.assertEquals(3, allATMCities.size());
        }

        //Amsterdam only...
        {
            List<String> amsterdamOnly = cmsApp.getUniqueCityNamesWhereContains("ster");

            TestCase.assertNotNull(amsterdamOnly);
            TestCase.assertEquals(1, amsterdamOnly.size());
            TestCase.assertEquals("Amsterdam", amsterdamOnly.get(0));
        }

        
    }
}
