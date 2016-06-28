package com.mbopartners.mbomobile.data.db.generated.model;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import de.greenrobot.dao.DaoException;

import com.mbopartners.mbomobile.data.db.generated.dao.TableExpenseDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableReceiptDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "TABLE_RECEIPT".
 */
public class TableReceipt {

    private Long id;
    private int RecState;
    /** Not-null value. */
    private String Filename;
    /** Not-null value. */
    private String MboExpenseId;
    /** Not-null value. */
    private java.util.Date CreationDate;
    private String ThumbnailPath;
    private Double Version;
    private long ExpenseId;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient TableReceiptDao myDao;

    private TableExpense tableExpense;
    private Long tableExpense__resolvedKey;


    public TableReceipt() {
    }

    public TableReceipt(Long id) {
        this.id = id;
    }

    public TableReceipt(Long id, int RecState, String Filename, String MboExpenseId, java.util.Date CreationDate, String ThumbnailPath, Double Version, long ExpenseId) {
        this.id = id;
        this.RecState = RecState;
        this.Filename = Filename;
        this.MboExpenseId = MboExpenseId;
        this.CreationDate = CreationDate;
        this.ThumbnailPath = ThumbnailPath;
        this.Version = Version;
        this.ExpenseId = ExpenseId;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTableReceiptDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRecState() {
        return RecState;
    }

    public void setRecState(int RecState) {
        this.RecState = RecState;
    }

    /** Not-null value. */
    public String getFilename() {
        return Filename;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setFilename(String Filename) {
        this.Filename = Filename;
    }

    /** Not-null value. */
    public String getMboExpenseId() {
        return MboExpenseId;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setMboExpenseId(String MboExpenseId) {
        this.MboExpenseId = MboExpenseId;
    }

    /** Not-null value. */
    public java.util.Date getCreationDate() {
        return CreationDate;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setCreationDate(java.util.Date CreationDate) {
        this.CreationDate = CreationDate;
    }

    public String getThumbnailPath() {
        return ThumbnailPath;
    }

    public void setThumbnailPath(String ThumbnailPath) {
        this.ThumbnailPath = ThumbnailPath;
    }

    public Double getVersion() {
        return Version;
    }

    public void setVersion(Double Version) {
        this.Version = Version;
    }

    public long getExpenseId() {
        return ExpenseId;
    }

    public void setExpenseId(long ExpenseId) {
        this.ExpenseId = ExpenseId;
    }

    /** To-one relationship, resolved on first access. */
    public TableExpense getTableExpense() {
        long __key = this.ExpenseId;
        if (tableExpense__resolvedKey == null || !tableExpense__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TableExpenseDao targetDao = daoSession.getTableExpenseDao();
            TableExpense tableExpenseNew = targetDao.load(__key);
            synchronized (this) {
                tableExpense = tableExpenseNew;
            	tableExpense__resolvedKey = __key;
            }
        }
        return tableExpense;
    }

    public void setTableExpense(TableExpense tableExpense) {
        if (tableExpense == null) {
            throw new DaoException("To-one property 'ExpenseId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.tableExpense = tableExpense;
            ExpenseId = tableExpense.getId();
            tableExpense__resolvedKey = ExpenseId;
        }
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
