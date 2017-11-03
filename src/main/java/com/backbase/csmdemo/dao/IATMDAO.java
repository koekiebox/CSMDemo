package com.backbase.csmdemo.dao;

import com.backbase.csmdemo.exception.CMSException;
import com.backbase.csmdemo.model.ATM;

import java.util.List;

/**
 * Data Access Object for retrieving ATM related information.
 *
 * @author jasonbruwer on 11/3/17.
 * @since 1.0
 */
public interface IATMDAO {

    /**
     * Retrieve a list of all ATMs.
     * 
     * @return
     * @throws CMSException When unable to 
     */
    public abstract List<ATM> getListOfAllATMs() throws CMSException;
}
