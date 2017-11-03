package com.backbase.csmdemo.dao.impl;

import com.backbase.csmdemo.dao.IATMDAO;
import com.backbase.csmdemo.model.ATM;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock
 *
 * @author jasonbruwer on 11/3/17.
 * @since 1.0
 */
public class MockATMDAO implements IATMDAO {

    private static String TYPE = "ACME Bank";

    /**
     * 
     * @return
     */
    @Override
    public List<ATM> getListOfAllATMs() {
        
        List<ATM> returnVal = new ArrayList<>();

        //


        returnVal.add(new ATM("cool"));
        returnVal.add(new ATM("batool"));
        returnVal.add(new ATM("zookie"));

        return returnVal;
    }
}
