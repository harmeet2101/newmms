package com.mbopartners.mbomobile.rest.model.response.payroll_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import ua.com.mobidev.android.mdrest.web.model.validation.Validatable;
import ua.com.mobidev.android.mdrest.web.model.validation.ValidationHelper;

/**
 * Created by windows-7 on 7/9/2016.
 */
public class PreviousPayment implements Serializable, Validatable {

    private static final String TAG = PreviousPayment.class.getSimpleName();

    @SerializedName("id")
    private String id;
    @SerializedName("businessCenterId")
    private String businessCenterId;
    @SerializedName("date")
    private Date date;
    @SerializedName("mboId")
    private String mboId;
    @SerializedName("businessWithholding")
    private BusinessWithHolding businessWithholding;
    @SerializedName("personalWithholding")
    private PersonWithHolding personalWithholding;




    public PreviousPayment(){}

    public PreviousPayment(String businessCenterId,Date date,String id,String mboId)
    {
        this.businessCenterId=businessCenterId;
        this.date=date;
        this.id=id;
        this.mboId=mboId;

    }

    public BusinessWithHolding getBusinessWithholding() {
        return businessWithholding;
    }

    public void setBusinessWithholding(BusinessWithHolding businessWithholding) {
        this.businessWithholding = businessWithholding;
    }

    public PreviousPayment(String businessCenterId,Date date,String id,String mboId,BusinessWithHolding businessWithHolding)
    {
        this.businessCenterId=businessCenterId;
        this.date=date;
        this.id=id;
        this.mboId=mboId;
        this.businessWithholding=businessWithHolding;

    }
    public PreviousPayment(String businessCenterId,Date date,String id,String mboId,BusinessWithHolding businessWithHolding,PersonWithHolding personalWithholding)
    {
        this.businessCenterId=businessCenterId;
        this.date=date;
        this.id=id;
        this.mboId=mboId;
        this.businessWithholding=businessWithHolding;
        this.personalWithholding=personalWithholding;
    }
    @Override
    public boolean isValid() {
        boolean result =
                id!=null &&
                        businessCenterId != null &&
                        mboId != null &&
                        date!=null;


        if (! result) {

            ValidationHelper.Screamer screamer = new ValidationHelper.Screamer(TAG, "");
            screamer.sayIfIsNull("id", id);
            screamer.sayIfIsNull("businessCenterId", businessCenterId);
            screamer.sayIfIsNull("mboId", mboId);
            screamer.sayIfIsNull("date", date);
        }
        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusinessCenterId() {
        return businessCenterId;
    }

    public void setBusinessCenterId(String businessCenterId) {
        this.businessCenterId = businessCenterId;
    }
    public Date getdate() {
        return date;
    }

    public void setdate(Date date) {
        this.date = date;
    }

    public String getMboId() {
        return mboId;
    }

    public void setMboId(String mboId) {
        this.mboId = mboId;
    }

    public PersonWithHolding getPersonalWithholding() {
        return personalWithholding;
    }

    public void setPersonalWithholding(PersonWithHolding personalWithholding) {
        this.personalWithholding = personalWithholding;
    }
}