package com.mbopartners.mbomobile.rest.model.response.payroll_response;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ua.com.mobidev.android.mdrest.web.model.validation.Validatable;

/**
 * Created by MboAdil on 30/6/16.
 */
public class PayrollField implements Serializable, Validatable {

    private static final String TAG = PayrollField.class.getSimpleName();

    @SerializedName("name")
    private String name = null;
    @SerializedName("isVisible")
    private Boolean isVisible=null;
    @SerializedName("balanceField")
    private String balanceField=null;
    @SerializedName("timePeriodField")
    private String timePeriodField=null;

    public String getBalanceField() {
        return balanceField;
    }

    public void setBalanceField(String balanceField) {
        this.balanceField = balanceField;
    }

    public String getTimePeriodField() {
        return timePeriodField;
    }

    public void setTimePeriodField(String timePeriodField) {
        this.timePeriodField = timePeriodField;
    }

    public Boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    @Override
    public boolean isValid() {
        boolean result =
                name != null;
        if (! result) {
            Log.e(TAG, "NOT VALID. name = " + name);
        }
        return result;
    }

    public PayrollField(String name,Boolean isVisible,String balanceField,String timePeriodField) {
        this.name = name;
        this.isVisible=isVisible;
        this.balanceField=balanceField;
        this.timePeriodField=timePeriodField;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PayrollDataField{" + "name='" + name  + '}';
    }
}
