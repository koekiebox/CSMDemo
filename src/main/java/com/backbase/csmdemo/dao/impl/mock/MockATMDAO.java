package com.backbase.csmdemo.dao.impl.mock;

import com.backbase.csmdemo.dao.IATMDAO;
import com.backbase.csmdemo.model.ATM;
import com.backbase.csmdemo.model.Address;
import com.backbase.csmdemo.model.GeoLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock implementation class used to test business logic.
 * This mock allows us to test without connecting outward.
 *
 * @author jasonbruwer on 11/3/17.
 * @since 1.0
 */
public class MockATMDAO implements IATMDAO {

    private static String TYPE = "ACME Bank";

    /**
     * Returns a static list of test ATMs.
     *
     * @return Static list of ATM's.
     */
    @Override
    public List<ATM> getListOfAllATMs() {
        
        List<ATM> returnVal = new ArrayList<>();

        returnVal.add(new ATM(TYPE,new Address(
                new GeoLocation(113,12),"Street 2",
                "13","1231 AJ","Den Bosch")));

        returnVal.add(new ATM(TYPE,new Address(
                new GeoLocation(112,15),"Street 1",
                "9","1241 AJ","Den Bosch")));
        
        returnVal.add(new ATM(TYPE,new Address(
                new GeoLocation(11,12),"Street 3",
                "14","1232 AX","Amsterdam")));
        
        returnVal.add(new ATM(TYPE,new Address(
                new GeoLocation(11,13),"Street 1",
                "13","1231 AX","Amsterdam")));

        returnVal.add(new ATM(TYPE,new Address(
                new GeoLocation(11,10),"Street 2",
                "15","1231 AX","Amsterdam")));

        returnVal.add(new ATM(TYPE,new Address(
                new GeoLocation(101,14),"Street 1",
                "9","1241 AF","van Nijvenheimstraat")));

        //Duplicate due to geo...
        returnVal.add(new ATM(TYPE,new Address(
                new GeoLocation(101,14),"Street 555",
                "9","1241 AF","van Nijvenheimstraat")));

        return returnVal;
    }
}
