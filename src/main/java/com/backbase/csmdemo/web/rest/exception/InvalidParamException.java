package com.backbase.csmdemo.web.rest.exception;

import com.backbase.csmdemo.exception.CMSException;

/**
 * When a rest parameter is invalid.
 *
 * @author jasonbruwer on 11/4/17.
 * @since 1.0
 */
public class InvalidParamException extends CMSException {

    private static int CODE = 10003;

    /**
     * @param messageParam Sets the master message.
     */
    public InvalidParamException(String messageParam) {
        super(messageParam, CODE);
    }

    /**
     * @param messageParam Sets the master message.
     * @param causeParam The cause of the problem.
     */
    public InvalidParamException(String messageParam, Throwable causeParam) {
        super(messageParam, causeParam, CODE);
    }
}
