package com.mbopartners.mbomobile.rest.model.response;

/**
 *
 */
public class ResponseAdapter<T> {
    private Class<T> responseEntityClass;

    public ResponseAdapter(Class<T> responseEntityClass) {
        this.responseEntityClass = responseEntityClass;
    }

    public Class<T> getEntityClass() {
        return responseEntityClass;
    }
}
