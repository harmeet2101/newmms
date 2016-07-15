package com.mbopartners.mbomobile.data.db.generated.model.payroll;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TableBusinessWithHoldingDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TablePayrollSummaryDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TablePersonWithHoldingDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TablePreviousPaymentDao;

import java.util.Date;
import java.util.List;

import de.greenrobot.dao.DaoException;

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
    private TableBusinessWithHolding businessWithholding;
    private TablePersonalWithHolding personalWithholding;
    public long getPreviousPaymentRowId() {
        return previousPaymentRowId;
    }

    public void setPreviousPaymentRowId(long previousPaymentRowId) {
        this.previousPaymentRowId = previousPaymentRowId;
    }

    private long previousPaymentRowId;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient TablePreviousPaymentDao myDao;

    private TablePayrollSummary tablePayrollSummary;
    private Long tablePayrollSummary__resolvedKey;

    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTablePreviousPaymentDao() : null;
    }

    public TablePayrollSummary getTablePayrollSummary() {

        long __key = this.previousPaymentRowId;
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
            throw new DaoException("To-one property 'previousPaymentRowId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.tablePayrollSummary = tablePayrollSummary;
            previousPaymentRowId = tablePayrollSummary.getId();
            tablePayrollSummary__resolvedKey = previousPaymentRowId;
        }
        this.tablePayrollSummary = tablePayrollSummary;
    }
    public TablePreviousPayment(){}



    public TablePreviousPayment(Long id, String businessCenterId, Date date, String previousPaymentId,String mboId,long previousPaymentRowId)
    {
        this.id=id;
        this.businessCenterId=businessCenterId;
        this.date=date;
        this.previousPaymentId=previousPaymentId;
        this.mboId=mboId;
        this.previousPaymentRowId=previousPaymentRowId;

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
