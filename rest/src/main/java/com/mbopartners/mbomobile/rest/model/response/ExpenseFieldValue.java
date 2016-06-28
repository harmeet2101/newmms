package com.mbopartners.mbomobile.rest.model.response;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ua.com.mobidev.android.mdrest.web.model.validation.Validatable;

public class ExpenseFieldValue implements Serializable, Validatable {
    private static final String TAG = ExpenseFieldValue.class.getSimpleName();

    @SerializedName("id")
    private String mboId = null;
    @SerializedName("value")
    private String value = null;

    public ExpenseFieldValue(String mboId, String value) {
        this.mboId = mboId;
        this.value = value;
    }

    @Override
    public boolean isValid() {
        boolean result =
                mboId != null &&
                value != null;

        if (! result) {
            Log.e(TAG, "NOT VALID. id = " + mboId);
        }
        return result;
    }

    public String getMboId() {
        return mboId;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "ExpenseFieldValue{" +
                "mboId='" + mboId + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
