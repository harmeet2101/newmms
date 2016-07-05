package com.mbopartners.mbomobile.data.db.generated.dao.payroll;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TableAmount;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TablePayrollDates;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * Created by MboAdil on 5/7/16.
 */
public class TablePayrollDatesDao extends AbstractDao<TablePayrollDates, Long> {


    public static final String TABLENAME = "TABLE_PAYROLL_DATES";

    /**
     * Properties of entity TablePayrollDates.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property BusinessCenterId = new Property(1, java.util.Date.class, "date", false, "date");

    };

    public TablePayrollDatesDao(DaoConfig config) {
        super(config);
    }

    public TablePayrollDatesDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TABLE_PAYROLL_DATES\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"date\" INTEGER NOT NULL);"); // 1: date
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TABLE_PAYROLL_DATES\"";
        db.execSQL(sql);
    }
    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TablePayrollDates entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getDate().getTime());

    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /** @inheritdoc */
    @Override
    public TablePayrollDates readEntity(Cursor cursor, int offset) {
        TablePayrollDates entity = new TablePayrollDates( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                new java.util.Date(cursor.getLong(offset + 1)  // date
        ));
        return entity;
    }

    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TablePayrollDates entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setDate(new java.util.Date(cursor.getInt(offset + 1)));

    }

    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(TablePayrollDates entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /** @inheritdoc */
    @Override
    public Long getKey(TablePayrollDates entity) {
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
