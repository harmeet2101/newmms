package com.mbopartners.mbomobile.data.db.generated.model.payroll;

/**
 * Created by MboAdil on 5/7/16.
 */
public class TablePayrollSummary {

    private Long id;
    /** Not-null value. */
    private TableBusinessAddress businessAddress;
    private double balance;
    private String summaryId;
    private TablePreviousPayment last_payroll;
    private String mboId;
    private String name;
    private TableNextPayment next_payroll;

    public TablePayrollSummary(){}

    public TablePayrollSummary(Long id, TableBusinessAddress businessAddress, double balance, String summaryId,
                               TablePreviousPayment last_payroll, String mboId, String name, TableNextPayment next_payroll)
    {
        this.id=id;
        this.businessAddress=businessAddress;
        this.balance=balance;
        this.summaryId=summaryId;
        this.last_payroll=last_payroll;
        this.mboId=mboId;
        this.name=name;
        this.next_payroll=next_payroll;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TableBusinessAddress getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(TableBusinessAddress businessAddress) {
        this.businessAddress = businessAddress;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getSummaryId() {
        return summaryId;
    }

    public void setSummaryId(String summaryId) {
        this.summaryId = summaryId;
    }

    public TablePreviousPayment getLast_payroll() {
        return last_payroll;
    }

    public void setLast_payroll(TablePreviousPayment last_payroll) {
        this.last_payroll = last_payroll;
    }

    public String getMboId() {
        return mboId;
    }

    public void setMboId(String mboId) {
        this.mboId = mboId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TableNextPayment getNext_payroll() {
        return next_payroll;
    }

    public void setNext_payroll(TableNextPayment next_payroll) {
        this.next_payroll = next_payroll;
    }
}
