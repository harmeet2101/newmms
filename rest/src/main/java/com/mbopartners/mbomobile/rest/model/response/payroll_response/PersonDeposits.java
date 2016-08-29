package com.mbopartners.mbomobile.rest.model.response.payroll_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ua.com.mobidev.android.mdrest.web.model.validation.Validatable;
import ua.com.mobidev.android.mdrest.web.model.validation.ValidationHelper;

/**
 * Created by MboAdil on 26/7/16.
 */
public class PersonDeposits implements Serializable, Validatable {

    private static final String TAG = PersonDeposits.class.getSimpleName();
    @SerializedName("amount")
    private Double amount;
    @SerializedName("name")
    private String name;


    @Override
    public boolean isValid() {
        boolean result =
                amount!=null &&
                        name != null;

        if (! result) {

            ValidationHelper.Screamer screamer = new ValidationHelper.Screamer(TAG, "");
            screamer.sayIfIsNull("amount", amount);
            screamer.sayIfIsNull("name", name);

        }
        return result;
    }
    public PersonDeposits(){}


    public PersonDeposits(Double amount,String name)
    {
        this.amount=amount;
        this.name=name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

