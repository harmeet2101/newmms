package com.mbopartners.mbomobile.rest.model.response.payroll_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import ua.com.mobidev.android.mdrest.web.model.validation.Validatable;
import ua.com.mobidev.android.mdrest.web.model.validation.ValidationHelper;

/**
 * Created by MboAdil on 7/7/16.
 */
public class NextPayment implements Serializable, Validatable {

    private static final String TAG = NextPayment.class.getSimpleName();

    @SerializedName("id")
    private String id;
    @SerializedName("amount")
    private Double amount;
    @SerializedName("businessCenterId")
    private String businessCenterId;
    @SerializedName("calculationMethod")
    private String calculationMethod;

    @SerializedName("endDate")
    private Date endDate;
    @SerializedName("startDate")
    private Date startDate;
    @SerializedName("frequency")
    private String frequency;
    @SerializedName("mboId")
    private String mboId;
    @SerializedName("businessWithholding")
    private BusinessWithHolding businessWithholding;


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public NextPayment(){}

    public NextPayment(Double amount,String businessCenterId,String calculationMethod
            ,Date endDate,Date startDate,String frequency,String id,String mboId)
    {
        this.amount=amount;
        this.businessCenterId=businessCenterId;
        this.calculationMethod=calculationMethod;
        this.endDate=endDate;
        this.startDate=startDate;
        this.frequency=frequency;
        this.id=id;
        this.mboId=mboId;

    }
    public NextPayment(Double amount,String businessCenterId,String calculationMethod
            ,Date endDate,Date startDate,String frequency,String id,String mboId,BusinessWithHolding businessWithholding)
    {
        this.amount=amount;
        this.businessCenterId=businessCenterId;
        this.calculationMethod=calculationMethod;
        this.endDate=endDate;
        this.startDate=startDate;
        this.frequency=frequency;
        this.id=id;
        this.mboId=mboId;
        this.businessWithholding=businessWithholding;
    }
    @Override
    public boolean isValid() {
        boolean result =
                id!=null &&
                        businessCenterId != null &&
                        mboId != null &&
                        amount != null && calculationMethod!=null&& endDate!=null
                        && startDate!=null&& frequency!=null && businessWithholding!=null;


        if (! result) {

            ValidationHelper.Screamer screamer = new ValidationHelper.Screamer(TAG, "");
            screamer.sayIfIsNull("id", id);
            screamer.sayIfIsNull("businessCenterId", businessCenterId);
            screamer.sayIfIsNull("mboId", mboId);
            screamer.sayIfIsNull("amount", amount);
            screamer.sayIfIsNull("calculationMethod", calculationMethod);
            screamer.sayIfIsNull("endDate", endDate);
            screamer.sayIfIsNull("startDate", startDate);
            screamer.sayIfIsNull("frequency", frequency);
            screamer.sayIfIsNull("businessWithholding", businessWithholding);
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

    public String getCalculationMethod() {
        return calculationMethod;
    }

    public void setCalculationMethod(String calculationMethod) {
        this.calculationMethod = calculationMethod;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getMboId() {
        return mboId;
    }

    public void setMboId(String mboId) {
        this.mboId = mboId;
    }

    public BusinessWithHolding getBusinessWithholding() {
        return businessWithholding;
    }

    public void setBusinessWithholding(BusinessWithHolding businessWithholding) {
        this.businessWithholding = businessWithholding;
    }
}
