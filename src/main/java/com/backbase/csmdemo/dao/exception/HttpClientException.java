package com.backbase.csmdemo.dao.exception;

import com.backbase.csmdemo.exception.CMSException;

/**
 * When an HTTP client problem occurs.
 *
 * Makes use of {@code 10002}.
 *
 * @author jasonbruwer on 11/4/17.
 * @since 1.0
 */
public class HttpClientException extends CMSException {

    private static int CODE = 10002;

    /**
     *
     * @param messageParam Sets the super message.
     */
    public HttpClientException(String messageParam) {
        super(messageParam, CODE);
    }

    /**
     *
     * @param messageParam Sets the super message.
     * @param causeParam The cause.
     */
    public HttpClientException(String messageParam, Throwable causeParam) {
        super(messageParam, causeParam, CODE);
    }
}
