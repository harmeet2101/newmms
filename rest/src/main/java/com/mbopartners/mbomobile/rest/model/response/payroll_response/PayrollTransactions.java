package com.mbopartners.mbomobile.rest.model.response.payroll_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import ua.com.mobidev.android.mdrest.web.model.validation.Validatable;
import ua.com.mobidev.android.mdrest.web.model.validation.ValidationHelper;

/**
 * Created by MboAdil on 25/7/16.
 */
public class PayrollTransactions implements Serializable, Validatable {

    private static final String TAG = PayrollTransactions.class.getSimpleName();

    @SerializedName("id")
    private String id;
    @SerializedName("mboId")
    private String mboId;
    @SerializedName("businessCenterId")
    private String businessCenterId;
    @SerializedName("date")
    private Date date;
    @SerializedName("businessWithholding")
    private BusinessWithHolding businessWithholding;
    @SerializedName("personalWithholding")
    private PersonWithHolding personalWithholding;



    public PayrollTransactions(){}


    public PayrollTransactions(String id,String mboId,String businessCenterId,Date date,BusinessWithHolding businessWithholding
    ,PersonWithHolding personalWithholding)
    {
        this.id=id;
        this.mboId=mboId;
        this.businessCenterId=businessCenterId;
        this.businessWithholding=businessWithholding;
        this.date=date;
        this.personalWithholding=personalWithholding;
    }

    @Override
    public boolean isValid() {
        boolean result =
                id!=null &&
                        businessCenterId != null &&
                        mboId != null &&
                        date != null && businessWithholding!=null
                        && personalWithholding!=null;

        if (! result) {

            ValidationHelper.Screamer screamer = new ValidationHelper.Screamer(TAG, "");
            screamer.sayIfIsNull("id", id);
            screamer.sayIfIsNull("businessCenterId", businessCenterId);
            screamer.sayIfIsNull("mboId", mboId);
            screamer.sayIfIsNull("date", date);
            screamer.sayIfIsNull("businessWithholding", businessWithholding);
            screamer.sayIfIsNull("personalWithholding", personalWithholding);
        }
        return result;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMboId() {
        return mboId;
    }

    public void setMboId(String mboId) {
        this.mboId = mboId;
    }

    public String getBusinessCenterId() {
        return businessCenterId;
    }

    public void setBusinessCenterId(String businessCenterId) {
        this.businessCenterId = businessCenterId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
