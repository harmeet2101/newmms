package com.mbopartners.mbomobile.data.db.generated.model.payroll;

/**
 * Created by MboAdil on 1/7/16.
 */
public class TableBusinessCenter {

    private long id;
    /** Not-null value. */
    private String name;
    private String mboId;
    private String balance;


    public TableBusinessCenter(){}

    public TableBusinessCenter(long id)
    {
        this.id=id;
    }

    public TableBusinessCenter(long id,String name,String mboId,String balance)
    {
        this.id=id;
        this.name=name;
        this.mboId=mboId;
        this.balance=balance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
