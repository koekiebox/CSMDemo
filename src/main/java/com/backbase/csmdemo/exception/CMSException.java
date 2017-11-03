package com.backbase.csmdemo.exception;

/**
 * Super class for all exception
 *
 * @author jasonbruwer on 11/3/17.
 * @since 1.0
 */
public abstract class CMSException extends Exception {

    private int errorCode;

    /**
     * Constructs a new runtime exception with the specified detail message. The
     * cause is not initialized, and may subsequently be initialized by a call
     * to {@link #initCause}.
     *
     * @param messageParam the detail message. The detail message is saved for later
     *            retrieval by the {@link #getMessage()} method.
     * @param errorCodeParam Error code of the {@code Exception}.
     */
    public CMSException(String messageParam, int errorCodeParam) {
        super(messageParam);
        this.errorCode = errorCodeParam;
    }

    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause. <p> Note that the detail message associated with {@code cause} is
     * <i>not</i> automatically incorporated in this runtime exception's detail
     * message.
     *
     * @param messageParam the detail message (which is saved for later retrieval by
     *            the {@link #getMessage()} method).
     * @param causeParam the cause (which is saved for later retrieval by the
     *            {@link #getCause()} method). (A <tt>null</tt> value is
     *            permitted, and indicates that the cause is nonexistent or
     *            unknown.)
     * @param errorCodeParam Error code of the {@code Exception}.
     */
    public CMSException(String messageParam, Throwable causeParam, int errorCodeParam) {
        super(messageParam, causeParam);
        this.errorCode = errorCodeParam;
    }

    /**
     * Gets the error code for {@code this} Exception.
     *
     * @return Numerical error code category for the exception.
     */
    public int getErrorCode() {
        return this.errorCode;
    }
}
