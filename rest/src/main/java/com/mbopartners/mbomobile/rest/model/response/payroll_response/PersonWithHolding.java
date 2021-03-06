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
    @SerializedName("deposits")
    private List<PersonDeposits> deposits=new ArrayList<>();
    @SerializedName("afterTaxDeductions")
    private List<PersonalDeductions> afterTaxDeductions=new ArrayList<>();
    @SerializedName("federalAllowance")
    private String federalAllowance;
    @SerializedName("netAmount")
    private PersonNetAmount netAmount;
    @SerializedName("paycheckAmount")
    private PersonPayCheckAmount    paycheckAmount;

    @Override
    public boolean isValid() {
        boolean result =
                grossAmount!=null && payrollTaxes!=null&& expenseReimbursements!=null && deposits!=null;

        if (! result) {

            ValidationHelper.Screamer screamer = new ValidationHelper.Screamer(TAG, "");
            screamer.sayIfIsNull("grossAmount", grossAmount);
            screamer.sayIfIsNull("payrollTaxes", payrollTaxes);
            screamer.sayIfIsNull("expenseReimbursements", expenseReimbursements);
            screamer.sayIfIsNull("deposits",deposits);

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

    public PersonWithHolding(PersonGrossAmount grossAmount,List<PersonPayrollTaxes> payrollTaxes,List<ExpenseReimbursement> expenseReimbursements
    ,List<PersonDeposits> deposits)
    {
        this.grossAmount=grossAmount;
        this.payrollTaxes=payrollTaxes;
        this.expenseReimbursements=expenseReimbursements;
        this.deposits=deposits;
    }

    public PersonWithHolding(PersonGrossAmount grossAmount,List<PersonPayrollTaxes> payrollTaxes,List<ExpenseReimbursement> expenseReimbursements
            ,List<PersonDeposits> deposits,List<PersonalDeductions> afterTaxDeductions,String federalAllowance)
    {
        this.grossAmount=grossAmount;
        this.payrollTaxes=payrollTaxes;
        this.expenseReimbursements=expenseReimbursements;
        this.deposits=deposits;
        this.afterTaxDeductions=afterTaxDeductions;
        this.federalAllowance=federalAllowance;
    }
    public PersonWithHolding(PersonGrossAmount grossAmount,List<PersonPayrollTaxes> payrollTaxes,List<ExpenseReimbursement> expenseReimbursements
            ,List<PersonDeposits> deposits,List<PersonalDeductions> afterTaxDeductions,String federalAllowance,PersonNetAmount netAmount,PersonPayCheckAmount payCheckAmount)
    {
        this.grossAmount=grossAmount;
        this.payrollTaxes=payrollTaxes;
        this.expenseReimbursements=expenseReimbursements;
        this.deposits=deposits;
        this.afterTaxDeductions=afterTaxDeductions;
        this.federalAllowance=federalAllowance;
        this.netAmount=netAmount;
        this.paycheckAmount=payCheckAmount;
    }



    public String getFederalAllowance() {
        return federalAllowance;
    }

    public void setFederalAllowance(String federalAllowance) {
        this.federalAllowance = federalAllowance;
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

    public List<PersonDeposits> getDeposits() {
        return deposits;
    }

    public void setDeposits(List<PersonDeposits> deposits) {
        this.deposits = deposits;
    }

    public List<PersonalDeductions> getAfterTaxDeductions() {
        return afterTaxDeductions;
    }

    public void setAfterTaxDeductions(List<PersonalDeductions> afterTaxDeductions) {
        this.afterTaxDeductions = afterTaxDeductions;
    }

    public PersonNetAmount getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(PersonNetAmount netAmount) {
        this.netAmount = netAmount;
    }

    public PersonPayCheckAmount getPaycheckAmount() {
        return paycheckAmount;
    }

    public void setPaycheckAmount(PersonPayCheckAmount paycheckAmount) {
        this.paycheckAmount = paycheckAmount;
    }
}
