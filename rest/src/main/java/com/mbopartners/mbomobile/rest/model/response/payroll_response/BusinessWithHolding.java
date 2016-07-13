package com.mbopartners.mbomobile.rest.model.response.payroll_response;

import com.google.gson.annotations.SerializedName;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TablePayrollAmount;

import java.io.Serializable;

import ua.com.mobidev.android.mdrest.web.model.validation.Validatable;
import ua.com.mobidev.android.mdrest.web.model.validation.ValidationHelper;

/**
 * Created by MboAdil on 13/7/16.
 */
public class BusinessWithHolding implements Serializable,Validatable {

    private static final String TAG = BusinessWithHolding.class.getSimpleName();

    @SerializedName("payrollAmount")
    private PayrollAmount payrollAmount;


    public BusinessWithHolding(PayrollAmount payrollAmount)
    {
        this.payrollAmount=payrollAmount;
    }
    @Override
    public boolean isValid() {
        boolean result =
                payrollAmount!=null/* &&
                        name != null &&
                        mboId != null &&
                        balance != null && next_payroll!=null*/;

        if (! result) {

            ValidationHelper.Screamer screamer = new ValidationHelper.Screamer(TAG, "");
            screamer.sayIfIsNull("payrollAmount", payrollAmount);
            /*screamer.sayIfIsNull("name", name);
            //screamer.sayIfIsNull("mboId", mboId);
            screamer.sayIfIsNull("balance", balance);*//*
            screamer.sayIfIsNull("next_payroll", next_payroll);
            screamer.sayIfIsNull("last_payroll", last_payroll);*/
        }
        return result;
    }
    public  BusinessWithHolding(){}

    public PayrollAmount getPayrollAmount() {
        return payrollAmount;
    }

    public void setPayrollAmount(PayrollAmount payrollAmount) {
        this.payrollAmount = payrollAmount;
    }
}
