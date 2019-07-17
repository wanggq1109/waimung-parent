package com.fire.waimung.common.exception;

public class StandardBusinessException extends BusinessException {

    private String error;

    private Object[] details;

    private long timestamp = System.currentTimeMillis();

    public StandardBusinessException(String error,Object... details) {
        super(error);
        this.error = error;
        this.details = details;
    }

    public String getError() {
        return error;
    }

    public Object[] getDetails() {
        return details;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
