package com.mbopartners.mbomobile.data.db.generated.model.payroll;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TableBusinessWithHoldingDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TablePayrollAmountDao;

import de.greenrobot.dao.DaoException;

/**
 * Created by MboAdil on 13/7/16.
 */
public class TablePayrollAmount {



    private Long id;
    /** Not-null value. */
    private double amount;
    private double amountMtd;
    private double amountYtd;
    private String name;
    private long paymentAmountRowId;
    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient TablePayrollAmountDao myDao;

    private TableBusinessWithHolding tableBusinessWithHolding;
    private Long tableBusinessWithHolding__resolvedKey;

    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTablePayrollAmountDao() : null;
    }

    public TablePayrollAmount(){}

    public TablePayrollAmount(Long id,double amount,double amountMtd,double amountYtd, String name,long paymentAmountRowId)
    {
        this.id=id;
        this.amount=amount;
        this.amountMtd=amountMtd;
        this.amountYtd=amountYtd;
        this.name=name;
        this.paymentAmountRowId=paymentAmountRowId;
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

    public long getPaymentAmountRowId() {
        return paymentAmountRowId;
    }

    public void setPaymentAmountRowId(long paymentAmountRowId) {
        this.paymentAmountRowId = paymentAmountRowId;
    }

    public TableBusinessWithHolding getTableBusinessWithHolding() {
        long __key = this.paymentAmountRowId;
        if (tableBusinessWithHolding__resolvedKey == null || !tableBusinessWithHolding__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TableBusinessWithHoldingDao targetDao = daoSession.getTableBusinessWithHoldingDao();
            TableBusinessWithHolding tableBusinessWithHolding = targetDao.load(__key);
            synchronized (this) {
                tableBusinessWithHolding = tableBusinessWithHolding;
                tableBusinessWithHolding__resolvedKey = __key;
            }
        }
        return tableBusinessWithHolding;
    }

    public void setTableBusinessWithHolding(TableBusinessWithHolding tableBusinessWithHolding) {
        if (tableBusinessWithHolding == null) {
            throw new DaoException("To-one property 'paymentAmountRowId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.tableBusinessWithHolding = tableBusinessWithHolding;
            paymentAmountRowId = tableBusinessWithHolding.getId();
            tableBusinessWithHolding__resolvedKey = paymentAmountRowId;
        }
        this.tableBusinessWithHolding = tableBusinessWithHolding;
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
