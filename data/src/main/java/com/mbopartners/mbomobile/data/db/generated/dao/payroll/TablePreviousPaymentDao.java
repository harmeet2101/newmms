package com.mbopartners.mbomobile.data.db.generated.dao.payroll;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TableAmount;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TablePreviousPayment;

import java.util.Date;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * Created by MboAdil on 5/7/16.
 */
public class TablePreviousPaymentDao extends AbstractDao<TablePreviousPayment, Long> {

    public static final String TABLENAME = "TABLE_PREVIOUS_PAYMENT";

    /**
     * Properties of entity TablePreviousPayment.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property BusinessCenterId = new Property(1, String.class, "businessCenterId", false, "businessCenterId");
        public final static Property date = new Property(2, Date.class, "date", false, "date");
        public final static Property previousPaymentId = new Property(3, String.class, "previousPaymentId", false, "previousPaymentId");
        public final static Property mboId = new Property(4, String.class, "mboId", false, "mboId");
    };

    public TablePreviousPaymentDao(DaoConfig config) {
        super(config);
    }

    public TablePreviousPaymentDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TABLE_PREVIOUS_PAYMENT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"businessCenterId\" TEXT NOT NULL,"+// 1: businessCenterId
                "\"date\" INTEGER NOT NULL ," + // 2: date
                "\"previousPaymentId\" TEXT NOT NULL ," + // 3: previousPaymentId
                "\"mboId\" TEXT NOT NULL );" ); // 4: mboId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TABLE_PREVIOUS_PAYMENT\"";
        db.execSQL(sql);
    }
    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TablePreviousPayment entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getBusinessCenterId());
        stmt.bindLong(3, entity.getDate().getTime());
        stmt.bindString(4, entity.getPreviousPaymentId());
        stmt.bindString(5, entity.getMboId());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /** @inheritdoc */
    @Override
    public TablePreviousPayment readEntity(Cursor cursor, int offset) {
        TablePreviousPayment entity = new TablePreviousPayment( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.getString(offset + 1), // businessCenterId
                new Date(cursor.getLong(offset + 2)), // date
                cursor.getString(offset + 3), // previousPaymentId
                cursor.getString(offset + 4)// mboId
        );
        return entity;
    }

    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TablePreviousPayment entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setBusinessCenterId(cursor.getString(offset + 1));
        entity.setDate(new Date(cursor.getLong((offset + 2))));
        entity.setPreviousPaymentId(cursor.getString(offset + 3));
        entity.setMboId(cursor.getString(offset + 4));

    }

    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(TablePreviousPayment entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /** @inheritdoc */
    @Override
    public Long getKey(TablePreviousPayment entity) {
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
