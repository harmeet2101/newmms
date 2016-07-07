package com.mbopartners.mbomobile.rest.model.response.payroll_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ua.com.mobidev.android.mdrest.web.model.validation.Validatable;
import ua.com.mobidev.android.mdrest.web.model.validation.ValidationHelper;

/**
 * Created by MboAdil on 7/7/16.
 */
public class PayrollSummary  implements Serializable, Validatable {

    private static final String TAG = BusinessCenter.class.getSimpleName();

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("mboId")
    private String mboId;
    @SerializedName("balance")
    private Double balance;


    public PayrollSummary(){}

    public PayrollSummary(String id,String name,String mboId,Double balance)
    {

        this.id=id;
        this.name=name;
        this.mboId=mboId;
        this.balance=balance;
    }
    @Override
    public boolean isValid() {
        boolean result =
                id!=null &&
                        name != null &&
                        mboId != null &&
                        balance != null;

        if (! result) {

            ValidationHelper.Screamer screamer = new ValidationHelper.Screamer(TAG, "");
            screamer.sayIfIsNull("id", id);
            screamer.sayIfIsNull("name", name);
            screamer.sayIfIsNull("mboId", mboId);
            screamer.sayIfIsNull("balance", balance);
        }
        return result;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMboId() {
        return mboId;
    }

    public void setMboId(String mboId) {
        this.mboId = mboId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}