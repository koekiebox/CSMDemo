package com.backbase.csmdemo.model;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Test the {@code Address} model
 *
 * @author jasonbruwer on 11/4/17.
 * @since 1.0
 *
 * @see Address
 */
public class TestGeoLocation {

    /**
     *
     */
    @Test
    public void testEquals()
    {
        GeoLocation geoLocationOne = new GeoLocation();
        GeoLocation geoLocationTwo = new GeoLocation();

        //Test the basic...
        TestCase.assertTrue(geoLocationOne.equals(geoLocationTwo));
        geoLocationOne.setLatitude(1);

        TestCase.assertFalse(geoLocationOne.equals(geoLocationTwo));
        geoLocationTwo.setLatitude(1);
        
        TestCase.assertTrue(geoLocationOne.equals(geoLocationTwo));
        geoLocationTwo.setLongitude(1);

        TestCase.assertFalse(geoLocationOne.equals(geoLocationTwo));

        geoLocationOne.setLongitude(1);
        
        TestCase.assertTrue(geoLocationOne.equals(geoLocationTwo));
    }
}
