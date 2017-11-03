package com.backbase.csmdemo.dao.exception;

import com.backbase.csmdemo.exception.CMSException;

/**
 * When a DAO issue occur.
 *
 * @author jasonbruwer on 11/4/17.
 * @since 1.0
 */
public class DAOException extends CMSException {

    private static int CODE = 10001;

    /**
     * 
     * @param messageParam
     */
    public DAOException(String messageParam) {
        super(messageParam, CODE);
    }

    /**
     * 
     * @param messageParam
     * @param causeParam
     */
    public DAOException(String messageParam, Throwable causeParam) {
        super(messageParam, causeParam, CODE);
    }
}
