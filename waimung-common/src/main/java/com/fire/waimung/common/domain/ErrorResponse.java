package com.fire.waimung.common.domain;

import com.fire.waimung.common.exception.StandardBusinessException;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {


    private String error;

    private List<String> details = new ArrayList<>();

    private long timestamp;

    public ErrorResponse(Throwable t) {
        if (t instanceof StandardBusinessException) {
            this.error = ((StandardBusinessException) t).getError();
            for (Object obj : ((StandardBusinessException) t).getDetails()) {
                this.details.add(obj.toString());
            }
            this.timestamp = ((StandardBusinessException) t).getTimestamp();
        } else {
            this.error = "SYSTEM_EXCEPTION";
            this.timestamp = System.currentTimeMillis();
        }
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
