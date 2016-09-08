package com.mbopartners.mbomobile.rest.model.param;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PayrollPreviewsEntity implements Serializable {


    @SerializedName("payrollDate")
    public String payrollDate;
    @SerializedName("method")
    public String method;
    @SerializedName("amount")
    public String amount;
}
