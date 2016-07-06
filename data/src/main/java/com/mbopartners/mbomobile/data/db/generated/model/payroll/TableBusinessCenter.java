package com.mbopartners.mbomobile.data.db.generated.model.payroll;

/**
 * Created by MboAdil on 1/7/16.
 */
public class TableBusinessCenter {

    private Long id;
    /** Not-null value. */
    private String businessId;
    private String name;
    private String mboId;
    private double balance;




    public TableBusinessCenter(Long id)
    {
        this.id=id;
    }

    public TableBusinessCenter(Long id,String businessId,String name,String mboId,double balance)
    {
        this.id=id;
        this.businessId=businessId;
        this.name=name;
        this.mboId=mboId;
        this.balance=balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMboId() {
        return mboId;
    }

    public void setMboId(String mboId) {
        this.mboId = mboId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }
}
