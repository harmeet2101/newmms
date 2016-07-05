package com.mbopartners.mbomobile.data.db.generated.model.payroll;

/**
 * Created by MboAdil on 5/7/16.
 */
public class TableDirectDeposits {

    private Long id;
    /** Not-null value. */
    private int amount;
    private String bankAccountId;
    private int order;
    private boolean payrollRemainderFlag;


    public TableDirectDeposits(){}



    public TableDirectDeposits(Long id,int amount, String bankAccountId, int order, boolean payrollRemainderFlag)
    {
        this.id=id;
        this.amount=amount;
        this.bankAccountId=bankAccountId;
        this.order=order;
        this.payrollRemainderFlag=payrollRemainderFlag;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(String bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isPayrollRemainderFlag() {
        return payrollRemainderFlag;
    }

    public void setPayrollRemainderFlag(boolean payrollRemainderFlag) {
        this.payrollRemainderFlag = payrollRemainderFlag;
    }
}
