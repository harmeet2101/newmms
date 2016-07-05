package com.mbopartners.mbomobile.data.db.generated.model.payroll;

import java.util.ArrayList;

/**
 * Created by MboAdil on 5/7/16.
 */
public class TableBusinessWithHolding {

    private Long  id;
    /** Not-null value. */
    private ArrayList<TableAmount>  businessExpenses;
    private ArrayList<TableAmount> payrollAmount;
    private ArrayList<TableAmount> payrollTaxes;


    public TableBusinessWithHolding(){}


    public TableBusinessWithHolding(Long id, ArrayList<TableAmount> businessExpenses, ArrayList<TableAmount> payrollAmount
            , ArrayList<TableAmount> payrollTaxes)
    {
        this.id=id;
        this.businessExpenses=businessExpenses;
        this.payrollAmount=payrollAmount;
        this.payrollTaxes=payrollTaxes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArrayList<TableAmount> getBusinessExpenses() {
        return businessExpenses;
    }

    public void setBusinessExpenses(ArrayList<TableAmount> businessExpenses) {
        this.businessExpenses = businessExpenses;
    }

    public ArrayList<TableAmount> getPayrollAmount() {
        return payrollAmount;
    }

    public void setPayrollAmount(ArrayList<TableAmount> payrollAmount) {
        this.payrollAmount = payrollAmount;
    }

    public ArrayList<TableAmount> getPayrollTaxes() {
        return payrollTaxes;
    }

    public void setPayrollTaxes(ArrayList<TableAmount> payrollTaxes) {
        this.payrollTaxes = payrollTaxes;
    }
}
