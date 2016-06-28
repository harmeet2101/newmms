package com.mbopartners.mbomobile.rest.model.response;

import java.io.Serializable;

import ua.com.mobidev.android.mdrest.web.model.validation.Validatable;

public class EmptyResponseEntity implements Serializable, Validatable {
    private static final String TAG = EmptyResponseEntity.class.getSimpleName();

    @Override
    public boolean isValid() {
        return true;
    }
}
