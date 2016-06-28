package com.mbopartners.mbomobile.data.db.generated.model;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import de.greenrobot.dao.DaoException;

import com.mbopartners.mbomobile.data.db.generated.dao.TableTimeTaskDao;
import com.mbopartners.mbomobile.data.db.generated.dao.TableWorkOrderDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "TABLE_TIME_TASK".
 */
public class TableTimeTask {

    private Long id;
    /** Not-null value. */
    private String MboId;
    /** Not-null value. */
    private String Name;
    private long WorkOrderId;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient TableTimeTaskDao myDao;

    private TableWorkOrder tableWorkOrder;
    private Long tableWorkOrder__resolvedKey;


    public TableTimeTask() {
    }

    public TableTimeTask(Long id) {
        this.id = id;
    }

    public TableTimeTask(Long id, String MboId, String Name, long WorkOrderId) {
        this.id = id;
        this.MboId = MboId;
        this.Name = Name;
        this.WorkOrderId = WorkOrderId;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTableTimeTaskDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getMboId() {
        return MboId;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setMboId(String MboId) {
        this.MboId = MboId;
    }

    /** Not-null value. */
    public String getName() {
        return Name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setName(String Name) {
        this.Name = Name;
    }

    public long getWorkOrderId() {
        return WorkOrderId;
    }

    public void setWorkOrderId(long WorkOrderId) {
        this.WorkOrderId = WorkOrderId;
    }

    /** To-one relationship, resolved on first access. */
    public TableWorkOrder getTableWorkOrder() {
        long __key = this.WorkOrderId;
        if (tableWorkOrder__resolvedKey == null || !tableWorkOrder__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TableWorkOrderDao targetDao = daoSession.getTableWorkOrderDao();
            TableWorkOrder tableWorkOrderNew = targetDao.load(__key);
            synchronized (this) {
                tableWorkOrder = tableWorkOrderNew;
            	tableWorkOrder__resolvedKey = __key;
            }
        }
        return tableWorkOrder;
    }

    public void setTableWorkOrder(TableWorkOrder tableWorkOrder) {
        if (tableWorkOrder == null) {
            throw new DaoException("To-one property 'WorkOrderId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.tableWorkOrder = tableWorkOrder;
            WorkOrderId = tableWorkOrder.getId();
            tableWorkOrder__resolvedKey = WorkOrderId;
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
