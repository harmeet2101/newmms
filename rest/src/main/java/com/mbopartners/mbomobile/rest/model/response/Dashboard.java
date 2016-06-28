package com.mbopartners.mbomobile.rest.model.response;

import android.util.Log;

import java.io.Serializable;
import java.util.*;

import com.google.gson.annotations.SerializedName;

import ua.com.mobidev.android.mdrest.web.model.validation.Validatable;
import ua.com.mobidev.android.mdrest.web.model.validation.ValidationHelper;

public class Dashboard implements Serializable, Validatable {
    private static final String TAG = Dashboard.class.getSimpleName();

    @SerializedName("purpose")
    private String purpose = null;
    @SerializedName("dashboardData")
    private List<DashboardField> dashboardData = new ArrayList<DashboardField>();

    @Override
    public boolean isValid() {
        boolean result =
                purpose != null &&
                dashboardData != null && ! dashboardData.isEmpty() && ValidationHelper.validAll(dashboardData);

        if (! result) {
            Log.e(TAG, "NOT VALID. purpose = " + purpose);
        }
        return result;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public List<DashboardField> getDashboardData() {
        return dashboardData;
    }

    public void setDashboardData(List<DashboardField> fields) {
        this.dashboardData = fields;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class DashboardData {\n");

        sb.append("  purpose: ").append(purpose).append("\n");
        sb.append("  fields: ").append(dashboardData).append("\n");
        sb.append("}\n");
        return sb.toString();
    }

}
