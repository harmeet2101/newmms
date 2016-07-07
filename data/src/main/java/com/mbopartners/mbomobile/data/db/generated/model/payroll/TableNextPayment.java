package com.mbopartners.mbomobile.data.db.generated.model.payroll;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by MboAdil on 5/7/16.
 */
public class TableNextPayment {

    private Long id;
    /** Not-null value. */
    private double amount;
    private String businessCenterId;
    private String calculationMethod;
    private Date endDate,startDate;
    private String frequency;
    private String nextPaymentId;
    private String mboId;
    /*private ArrayList<TableDirectDeposits> tableDirectDeposits;
    private ArrayList<TablePayrollDates> payrollDates;*/

    public TableNextPayment(){}

    public TableNextPayment(Long id)
    {
        this.id=id;
    }

    /*public ArrayList<TableDirectDeposits> getTableDirectDeposits() {
        return tableDirectDeposits;
    }

    public void setTableDirectDeposits(ArrayList<TableDirectDeposits> tableDirectDeposits) {
        this.tableDirectDeposits = tableDirectDeposits;
    }*/

    public TableNextPayment(Long id, double amount, String businessCenterId, String calculationMethod, Date endDate
            , Date startDate, String frequency, String nextPaymentId, String mboId)
    {
        this.id=id;
        this.amount=amount;
        this.businessCenterId=businessCenterId;
        this.calculationMethod=calculationMethod;
        this.endDate=endDate;
        this.startDate=startDate;
        this.frequency=frequency;
        this.nextPaymentId=nextPaymentId;
        this.mboId=mboId;

    }

    /*public ArrayList<TablePayrollDates> getPayrollDates() {
        return payrollDates;
    }

    public void setPayrollDates(ArrayList<TablePayrollDates> payrollDates) {
        this.payrollDates = payrollDates;
    }*/

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

    public String getBusinessCenterId() {
        return businessCenterId;
    }

    public void setBusinessCenterId(String businessCenterId) {
        this.businessCenterId = businessCenterId;
    }

    public String getCalculationMethod() {
        return calculationMethod;
    }

    public void setCalculationMethod(String calculationMethod) {
        this.calculationMethod = calculationMethod;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getNextPaymentId() {
        return nextPaymentId;
    }

    public void setNextPaymentId(String nextPaymentId) {
        this.nextPaymentId = nextPaymentId;
    }

    public String getMboId() {
        return mboId;
    }

    public void setMboId(String mboId) {
        this.mboId = mboId;
    }
}
