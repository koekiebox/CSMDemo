package com.backbase.csmdemo.dao;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.List;

/**
 * Test the {@code DAOFactory} class.
 *
 * @author jasonbruwer on 11/4/17.
 * @since 1.0
 *
 * @see DAOFactory
 */
public class TestDAOFactory {

    /**
     * Test whether the ING DAO works.
     */
    @Test
    public void testGetIGNATMDAO() {
        
        IATMDAO ingDao = DAOFactory.getATMDAO(DAOFactory.ATMType.ING);

        TestCase.assertNotNull("ING is not set!",ingDao);
    }

    /**
     * Test whether the Test DAO works.
     */
    @Test
    public void testGetTestDAO() {

        IATMDAO ingDao = DAOFactory.getATMDAO(DAOFactory.ATMType.Test);

        TestCase.assertNotNull("Test is not set!",ingDao);
    }

    /**
     * Test whether fetching all the DAO's work.
     * We don't want the test DAO.
     */
    @Test
    public void testGetAllDAOs() {

        List<IATMDAO> ingDaos = DAOFactory.getAllATMDAOs();

        TestCase.assertNotNull("Test is not set!",ingDaos);
        TestCase.assertFalse("Test is not set!",ingDaos.isEmpty());
        TestCase.assertEquals(1, ingDaos.size());
    }

    /**
     * Test whether the Test DAO works.
     */
    @Test
    public void testGetTestSpecificDAO() {

        List<IATMDAO> testDao = DAOFactory.getSpecificATMDAOs(
                DAOFactory.ATMType.Test);

        TestCase.assertNotNull("Test is not set!",testDao);
        TestCase.assertFalse("Test is not set!",testDao.isEmpty());
        TestCase.assertEquals(1, testDao.size());
    }
}
