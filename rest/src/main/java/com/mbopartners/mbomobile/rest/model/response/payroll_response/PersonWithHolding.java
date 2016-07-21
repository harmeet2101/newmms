package com.mbopartners.mbomobile.rest.model.response.payroll_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ua.com.mobidev.android.mdrest.web.model.validation.Validatable;
import ua.com.mobidev.android.mdrest.web.model.validation.ValidationHelper;

/**
 * Created by MboAdil on 14/7/16.
 */
public class PersonWithHolding implements Serializable,Validatable {

    private static final String TAG = PersonWithHolding.class.getSimpleName();

    @SerializedName("grossAmount")
    private PersonGrossAmount grossAmount;
    @SerializedName("payrollTaxes")
    private List<PersonPayrollTaxes> payrollTaxes = new ArrayList<PersonPayrollTaxes>();
    @SerializedName("expenseReimbursements")
    private List<ExpenseReimbursement> expenseReimbursements = new ArrayList<ExpenseReimbursement>();


    @Override
    public boolean isValid() {
        boolean result =
                grossAmount!=null;

        if (! result) {

            ValidationHelper.Screamer screamer = new ValidationHelper.Screamer(TAG, "");
            screamer.sayIfIsNull("grossAmount", grossAmount);
            screamer.sayIfIsNull("payrollTaxes", payrollTaxes);


        }
        return result;
    }
    public  PersonWithHolding(){}

    public PersonWithHolding(PersonGrossAmount grossAmount)
    {
        this.grossAmount=grossAmount;
    }

    public PersonWithHolding(PersonGrossAmount grossAmount,List<PersonPayrollTaxes> payrollTaxes)
    {
        this.grossAmount=grossAmount;
        this.payrollTaxes=payrollTaxes;
    }
    public PersonWithHolding(PersonGrossAmount grossAmount,List<PersonPayrollTaxes> payrollTaxes,List<ExpenseReimbursement> expenseReimbursements)
    {
        this.grossAmount=grossAmount;
        this.payrollTaxes=payrollTaxes;
        this.expenseReimbursements=expenseReimbursements;
    }


    public PersonGrossAmount getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(PersonGrossAmount grossAmount) {
        this.grossAmount = grossAmount;
    }

    public List<PersonPayrollTaxes> getPayrollTaxes() {
        return payrollTaxes;
    }

    public void setPayrollTaxes(List<PersonPayrollTaxes> payrollTaxes) {
        this.payrollTaxes = payrollTaxes;

    }

    public List<ExpenseReimbursement> getExpenseReimbursements() {
        return expenseReimbursements;
    }

    public void setExpenseReimbursements(List<ExpenseReimbursement> expenseReimbursements) {
        this.expenseReimbursements = expenseReimbursements;
    }
}