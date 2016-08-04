package com.mbopartners.mbomobile.rest.model.response.payroll_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ua.com.mobidev.android.mdrest.web.model.validation.Validatable;
import ua.com.mobidev.android.mdrest.web.model.validation.ValidationHelper;

/**
 * Created by MboAdil on 4/8/16.
 */
public class PersonNetAmount implements Serializable, Validatable {


    private static final String TAG = PersonNetAmount.class.getSimpleName();
    @SerializedName("amount")
    private Double amount;
    @SerializedName("amountMtd")
    private Double amountMtd;
    @SerializedName("amountYtd")
    private Double amountYtd;
    @SerializedName("name")
    private String name;

    @Override
    public boolean isValid() {
        boolean result =
                amount!=null &&
                        name != null &&
                        amountMtd != null &&
                        amountYtd != null;

        if (! result) {

            ValidationHelper.Screamer screamer = new ValidationHelper.Screamer(TAG, "");
            screamer.sayIfIsNull("amount", amount);
            screamer.sayIfIsNull("name", name);
            screamer.sayIfIsNull("amountMtd", amountMtd);
            screamer.sayIfIsNull("amountYtd",amountYtd);

        }
        return result;
    }
    public PersonNetAmount(){}


    public PersonNetAmount(Double amount,Double amountMtd,Double amountYtd,String name)
    {
        this.amount=amount;
        this.amountMtd=amountMtd;
        this.amountYtd=amountYtd;
        this.name=name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmountMtd() {
        return amountMtd;
    }

    public void setAmountMtd(double amountMtd) {
        this.amountMtd = amountMtd;
    }

    public double getAmountYtd() {
        return amountYtd;
    }

    public void setAmountYtd(double amountYtd) {
        this.amountYtd = amountYtd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
