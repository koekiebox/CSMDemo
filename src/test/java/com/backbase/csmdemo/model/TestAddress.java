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
public class TestAddress {

    /**
     *
     */
    @Test
    public void testEquals()
    {
        Address addressOne = new Address(new GeoLocation());
        Address addressTwo = new Address(new GeoLocation());
        
        //Test the basic...
        TestCase.assertTrue(addressOne.equals(addressTwo));
        addressOne.getGeoLocation().setLatitude(1);

        TestCase.assertFalse(addressOne.equals(addressTwo));
        addressTwo.getGeoLocation().setLatitude(1);

        TestCase.assertTrue(addressOne.equals(addressTwo));
        addressTwo.getGeoLocation().setLongitude(1);

        TestCase.assertFalse(addressOne.equals(addressTwo));
        
        addressOne.getGeoLocation().setLongitude(1);

        TestCase.assertTrue(addressOne.equals(addressTwo));
    }
}
