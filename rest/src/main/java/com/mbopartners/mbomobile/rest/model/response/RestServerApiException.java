package com.mbopartners.mbomobile.rest.model.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import ua.com.mobidev.android.mdrest.web.model.validation.Validatable;


public class RestServerApiException implements Serializable, Validatable {
    private static final String TAG = RestServerApiException.class.getSimpleName();

    @SerializedName("timestamp")
    private Date timestamp = null;
    @SerializedName("status")
    private Integer status = null;
    @SerializedName("errors")
    private RestServerApiErrorDetail[] errors = null;
    @SerializedName("error")
    private String error = null;
    @SerializedName("message")
    private String message = null;
    @SerializedName("exception")
    private String exception = null;
    @SerializedName("path")
    private String path = null;

    @Override
    public boolean isValid() {
        return true;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public RestServerApiErrorDetail[] getErrors() {
        return errors;
    }

    public void setErrors(RestServerApiErrorDetail[] errors) {
        this.errors = errors;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getErrorsAsString() {
        StringBuilder errorsStringBuilder = new StringBuilder("");
        if (errors != null){
            for (RestServerApiErrorDetail oneError : errors) {
                errorsStringBuilder.append(oneError.toString());
                errorsStringBuilder.append("\n");
            }
        }
        return errorsStringBuilder.toString();
    }

    @Override
    public String toString() {
        return "RestServerApiException{" +
                "timestamp=" + timestamp +
                ", status=" + status +
                ", errors=" + getErrorsAsString() +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", exception='" + exception + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
