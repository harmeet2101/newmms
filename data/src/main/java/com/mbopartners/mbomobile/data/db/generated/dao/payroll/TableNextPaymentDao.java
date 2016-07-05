package com.mbopartners.mbomobile.data.db.generated.dao.payroll;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TableAmount;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TableNextPayment;

import java.util.Date;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * Created by MboAdil on 5/7/16.
 */
public class TableNextPaymentDao extends AbstractDao<TableNextPayment, Long> {

    public static final String TABLENAME = "TABLE_NEXT_PAYMENT";

    /**
     * Properties of entity TableNextPayment.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property amount = new Property(1, Double.class, "amount", false, "amount");
        public final static Property BusinessCenterId = new Property(2, String.class, "businessCenterId", false, "businessCenterId");
        public final static Property calculationMethod = new Property(3, String.class, "calculationMethod", false, "calculationMethod");
        public final static Property endDate = new Property(4, Date.class, "endDate", false, "endDate");
        public final static Property startDate = new Property(5, Date.class, "startDate", false, "startDate");
        public final static Property frequency = new Property(6, String.class, "frequency", false, "frequency");
        public final static Property nextPaymentId = new Property(7, String.class, "nextPaymentId", false, "nextPaymentId");
        public final static Property mboId = new Property(8, String.class, "mboId", false, "mboId");
    };

    public TableNextPaymentDao(DaoConfig config) {
        super(config);
    }

    public TableNextPaymentDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TABLE_NEXT_PAYMENT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," +     // 0: id
                "\"amount\" REAL NOT NULL,"+                     // 1: amount
                "\"businessCenterId\" TEXT NOT NULL ," + // 2: businessCenterId
                "\"calculationMethod\" TEXT NOT NULL ," + // 3: calculationMethod
                "\"endDate\" INTEGER NOT NULL,"+          // 4.endDate
                "\"startDate\" INTEGER NOT NULL,"+        //5.startDate
                "\"frequency\" TEXT NOT NULL,"+           // 6.frequency
                "\"nextPaymentId\" TEXT NOT NULL,"+       //   7.nextPaymentId
                "\"mboId\" TEXT NOT NULL, );");           // 8: mboId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TABLE_NEXT_PAYMENT\"";
        db.execSQL(sql);
    }
    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TableNextPayment entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindDouble(2, entity.getAmount());
        stmt.bindString(3, entity.getBusinessCenterId());
        stmt.bindString(4, entity.getCalculationMethod());
        stmt.bindLong(5, entity.getEndDate().getTime());
        stmt.bindLong(6,entity.getStartDate().getTime());
        stmt.bindString(7, entity.getFrequency());
        stmt.bindString(8, entity.getNextPaymentId());
        stmt.bindString(9, entity.getMboId());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /** @inheritdoc */
    @Override
    public TableNextPayment readEntity(Cursor cursor, int offset) {
        

        TableNextPayment entity = new TableNextPayment(
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0),
                cursor.getLong(offset + 1),
                cursor.getString(offset + 2),
                cursor.getString(offset + 3),
                new Date(cursor.getLong(offset + 4)),
                new Date(cursor.getLong(offset + 5)),
                cursor.getString(offset + 6),
                cursor.getString(offset + 7),
                cursor.getString(offset + 8)

        );
        return entity;
    }

    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TableNextPayment entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setAmount(cursor.getInt(offset + 1));
        entity.setBusinessCenterId(cursor.getString(offset + 2));
        entity.setCalculationMethod(cursor.getString(offset + 3));
        entity.setEndDate(new java.util.Date(cursor.getLong(offset + 4)));
        entity.setStartDate(new java.util.Date(cursor.getLong(offset + 5)));
        entity.setFrequency(cursor.getString(offset + 6));
        entity.setNextPaymentId(cursor.getString(offset + 7));
        entity.setMboId(cursor.getString(offset + 8));
    }

    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(TableNextPayment entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /** @inheritdoc */
    @Override
    public Long getKey(TableNextPayment entity) {
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
