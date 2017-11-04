package com.backbase.csmdemo.dao;

import com.backbase.csmdemo.exception.CMSException;
import com.backbase.csmdemo.model.ATM;
import com.backbase.csmdemo.model.Address;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Test the ATM DAO.
 *
 * @author jasonbruwer on 11/4/17.
 * @since 1.0
 */
public class TestATMDAO {

    /**
     *
     */
    @Before
    public void beforeTestStart()
    {
        //Do nothing for now...
    }

    /**
     * 
     * @throws CMSException
     */
    @Test
    public void testIGNATMDAO()
    throws CMSException {
        
        IATMDAO ingDao = DAOFactory.getATMDAO(DAOFactory.ATMType.ING);

        List<ATM> atmListing = ingDao.getListOfAllATMs();

        TestCase.assertNotNull("Listing is not set (null).", atmListing);
        TestCase.assertFalse("Listing is not set.", atmListing.isEmpty());
        
        ATM firstAtm = atmListing.get(0);

        TestCase.assertNotNull("ATM is not set (null).", firstAtm);
        TestCase.assertNotNull("ATM is not set (null).", firstAtm.getType());
        TestCase.assertEquals("ATM is not ING.","ING", firstAtm.getType());

        Address atmAddress = firstAtm.getAddress();

        TestCase.assertNotNull("Address is not set (null).", atmAddress);

        TestCase.assertNotNull("City is not set (null).", atmAddress.getCity());
        TestCase.assertNotNull("Geo Location is not set (null).", atmAddress.getGeoLocation());
        TestCase.assertNotNull("House Number is not set (null).", atmAddress.getHouseNumber());
        TestCase.assertNotNull("Postal Code is not set (null).", atmAddress.getPostalCode());
        TestCase.assertNotNull("Street is not set (null).", atmAddress.getStreet());
    }
}
