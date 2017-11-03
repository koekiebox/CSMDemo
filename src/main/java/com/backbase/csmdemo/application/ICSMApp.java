package com.backbase.csmdemo.application;

import com.backbase.csmdemo.model.ATM;

import java.util.List;

/**
 * The CSM application used for business logic functions.
 *
 * @author jasonbruwer on 11/3/17.
 * @since 1.0
 */
public interface ICSMApp {

    /**
     * Retrieve all the ATMs for {@code cityParam}
     *
     * @param cityParam The city for which to retrieve ATMs.
     * @return Listing of ATMs.
     */
    public abstract List<ATM> getListOfATMsForCity(String cityParam);

    /**
     * Retrieve all the ATMs from all possible sources.
     *
     * @return Listing of ATMs.
     */
    public abstract List<ATM> getCompleteListOfATMs();
}
