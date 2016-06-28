package com.mbopartners.mbomobile.ui.model;

import android.util.Log;

import java.util.Date;

public class ExpenseTimesheetItem implements Comparable<ExpenseTimesheetItem> {
    private final String ExpenseMboId;
    private final String mboExpenseTypeId;
    private final String expenseTypeName;
    private final String amount;
    private final String vendor;
    private final boolean editable;
    private final String companyName;
    private final String workOrderName;
    private final Date virtualDate;
    private String lastChangedDate;
    private Date currentDate;

    public String getLastChangedDate() {
        return lastChangedDate;
    }

    public void setLastChangedDate(String lastChangedDate) {
        this.lastChangedDate = lastChangedDate;
    }

    public ExpenseTimesheetItem(String expenseMboId, String mboExpenseTypeId, String expenseTypeName, String amount, String vendor, boolean editable, String companyName, String workOrderName, Date virtualDate) {
        ExpenseMboId = expenseMboId;
        this.mboExpenseTypeId = mboExpenseTypeId;
        this.expenseTypeName = expenseTypeName;
        this.amount = amount;
        this.vendor = vendor;
        this.editable = editable;
        this.companyName = companyName;
        this.workOrderName = workOrderName;
        this.virtualDate = virtualDate;
    }

    public ExpenseTimesheetItem(String expenseMboId, String mboExpenseTypeId, String expenseTypeName, String amount, String vendor, boolean editable, String companyName, String workOrderName, Date virtualDate,String lastChangedDate, Date currentDate) {
        ExpenseMboId = expenseMboId;
        this.mboExpenseTypeId = mboExpenseTypeId;
        this.expenseTypeName = expenseTypeName;
        this.amount = amount;
        this.vendor = vendor;
        this.editable = editable;
        this.companyName = companyName;
        this.workOrderName = workOrderName;
        this.virtualDate = virtualDate;
        this.lastChangedDate=lastChangedDate;
        this.currentDate = currentDate;
    }

    public String getExpenseMboId() {
        return ExpenseMboId;
    }

    public String getMboExpenseTypeId() {
        return mboExpenseTypeId;
    }

    public String getExpenseTypeName() {
        return expenseTypeName;
    }

    public String getAmount() {
        return amount;
    }

    public String getVendor() {
        return vendor;
    }

    public boolean isEditable() {
        return editable;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getWorkOrderName() {
        return workOrderName;
    }

    public Date getVirtualDate() {
        return virtualDate;
    }


    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public String toString()
    {
        return "";
    }

    public int compareTo(ExpenseTimesheetItem item) {
        Log.d("dates", " " + this.getCurrentDate() + " " + item.getCurrentDate());
        if (this.getCurrentDate().before(item.getCurrentDate())) {
            return 1;
        } else if (this.getCurrentDate().after(item.getCurrentDate())) {
            return -1;
        } else
            return 0;
    }
}
