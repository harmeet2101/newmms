package com.mbopartners.mbomobile.data.db.generated.model.payroll;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TableNextPaymentDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TablePayrollSummaryDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TablePreviousPaymentDao;

import java.util.List;

import de.greenrobot.dao.DaoException;

/**
 * Created by MboAdil on 5/7/16.
 */
public class TablePayrollSummary {

    private Long id;
    /** Not-null value. */
    private double balance;
    private String summaryId;
    private String mboId;
    private String name;
    private TableNextPayment next_payroll;
    private TablePreviousPayment last_payroll;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient TablePayrollSummaryDao myDao;
    private Long tableNextPayment__resolvedKey;

    public TablePayrollSummary(){}

    public TablePayrollSummary(Long id, double balance, String summaryId,String mboId, String name)
    {
        this.id=id;

        this.balance=balance;
        this.summaryId=summaryId;
        this.mboId=mboId;
        this.name=name;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTablePayrollSummaryDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TablePreviousPayment getLast_payroll() {

        if (last_payroll == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TablePreviousPaymentDao targetDao = daoSession.getTablePreviousPaymentDao();
            List<TablePreviousPayment> FieldsNew = targetDao._queryTableDashboard_Fields(id);
            synchronized (this) {
                if(last_payroll == null) {
                    last_payroll = FieldsNew.get(0);
                }
            }
        }
        return last_payroll;
    }

    public void setLast_payroll(TablePreviousPayment last_payroll) {
        this.last_payroll = last_payroll;
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
        if (next_payroll == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TableNextPaymentDao targetDao = daoSession.getTableNextPaymentDao();
            List<TableNextPayment> FieldsNew = targetDao._queryTableDashboard_Fields(id);
            synchronized (this) {
                if(next_payroll == null) {
                    next_payroll = FieldsNew.get(0);
                }
            }
        }

        return next_payroll;
    }

    public void setNext_payroll(TableNextPayment next_payroll) {
        this.next_payroll = next_payroll;
    }



    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetFields() {
        next_payroll = null;
        last_payroll=null;
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
