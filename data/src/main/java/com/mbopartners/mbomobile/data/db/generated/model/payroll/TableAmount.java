package com.mbopartners.mbomobile.data.db.generated.model.payroll;

/**
 * Created by MboAdil on 5/7/16.
 */
public class TableAmount {

    private Long id;
    /** Not-null value. */
    private double amount;
    private double amountMtd;
    private double amountYtd;
    private String name;

    public TableAmount(){}

    public TableAmount(Long id,double amount,double amountMtd,double amountYtd, String name)
    {
        this.id=id;
        this.amount=amount;
        this.amountMtd=amountMtd;
        this.amountYtd=amountYtd;
        this.name=name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmountMtd() {
        return amountMtd;
    }

    public void setAmountMtd(double amountMtd) {
        this.amountMtd = amountMtd;
    }

    public double getAmountYtd() {
        return amountYtd;
    }

    public void setAmountYtd(double amountYtd) {
        this.amountYtd = amountYtd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
