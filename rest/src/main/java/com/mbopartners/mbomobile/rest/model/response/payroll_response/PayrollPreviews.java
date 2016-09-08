package com.mbopartners.mbomobile.rest.model.response.payroll_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ua.com.mobidev.android.mdrest.web.model.validation.Validatable;
import ua.com.mobidev.android.mdrest.web.model.validation.ValidationHelper;

public class PayrollPreviews implements Serializable, Validatable {

    private static final String TAG = PayrollPreviews.class.getSimpleName();

    @SerializedName("businessWithholding")
    private BusinessWithHolding businessWithholding;
    @SerializedName("personalWithholding")
    private PersonWithHolding personalWithholding;


    public PayrollPreviews(){}

    public PayrollPreviews(BusinessWithHolding businessWithholding,PersonWithHolding personalWithholding) {

        this.businessWithholding=businessWithholding;
        this.personalWithholding=personalWithholding;
    }


    @Override
    public boolean isValid() {
        boolean result =
                businessWithholding!=null
                        && personalWithholding!=null;

        if (! result) {

            ValidationHelper.Screamer screamer = new ValidationHelper.Screamer(TAG, "");
            screamer.sayIfIsNull("businessWithholding", businessWithholding);
            screamer.sayIfIsNull("personalWithholding", personalWithholding);
        }
        return result;
    }


    public BusinessWithHolding getBusinessWithholding() {
        return businessWithholding;
    }

    public void setBusinessWithholding(BusinessWithHolding businessWithholding) {
        this.businessWithholding = businessWithholding;
    }

    public PersonWithHolding getPersonalWithholding() {
        return personalWithholding;
    }

    public void setPersonalWithholding(PersonWithHolding personalWithholding) {
        this.personalWithholding = personalWithholding;
    }
}
