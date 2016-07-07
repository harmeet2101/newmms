package com.mbopartners.mbomobile.data.db.generated.dao.payroll;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TableNextPayment;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TablePayrollSummary;

import java.util.Date;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * Created by MboAdil on 7/7/16.
 */
public class TablePayrollSummaryDao extends AbstractDao<TablePayrollSummary, Long> {

    public static final String TABLENAME = "TABLE_SUMMARY";

    /**
     * Properties of entity TablePayrollSummary.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property balance = new Property(1, Double.class, "balance", false, "balance");
        public final static Property summaryId = new Property(2, String.class, "summaryId", false, "summaryId");
        public final static Property mboId = new Property(3, String.class, "mboId", false, "mboId");
        public final static Property name = new Property(4, String.class, "name", false, "name");

    };

    public TablePayrollSummaryDao(DaoConfig config) {
        super(config);
    }

    public TablePayrollSummaryDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TABLE_SUMMARY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," +     // 0: id
                "\"balance\" REAL NOT NULL,"+                     // 1: amount
                "\"summaryId\" TEXT NOT NULL ," + // 2: businessCenterId
                "\"mboId\" TEXT NOT NULL ," + // 3: calculationMethod
                "\"name\" TEXT NOT NULL);");           // 8: mboId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TABLE_SUMMARY\"";
        db.execSQL(sql);
    }
    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TablePayrollSummary entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindDouble(2, entity.getBalance());
        stmt.bindString(3, entity.getSummaryId());
        stmt.bindString(4, entity.getMboId());
        stmt.bindString(5, entity.getName());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /** @inheritdoc */
    @Override
    public TablePayrollSummary readEntity(Cursor cursor, int offset) {


        TablePayrollSummary entity = new TablePayrollSummary(
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0),
                cursor.getDouble(offset + 1),
                cursor.getString(offset + 2),
                cursor.getString(offset + 3),
                cursor.getString(offset + 4)
        );
        return entity;
    }

    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TablePayrollSummary entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setBalance(cursor.getDouble(offset + 1));
        entity.setSummaryId(cursor.getString(offset + 2));
        entity.setMboId(cursor.getString(offset + 3));
        entity.setName(cursor.getString(offset + 4));
    }

    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(TablePayrollSummary entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /** @inheritdoc */
    @Override
    public Long getKey(TablePayrollSummary entity) {
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
