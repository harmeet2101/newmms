package com.mbopartners.mbomobile.data.db.generated.model.payroll;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TableNextPaymentDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TablePayrollSummaryDao;

import java.util.ArrayList;
import java.util.Date;

import de.greenrobot.dao.DaoException;

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

    private long nextPaymentRowId;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient TableNextPaymentDao myDao;

    private TablePayrollSummary tablePayrollSummary;
    private Long tablePayrollSummary__resolvedKey;


    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTableNextPaymentDao() : null;
    }
    public long getNextPaymentRowId() {
        return nextPaymentRowId;
    }

    public void setNextPaymentRowId(long nextPaymentRowId) {
        this.nextPaymentRowId = nextPaymentRowId;
    }

    public TablePayrollSummary getTablePayrollSummary() {

        long __key = this.nextPaymentRowId;
        if (tablePayrollSummary__resolvedKey == null || !tablePayrollSummary__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TablePayrollSummaryDao targetDao = daoSession.getTablePayrollSummaryDao();
            TablePayrollSummary tablePayrollSummary = targetDao.load(__key);
            synchronized (this) {
                this.tablePayrollSummary = tablePayrollSummary;
                tablePayrollSummary__resolvedKey = __key;
            }
        }
        return tablePayrollSummary;
    }

    public void setTablePayrollSummary(TablePayrollSummary tablePayrollSummary) {

        if (tablePayrollSummary == null) {
            throw new DaoException("To-one property 'nextPaymentRowId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.tablePayrollSummary = tablePayrollSummary;
            nextPaymentRowId = tablePayrollSummary.getId();
            tablePayrollSummary__resolvedKey = nextPaymentRowId;
        }
        this.tablePayrollSummary = tablePayrollSummary;
    }

    public TableNextPayment(Long id, double amount, String businessCenterId, String calculationMethod, Date endDate
            , Date startDate, String frequency, String nextPaymentId, String mboId,long nextPaymentRowId)
    {
        this.id=id;
        this.amount=amount;

        this.businessCenterId=businessCenterId;
        this.calculationMethod=calculationMethod;
        this.endDate=endDate;
        this.startDate=startDate;
        this.frequency=frequency;
        this.nextPaymentId=nextPaymentId;
        this.nextPaymentRowId=nextPaymentRowId;
        this.mboId=mboId;

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



    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
}
