package com.backbase.csmdemo.dao.exception;

import com.backbase.csmdemo.exception.CMSException;

/**
 * When a DAO issue occur.
 *
 * Makes use of {@code 10001}.
 *
 * @author jasonbruwer on 11/4/17.
 * @since 1.0
 */
public class DAOException extends CMSException {

    private static int CODE = 10001;

    /**
     * 
     * @param messageParam Sets the super message.
     */
    public DAOException(String messageParam) {
        super(messageParam, CODE);
    }

    /**
     *
     * @param messageParam Sets the super message.
     * @param causeParam The origin / cause of the problem.
     */
    public DAOException(String messageParam, Throwable causeParam) {
        super(messageParam, causeParam, CODE);
    }
}
