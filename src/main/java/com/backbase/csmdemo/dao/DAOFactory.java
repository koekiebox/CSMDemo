package com.backbase.csmdemo.dao;

import com.backbase.csmdemo.dao.impl.ing.IngATMDAO;
import com.backbase.csmdemo.dao.impl.mock.MockATMDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object factory.
 *
 * If the system was much bigger, one would
 * need to use an AbstractFactory pattern.
 *
 * @author jasonbruwer on 11/3/17.
 * @since 1.0
 */
public class DAOFactory {

    /**
     *
     */
    public enum ATMType{
        ING,
        Test
    }

    /**
     * Retrieve the Data Access Object interfaces for the {@code atmTypeParam}.
     *
     * @param typesToRetrieveParam The types to filter by.
     *
     * @return All IATMDAO.
     */
    public static List<IATMDAO> getSpecificATMDAOs(ATMType ... typesToRetrieveParam)
    {
        List<IATMDAO> returnVal = new ArrayList<>();

        if(typesToRetrieveParam == null || typesToRetrieveParam.length == 0)
        {
            return returnVal;
        }

        for(ATMType atmType : ATMType.values())
        {
            for(ATMType fromParamToTest : typesToRetrieveParam)
            {
                if(fromParamToTest == atmType)
                {
                    returnVal.add(getATMDAO(atmType));
                    break;
                }
            }
        }

        return returnVal;
    }
    
    /**
     * Retrieve the Data Access Object interfaces for the {@code atmTypeParam}.
     *
     * @return All IATMDAO.
     */
    public static List<IATMDAO> getAllATMDAOs()
    {
        List<IATMDAO> returnVal = new ArrayList<>();

        for(ATMType atmType : ATMType.values())
        {
            //We exclude the test...
            if(atmType == ATMType.Test)
            {
                continue;
            }

            returnVal.add(getATMDAO(atmType));
        }

        return returnVal;
    }

    /**
     * Retrieve the Data Access Object interfaces for the {@code atmTypeParam}.
     *
     * @param atmTypeParam
     * @return
     */
    public static IATMDAO getATMDAO(ATMType atmTypeParam)
    {
        if(atmTypeParam == null)
        {
            return null;
        }

        switch (atmTypeParam)
        {
            case ING:
                return new IngATMDAO();
            case Test:
                return new MockATMDAO();
                default:
        }

        return null;
    }
}
