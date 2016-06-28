package com.mbopartners.mbomobile.rest.model.response;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ua.com.mobidev.android.mdrest.web.model.validation.Validatable;

public class ExpenseData implements Serializable, Validatable {
    private static final String TAG = ExpenseData.class.getSimpleName();

    @SerializedName("name")
    private String name = null;
    @SerializedName("value")
    private String value = null;

    @Override
    public boolean isValid() {
        boolean result = (name !=null && value != null);

        if (! result) {
            Log.e(TAG, "NOT VALID. name = " + name);
        }
        return result;
    }

    public ExpenseData(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ExpenseData{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
