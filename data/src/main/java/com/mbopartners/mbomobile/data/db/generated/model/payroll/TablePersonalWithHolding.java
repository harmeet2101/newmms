package com.mbopartners.mbomobile.data.db.generated.model.payroll;



import java.util.ArrayList;

/**
 * Created by MboAdil on 5/7/16.
 */
public class TablePersonalWithHolding {


    private Long id;
    /** Not-null value. */
    private ArrayList<TableAmount>  afterTaxDeductions;
    private ArrayList<TableAmount> beforeTaxDeductions;
    private ArrayList<TableAmount> deposits;
    private ArrayList<TableAmount> expenseReimbursements;
    private ArrayList<TableAmount> grossAmount;
    private ArrayList<TableAmount> netAmount;
    private ArrayList<TableAmount> paycheckAmount;
    private ArrayList<TableAmount> payrollTaxes;
    private ArrayList<TableAmount> miscellaneousDeductions;
    private String federalAllowance,federalStatus,livedInState,workedInState;

    public TablePersonalWithHolding(){}

    public TablePersonalWithHolding(Long id, ArrayList<TableAmount> afterTaxDeductions, ArrayList<TableAmount> beforeTaxDeductions
            , ArrayList<TableAmount> deposits, ArrayList<TableAmount> expenseReimbursements, ArrayList<TableAmount> grossAmount
            , ArrayList<TableAmount> netAmount, ArrayList<TableAmount> paycheckAmount, ArrayList<TableAmount> payrollTaxes
            , ArrayList<TableAmount> miscellaneousDeductions, String federalAllowance, String federalStatus
            , String livedInState, String workedInState)
    {
      this.id=id;
        this.afterTaxDeductions=afterTaxDeductions;
        this.beforeTaxDeductions=beforeTaxDeductions;
        this.deposits=deposits;
        this.expenseReimbursements=expenseReimbursements;
        this.grossAmount=grossAmount;
        this.netAmount=netAmount;
        this.paycheckAmount=paycheckAmount;
        this.payrollTaxes=payrollTaxes;
        this.miscellaneousDeductions=miscellaneousDeductions;
        this.federalAllowance=federalAllowance;
        this.federalStatus=federalStatus;
        this.livedInState=livedInState;
        this.workedInState=workedInState;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArrayList<TableAmount> getAfterTaxDeductions() {
        return afterTaxDeductions;
    }

    public void setAfterTaxDeductions(ArrayList<TableAmount> afterTaxDeductions) {
        this.afterTaxDeductions = afterTaxDeductions;
    }

    public ArrayList<TableAmount> getBeforeTaxDeductions() {
        return beforeTaxDeductions;
    }

    public void setBeforeTaxDeductions(ArrayList<TableAmount> beforeTaxDeductions) {
        this.beforeTaxDeductions = beforeTaxDeductions;
    }

    public ArrayList<TableAmount> getDeposits() {
        return deposits;
    }

    public void setDeposits(ArrayList<TableAmount> deposits) {
        this.deposits = deposits;
    }

    public ArrayList<TableAmount> getExpenseReimbursements() {
        return expenseReimbursements;
    }

    public void setExpenseReimbursements(ArrayList<TableAmount> expenseReimbursements) {
        this.expenseReimbursements = expenseReimbursements;
    }

    public ArrayList<TableAmount> getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(ArrayList<TableAmount> grossAmount) {
        this.grossAmount = grossAmount;
    }

    public ArrayList<TableAmount> getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(ArrayList<TableAmount> netAmount) {
        this.netAmount = netAmount;
    }

    public ArrayList<TableAmount> getPaycheckAmount() {
        return paycheckAmount;
    }

    public void setPaycheckAmount(ArrayList<TableAmount> paycheckAmount) {
        this.paycheckAmount = paycheckAmount;
    }

    public ArrayList<TableAmount> getPayrollTaxes() {
        return payrollTaxes;
    }

    public void setPayrollTaxes(ArrayList<TableAmount> payrollTaxes) {
        this.payrollTaxes = payrollTaxes;
    }

    public ArrayList<TableAmount> getMiscellaneousDeductions() {
        return miscellaneousDeductions;
    }

    public void setMiscellaneousDeductions(ArrayList<TableAmount> miscellaneousDeductions) {
        this.miscellaneousDeductions = miscellaneousDeductions;
    }

    public String getFederalAllowance() {
        return federalAllowance;
    }

    public void setFederalAllowance(String federalAllowance) {
        this.federalAllowance = federalAllowance;
    }

    public String getFederalStatus() {
        return federalStatus;
    }

    public void setFederalStatus(String federalStatus) {
        this.federalStatus = federalStatus;
    }

    public String getLivedInState() {
        return livedInState;
    }

    public void setLivedInState(String livedInState) {
        this.livedInState = livedInState;
    }

    public String getWorkedInState() {
        return workedInState;
    }

    public void setWorkedInState(String workedInState) {
        this.workedInState = workedInState;
    }
}
