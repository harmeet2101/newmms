package com.mbopartners.mbomobile.data.db.generated.model.payroll;

import java.util.Date;

/**
 * Created by MboAdil on 5/7/16.
 */
public class TablePreviousPayment {

    private Long id;
    /** Not-null value. */
    private String businessCenterId;
    private Date date;
    private String previousPaymentId;
    private String mboId;
    private TableBusinessWithHolding tableBusinessWithHolding;
    private TablePersonalWithHolding tablePersonalWithHolding;


    public TablePreviousPayment(){}



    public TablePreviousPayment(Long id, String businessCenterId, Date date, String previousPaymentId,String mboId)
    {
        this.id=id;
        this.businessCenterId=businessCenterId;
        this.date=date;
        this.previousPaymentId=previousPaymentId;
        this.mboId=mboId;

    }

    public TableBusinessWithHolding getTableBusinessWithHolding() {
        return tableBusinessWithHolding;
    }

    public void setTableBusinessWithHolding(TableBusinessWithHolding tableBusinessWithHolding) {
        this.tableBusinessWithHolding = tableBusinessWithHolding;
    }

    public TablePersonalWithHolding getTablePersonalWithHolding() {
        return tablePersonalWithHolding;
    }

    public void setTablePersonalWithHolding(TablePersonalWithHolding tablePersonalWithHolding) {
        this.tablePersonalWithHolding = tablePersonalWithHolding;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessCenterId() {
        return businessCenterId;
    }

    public void setBusinessCenterId(String businessCenterId) {
        this.businessCenterId = businessCenterId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPreviousPaymentId() {
        return previousPaymentId;
    }

    public void setPreviousPaymentId(String previousPaymentId) {
        this.previousPaymentId = previousPaymentId;
    }

    public String getMboId() {
        return mboId;
    }

    public void setMboId(String mboId) {
        this.mboId = mboId;
    }
}
