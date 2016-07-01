package com.mbopartners.mbomobile.rest.model.response.payroll_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ua.com.mobidev.android.mdrest.web.model.validation.Validatable;
import ua.com.mobidev.android.mdrest.web.model.validation.ValidationHelper;

/**
 * Created by MboAdil on 30/6/16.
 */
public class BusinessCenter implements Serializable, Validatable {

    private static final String TAG = BusinessCenter.class.getSimpleName();
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("mboId")
    private String mboId;
    @SerializedName("balance")
    private String balance;
    @SerializedName("address")
    private Address address;

    public BusinessCenter(){}

    public BusinessCenter(String id,String name,String mboId,String balance,Address address)
    {
        this.id=id;
        this.name=name;
        this.mboId=mboId;
        this.balance=balance;
        this.address=address;
    }
    @Override
    public boolean isValid() {
        boolean result =
                id != null &&
                        //description != null &&
                        name != null &&
                        mboId != null &&
                        balance != null &&
                        address != null;

        if (! result) {

            ValidationHelper.Screamer screamer = new ValidationHelper.Screamer(TAG, "");
            screamer.sayIfIsNull("id", id);
            screamer.sayIfIsNull("name", name);
            screamer.sayIfIsNull("mboId", mboId);
            screamer.sayIfIsNull("balance", balance);
            screamer.sayIfIsNull("address", address);
        }
        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
