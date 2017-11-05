package com.backbase.csmdemo.web.rest;

import com.backbase.csmdemo.ABaseTestCase;
import com.backbase.csmdemo.dao.exception.HttpClientException;
import com.backbase.csmdemo.model.ATM;
import junit.framework.TestCase;
import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the {@code ATMController} rest web service.
 *
 * Please ensure the service is up before running this test case.
 *
 * @author jasonbruwer on 11/4/17.
 * @since 1.0
 *
 * @see ATMController
 * @see ABaseTestCase
 */
public class TestATMControllerRest extends ABaseTestCase{

    private RestClient restClient = null;

    /**
     * Prepare client.
     */
    @Before
    public void init()
    {
        this.restClient = new RestClient(URLEndpoint.API_BASE);
    }

    /**
     * Test fetching cities by prefix.
     * 
     * @throws HttpClientException
     */
    @Test
    public void testListCitiesByAliasLookup()
    throws HttpClientException
    {
        String alias = "amst";

        JSONArray listOfCitiesFor =
                this.restClient.getJsonArray(URLEndpoint.PostFix.ATM_CITY+ "?" +
                        URLEndpoint.Param.CITY_ALIAS + "="+alias);

        TestCase.assertNotNull(listOfCitiesFor);
        TestCase.assertTrue(listOfCitiesFor.length() > 0);
    }

    /**
     * Test fetching cities by prefix.
     *
     * @throws HttpClientException
     */
    @Test
    public void testATMLookupByCiryAmsterdamLookup()
            throws HttpClientException
    {
        String city = "Amsterdam";

        JSONArray listOfAtmsForAmsterdam =
                this.restClient.getJsonArray(URLEndpoint.PostFix.ATM+ "?" +
                        URLEndpoint.Param.CITY + "="+city);

        TestCase.assertNotNull(listOfAtmsForAmsterdam);
        TestCase.assertTrue(listOfAtmsForAmsterdam.length() > 0);

        ATM firstAtm = new ATM(listOfAtmsForAmsterdam.getJSONObject(0));

        //Type...
        TestCase.assertNotNull(firstAtm.getType());
        TestCase.assertFalse(firstAtm.getType().isEmpty());

        //Address...
        TestCase.assertNotNull(firstAtm.getAddress());
        
        TestCase.assertNotNull(firstAtm.getAddress().getCity());
        TestCase.assertFalse(firstAtm.getAddress().getCity().isEmpty());

        TestCase.assertNotNull(firstAtm.getAddress().getHouseNumber());
        TestCase.assertFalse(firstAtm.getAddress().getHouseNumber().isEmpty());

        TestCase.assertNotNull(firstAtm.getAddress().getPostalCode());
        TestCase.assertFalse(firstAtm.getAddress().getPostalCode().isEmpty());

        TestCase.assertNotNull(firstAtm.getAddress().getStreet());
        TestCase.assertFalse(firstAtm.getAddress().getStreet().isEmpty());

        TestCase.assertNotNull(firstAtm.getAddress().getGeoLocation());
    }
}
