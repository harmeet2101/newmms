package com.mbopartners.mbomobile.data.db.generated.model.payroll;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TableBusinessWithHoldingDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TablePreviousPaymentDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TableSummaryBusinessWithHoldingDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TableSummaryPayrollAmountDao;

import java.util.List;

import de.greenrobot.dao.DaoException;

/**
 * Created by MboAdil on 27/7/16.
 */
public class TableSummaryBusinessWithHolding {

    private Long  id;
    /** Not-null value. */
    private TableSummaryPayrollAmount payrollAmount;
   /* private List<TableBusinessExpenses> businessExpenses;
    private List<TableBusinessPayrollTaxes> payrollTaxes;*/

    private long businessWithHoldingRowId;


    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient TableSummaryBusinessWithHoldingDao myDao;

    private TablePreviousPayment tablePreviousPayment;
    private Long tablePreviousPayment__resolvedKey;

    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTableSummaryBusinessWithHoldingDao() : null;
    }



    public TablePreviousPayment getTablePreviousPayment() {

        long __key = this.businessWithHoldingRowId;
        if (tablePreviousPayment__resolvedKey == null || !tablePreviousPayment__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TablePreviousPaymentDao targetDao = daoSession.getTablePreviousPaymentDao();
            TablePreviousPayment tablePreviousPayment = targetDao.load(__key);
            synchronized (this) {

                tablePreviousPayment = tablePreviousPayment;
                tablePreviousPayment__resolvedKey = __key;
            }
        }
        return tablePreviousPayment;
    }

    public void setTablePreviousPayment(TablePreviousPayment tablePreviousPayment) {
        if (tablePreviousPayment == null) {
            throw new DaoException("To-one property 'businessWithHoldingRowId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.tablePreviousPayment = tablePreviousPayment;
            businessWithHoldingRowId = tablePreviousPayment.getId();
            tablePreviousPayment__resolvedKey = businessWithHoldingRowId;
        }
        this.tablePreviousPayment = tablePreviousPayment;
    }

    public long getBusinessWithHoldingRowId() {
        return businessWithHoldingRowId;
    }

    public void setBusinessWithHoldingRowId(long businessWithHoldingRowId) {
        this.businessWithHoldingRowId = businessWithHoldingRowId;
    }

    public TableSummaryBusinessWithHolding(){}


    public TableSummaryBusinessWithHolding(Long id, long businessWithHoldingRowId)
    {
        this.id=id;
        this.businessWithHoldingRowId=businessWithHoldingRowId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TableSummaryPayrollAmount getPayrollAmount() {

        if (payrollAmount == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TableSummaryPayrollAmountDao targetDao = daoSession.getTableSummaryPayrollAmountDao();
            List<TableSummaryPayrollAmount> FieldsNew = targetDao._queryTableDashboard_Fields(id);
            synchronized (this) {
                if(payrollAmount == null&& FieldsNew.size()!=0) {
                    payrollAmount = FieldsNew.get(0);
                }
            }
        }
        return payrollAmount;
    }

    public void setPayrollAmount(TableSummaryPayrollAmount payrollAmount) {
        this.payrollAmount = payrollAmount;
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
