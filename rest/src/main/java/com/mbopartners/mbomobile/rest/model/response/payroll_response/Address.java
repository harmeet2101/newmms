package com.mbopartners.mbomobile.rest.model.response.payroll_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ua.com.mobidev.android.mdrest.web.model.validation.Validatable;
import ua.com.mobidev.android.mdrest.web.model.validation.ValidationHelper;

/**
 * Created by MboAdil on 30/6/16.
 */
public class Address implements Serializable, Validatable {

    private static final String TAG = Address.class.getSimpleName();
    @SerializedName("line1")
    private String line1;
    @SerializedName("line2")
    private String line2;
    @SerializedName("city")
    private String city;
    @SerializedName("state")
    private String state;
    @SerializedName("postalCode")
    private String postalCode;

    @Override
    public boolean isValid() {
        boolean result =
                line1 != null &&
                        //description != null &&
                        line2 != null &&
                        city != null &&
                        state != null &&
                        postalCode != null;

        if (! result) {

            ValidationHelper.Screamer screamer = new ValidationHelper.Screamer(TAG, "");
            screamer.sayIfIsNull("line1", line1);
            screamer.sayIfIsNull("line2", line2);
            screamer.sayIfIsNull("city", city);
            screamer.sayIfIsNull("state", state);
            screamer.sayIfIsNull("postalCode", postalCode);
        }
        return result;
    }
    private Address(){}

    private Address(String line1,String line2,String city,String state,String postalCode)
    {
        this.line1=line1;
        this.line2=line2;
        this.city=city;
        this.state=state;
        this.postalCode=postalCode;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
