package com.mbopartners.mbomobile.data.db.generated.model.payroll;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TablePersonAfterDeductionsDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TablePersonWithHoldingDao;

import de.greenrobot.dao.DaoException;

/**
 * Created by MboAdil on 28/7/16.
 */
public class TablePersonAfterDeductions {


    private Long id;
    /** Not-null value. */
    private double amount;
    private String name;
    private double amountMtd;
    private double amountYtd;
    private long afterDeductionsRowId;
    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient TablePersonAfterDeductionsDao myDao;



    private TablePersonalWithHolding tablePersonalWithHolding;
    private Long tablePersonWithHolding__resolvedKey;

    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTablePersonAfterDeductionsDao() : null;
    }

    public TablePersonAfterDeductions(){}

    public TablePersonAfterDeductions(Long id, Double amount, Double amountMtd,Double amountYtd,String name, long afterDeductionsRowId)
    {
        this.id=id;
        this.amount=amount;
        this.name=name;
        this.afterDeductionsRowId=afterDeductionsRowId;
        this.amountMtd=amountMtd;
        this.amountYtd=amountYtd;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPaymentAmountRowId() {
        return afterDeductionsRowId;
    }

    public void setPaymentAmountRowId(long afterDeductionsRowId) {
        this.afterDeductionsRowId = afterDeductionsRowId;
    }


    public TablePersonalWithHolding getTablePersonalWithHolding() {

        long __key = this.afterDeductionsRowId;
        if (tablePersonWithHolding__resolvedKey == null || !tablePersonWithHolding__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TablePersonWithHoldingDao targetDao = daoSession.getTablePersonWithHoldingDao();
            TablePersonalWithHolding tablePersonalWithHolding = targetDao.load(__key);
            synchronized (this) {
                this.tablePersonalWithHolding = tablePersonalWithHolding;
                tablePersonWithHolding__resolvedKey = __key;
            }
        }
        return tablePersonalWithHolding;
    }

    public void setTablePersonalWithHolding(TablePersonalWithHolding tablePersonalWithHolding) {
        if (tablePersonalWithHolding == null) {
            throw new DaoException("To-one property 'depositsRowId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.tablePersonalWithHolding = tablePersonalWithHolding;
            afterDeductionsRowId = tablePersonalWithHolding.getId();
            tablePersonWithHolding__resolvedKey = afterDeductionsRowId;
        }
        this.tablePersonalWithHolding = tablePersonalWithHolding;
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
