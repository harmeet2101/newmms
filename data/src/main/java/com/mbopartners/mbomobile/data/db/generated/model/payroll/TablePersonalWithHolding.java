package com.mbopartners.mbomobile.data.db.generated.model.payroll;



import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TableExpenseReimbersementsDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TablePersonDepositsDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TablePersonGrossAmountDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TablePersonPayrollTaxesDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TablePersonWithHoldingDao;
import com.mbopartners.mbomobile.data.db.generated.dao.payroll.TablePreviousPaymentDao;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.DaoException;

/**
 * Created by MboAdil on 5/7/16.
 */
public class TablePersonalWithHolding {


    private Long id;
    /** Not-null value. */
    private long personWithHoldingRowId;
    private TablePersonGrossAmount grossAmount;
    private List<TablePersonPayrollTaxes> payrollTaxes;
    private List<TableExpenseReimbersements> expenseReimbursements;
    private List<TablePersonDeposits> deposits;
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient TablePersonWithHoldingDao myDao;

    private TablePreviousPayment tablePreviousPayment;
    private Long tablePreviousPayment__resolvedKey;

    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTablePersonWithHoldingDao() : null;
    }
    public TablePersonalWithHolding(){}

    public TablePersonalWithHolding(Long id, long personWithHoldingRowId)
    {
        this.id=id;
        this.personWithHoldingRowId=personWithHoldingRowId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getPersonWithHoldingRowId() {
        return personWithHoldingRowId;
    }

    public void setPersonWithHoldingRowId(long personWithHoldingRowId) {
        this.personWithHoldingRowId = personWithHoldingRowId;
    }


    public TablePreviousPayment getTablePreviousPayment() {

        long __key = this.personWithHoldingRowId;
        if (tablePreviousPayment__resolvedKey == null || !tablePreviousPayment__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TablePreviousPaymentDao targetDao = daoSession.getTablePreviousPaymentDao();
            TablePreviousPayment tablePreviousPayment = targetDao.load(__key);
            synchronized (this) {

                this.tablePreviousPayment = tablePreviousPayment;
                tablePreviousPayment__resolvedKey = __key;
            }
        }
        return tablePreviousPayment;
    }

    public void setTablePreviousPayment(TablePreviousPayment tablePreviousPayment) {
        if (tablePreviousPayment == null) {
            throw new DaoException("To-one property 'personWithHoldingRowId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.tablePreviousPayment = tablePreviousPayment;
            personWithHoldingRowId = tablePreviousPayment.getId();
            tablePreviousPayment__resolvedKey = personWithHoldingRowId;
        }
        this.tablePreviousPayment = tablePreviousPayment;
    }

    public TablePersonGrossAmount getGrossAmount() {
        if (grossAmount == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TablePersonGrossAmountDao targetDao = daoSession.getTablePersonGrossAmountDao();
            List<TablePersonGrossAmount> FieldsNew = targetDao._queryTableDashboard_Fields(id);
            synchronized (this) {
                if(grossAmount == null&& FieldsNew.size()!=0) {
                    grossAmount = FieldsNew.get(0);
                }
            }
        }
        return grossAmount;
    }

    public void setGrossAmount(TablePersonGrossAmount grossAmount) {
        this.grossAmount = grossAmount;
    }

    public List<TablePersonPayrollTaxes> getPayrollTaxes() {
        if (payrollTaxes == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TablePersonPayrollTaxesDao targetDao = daoSession.getTablePersonPayrollTaxesDao();
            List<TablePersonPayrollTaxes> FieldsNew = targetDao._queryTableDashboard_Fields(id);
            synchronized (this) {
                if(payrollTaxes == null&& FieldsNew.size()!=0) {
                    payrollTaxes = FieldsNew;
                }
            }
        }
        return payrollTaxes;
    }

    public void setPayrollTaxes(List<TablePersonPayrollTaxes> payrollTaxes) {
        this.payrollTaxes = payrollTaxes;
    }

    public List<TableExpenseReimbersements> getExpenseReimbursements() {

        if (expenseReimbursements == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TableExpenseReimbersementsDao targetDao = daoSession.getTableExpenseReimbersementsDao();
            List<TableExpenseReimbersements> FieldsNew = targetDao._queryTableDashboard_Fields(id);
            synchronized (this) {
                if(expenseReimbursements == null&& FieldsNew.size()!=0) {
                    expenseReimbursements = FieldsNew;
                }
            }
        }
        return expenseReimbursements;
    }



    public void setExpenseReimbursements(List<TableExpenseReimbersements> expenseReimbursements) {
        this.expenseReimbursements = expenseReimbursements;
    }

    public List<TablePersonDeposits> getDeposits() {

        if (deposits == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TablePersonDepositsDao targetDao = daoSession.getTablePersonDepositsDao();
            List<TablePersonDeposits> FieldsNew = targetDao._queryTableDashboard_Fields(id);
            synchronized (this) {
                if(deposits == null&& FieldsNew.size()!=0) {
                    deposits = FieldsNew;
                }
            }
        }
        return deposits;
    }

    public void setDeposits(List<TablePersonDeposits> deposits) {
        this.deposits = deposits;
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
