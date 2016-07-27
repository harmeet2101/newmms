package com.mbopartners.mbomobile.data.db.generated.dao.payroll;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TablePayrollTransactions;

import java.util.Date;
import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * Created by MboAdil on 25/7/16.
 */
public class TablePayrollTransactionsDao extends AbstractDao<TablePayrollTransactions,Long> {


    public static final String TABLENAME = "TABLE_TRANSACTIONS";
    private DaoSession daoSession;
    /**
     * Properties of entity TablePayrollTransactions.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property summaryId = new Property(1, String.class, "transactionId", false, "transactionId");
        public final static Property mboId = new Property(2, String.class, "mboId", false, "mboId");
        public final static Property businessCenterId = new Property(3, String.class, "businessCenterId", false, "businessCenterId");
        public final static Property date = new Property(4, Date.class, "date", false, "date");
    };


    public TablePayrollTransactionsDao(DaoConfig config) {
        super(config);
    }

    public TablePayrollTransactionsDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession=daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TABLE_TRANSACTIONS\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," +     // 0: id
                "\"transactionId\" TEXT NOT NULL ," +
                "\"mboId\" TEXT NOT NULL," +
                "\"businessCenterId\" TEXT NOT NULL ," +
                "\"date\" INTEGER NOT NULL);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TABLE_TRANSACTIONS\"";
        db.execSQL(sql);
    }

    @Override
    protected void attachEntity(TablePayrollTransactions entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TablePayrollTransactions entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2,entity.getTransactionId());
        stmt.bindString(3, entity.getMboId());
        stmt.bindString(4, entity.getBusinessCenterId());
        stmt.bindLong(5, entity.getDate().getTime());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /** @inheritdoc */
    @Override
    public TablePayrollTransactions readEntity(Cursor cursor, int offset) {


        TablePayrollTransactions entity = new TablePayrollTransactions(
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0),
                cursor.getString(offset + 1),
                cursor.getString(offset + 2),
                cursor.getString(offset+3),
                new Date(cursor.getLong(offset + 4))

        );
        return entity;
    }

    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TablePayrollTransactions entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTransactionId(cursor.getString(offset + 1));
        entity.setMboId(cursor.getString(offset + 2));
        entity.setBusinessCenterId(cursor.getString(offset + 3));
        entity.setDate(new java.util.Date(cursor.getLong(offset + 4)));
    }

    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(TablePayrollTransactions entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /** @inheritdoc */
    @Override
    public Long getKey(TablePayrollTransactions entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }


}
