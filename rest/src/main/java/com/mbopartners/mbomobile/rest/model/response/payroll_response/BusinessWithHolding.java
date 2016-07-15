package com.mbopartners.mbomobile.rest.model.response.payroll_response;

import com.google.gson.annotations.SerializedName;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TablePayrollAmount;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ua.com.mobidev.android.mdrest.web.model.validation.Validatable;
import ua.com.mobidev.android.mdrest.web.model.validation.ValidationHelper;

/**
 * Created by MboAdil on 13/7/16.
 */
public class BusinessWithHolding implements Serializable,Validatable {

    private static final String TAG = BusinessWithHolding.class.getSimpleName();

    @SerializedName("payrollAmount")
    private PayrollAmount payrollAmount;
    @SerializedName("businessExpenses")
    private List<BusinessExpenses> businessExpenses = new ArrayList<BusinessExpenses>();
    @SerializedName("payrollTaxes")
    private List<BusinessPayrollTaxes> payrollTaxes = new ArrayList<BusinessPayrollTaxes>();


    public BusinessWithHolding(PayrollAmount payrollAmount)
    {
        this.payrollAmount=payrollAmount;
    }

    public BusinessWithHolding(PayrollAmount payrollAmount,List<BusinessExpenses> businessExpenses)
    {
        this.payrollAmount=payrollAmount;
        this.businessExpenses=businessExpenses;
    }
    public BusinessWithHolding(PayrollAmount payrollAmount,List<BusinessExpenses> businessExpenses,List<BusinessPayrollTaxes> payrollTaxes)
    {
        this.payrollAmount=payrollAmount;
        this.businessExpenses=businessExpenses;
        this.payrollTaxes=payrollTaxes;
    }

    @Override
    public boolean isValid() {
        boolean result =
                payrollAmount!=null &&
                        businessExpenses != null /*&&
                        mboId != null &&
                        balance != null && next_payroll!=null*/;

        if (! result) {

            ValidationHelper.Screamer screamer = new ValidationHelper.Screamer(TAG, "");
            screamer.sayIfIsNull("payrollAmount", payrollAmount);
            screamer.sayIfIsNull("businessExpenses", businessExpenses);
            screamer.sayIfIsNull("payrollTaxes", payrollTaxes);

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

    public List<BusinessExpenses> getBusinessExpenses() {
        return businessExpenses;
    }

    public void setBusinessExpenses(List<BusinessExpenses> businessExpenses) {
        this.businessExpenses = businessExpenses;
    }

    public List<BusinessPayrollTaxes> getPayrollTaxes() {
        return payrollTaxes;
    }

    public void setPayrollTaxes(List<BusinessPayrollTaxes> payrollTaxes) {
        this.payrollTaxes = payrollTaxes;
    }


}
