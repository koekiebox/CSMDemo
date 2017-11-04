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
public class TestATM {

    /**
     *
     */
    @Test
    public void testEquals()
    {
        ATM atmOne = new ATM("ING",new Address(new GeoLocation()));
        ATM atmTwo = new ATM("HSBC",new Address(new GeoLocation()));

        //Test the basic...
        TestCase.assertFalse(atmOne.equals(atmTwo));
        atmOne.setType("HSBC");

        TestCase.assertTrue(atmOne.equals(atmTwo));

        TestCase.assertTrue(atmOne.equals(atmTwo));
        atmOne.getAddress().getGeoLocation().setLatitude(1);

        TestCase.assertFalse(atmOne.equals(atmTwo));
        atmTwo.getAddress().getGeoLocation().setLatitude(1);

        TestCase.assertTrue(atmOne.equals(atmTwo));
        atmTwo.getAddress().getGeoLocation().setLongitude(1);

        TestCase.assertFalse(atmOne.equals(atmTwo));

        atmOne.getAddress().getGeoLocation().setLongitude(1);

        TestCase.assertTrue(atmOne.equals(atmTwo));
    }
}
