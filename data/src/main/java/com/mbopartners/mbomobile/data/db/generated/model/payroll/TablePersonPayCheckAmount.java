package com.mbopartners.mbomobile.data.db.generated.model.payroll;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TablePersonPayCheckAmountDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TablePersonWithHoldingDao;

import de.greenrobot.dao.DaoException;

/**
 * Created by MboAdil on 4/8/16.
 */
public class TablePersonPayCheckAmount {

    private Long id;
    /** Not-null value. */
    private double amount;
    private double amountMtd;
    private double amountYtd;
    private String name;
    private long payCheckAmountRowId;
    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient TablePersonPayCheckAmountDao myDao;



    private TablePersonalWithHolding tablePersonalWithHolding;
    private Long tablePersonWithHolding__resolvedKey;

    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTablePersonPayCheckAmountDao() : null;

    }

    public TablePersonPayCheckAmount(){}

    public TablePersonPayCheckAmount(Long id,double amount,double amountMtd,double amountYtd, String name,long payCheckAmountRowId)
    {
        this.id=id;
        this.amount=amount;
        this.amountMtd=amountMtd;
        this.amountYtd=amountYtd;
        this.name=name;
        this.payCheckAmountRowId=payCheckAmountRowId;
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
        return payCheckAmountRowId;
    }

    public void setPaymentAmountRowId(long grossAmountRowId) {
        this.payCheckAmountRowId = grossAmountRowId;
    }


    public TablePersonalWithHolding getTablePersonalWithHolding() {

        long __key = this.payCheckAmountRowId;
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
