package com.mbopartners.mbomobile.data.db.generated.model.payroll;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TableBusinessWithHoldingDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TablePayrollTransactionsDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TablePersonWithHoldingDao;

import java.util.Date;
import java.util.List;

import de.greenrobot.dao.DaoException;

/**
 * Created by MboAdil on 25/7/16.
 */
public class TablePayrollTransactions {


    private Long id;
    private String transactionId;
    private String businessCenterId;
    private String mboId;
    private Date date;
    private TableBusinessWithHolding businessWithholding;
    private TablePersonalWithHolding personalWithholding;

    /** Used to resolve relations */
    private transient DaoSession daoSession;


    /** Used for active entity operations. */
    private transient TablePayrollTransactionsDao myDao;


    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTablePayrollTransactionsDao() : null;
    }

    public TablePayrollTransactions(){}

    public TablePayrollTransactions(Long id, String transactionId,String businessCenterId,String mboId,Date date)
    {
        this.id=id;
        this.mboId=mboId;
        this.date=date;
        this.businessCenterId=businessCenterId;
        this.transactionId=transactionId;
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

    public String getMboId() {
        return mboId;
    }

    public void setMboId(String mboId) {
        this.mboId = mboId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TableBusinessWithHolding getBusinessWithholding() {

        if (businessWithholding == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TableBusinessWithHoldingDao targetDao = daoSession.getTableBusinessWithHoldingDao();
            List<TableBusinessWithHolding> FieldsNew = targetDao._queryTableDashboard_Fields(id);
            synchronized (this) {
                if(businessWithholding == null&& FieldsNew.size()!=0) {
                    businessWithholding = FieldsNew.get(0);
                }
            }
        }
        return businessWithholding;
    }

    public void setBusinessWithholding(TableBusinessWithHolding businessWithholding) {
        this.businessWithholding = businessWithholding;
    }

    public TablePersonalWithHolding getPersonalWithholding() {

        if (personalWithholding == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TablePersonWithHoldingDao targetDao = daoSession.getTablePersonWithHoldingDao();
            List<TablePersonalWithHolding> FieldsNew = targetDao._queryTableDashboard_Fields(id);
            synchronized (this) {
                if(personalWithholding == null&& FieldsNew.size()!=0) {
                    personalWithholding = FieldsNew.get(0);
                }
            }
        }
        return personalWithholding;
    }


    public void setPersonalWithholding(TablePersonalWithHolding personalWithholding) {
        this.personalWithholding = personalWithholding;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }


    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetFields() {
        businessWithholding = null;
        personalWithholding=null;
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
