package com.mbopartners.mbomobile.data.db.generated.dao.payroll;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TableAmount;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TableDirectDeposits;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * Created by MboAdil on 5/7/16.
 */
public class TableAmountDao extends AbstractDao<TableAmount, Long> {


    public static final String TABLENAME = "TABLE_AMOUNT";

    /**
     * Properties of entity TableAmount.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property BusinessCenterId = new Property(1, Double.class, "amount", false, "amount");
        public final static Property Name = new Property(2, Double.class, "amountMtd", false, "amountMtd");
        public final static Property MboId = new Property(3, Double.class, "amountYtd", false, "amountYtd");
        public final static Property Balance = new Property(4, String.class, "name", false, "name");
    };

    public TableAmountDao(DaoConfig config) {
        super(config);
    }

    public TableAmountDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TABLE_AMOUNT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"amount\" REAL NOT NULL,"+// 1: amount
                "\"amountMtd\" REAL NOT NULL ," + // 2: amountMtd
                "\"amountYtd\" REAL NOT NULL ," + // 3: amountYtd
                "\"name\" TEXT NOT NULL );" ); // 4: name
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TABLE_AMOUNT\"";
        db.execSQL(sql);
    }
    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TableAmount entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindDouble(2, entity.getAmount());
        stmt.bindDouble(3, entity.getAmountMtd());
        stmt.bindDouble(4, entity.getAmountYtd());
        stmt.bindString(5, entity.getName());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /** @inheritdoc */
    @Override
    public TableAmount readEntity(Cursor cursor, int offset) {
        TableAmount entity = new TableAmount( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.getDouble(offset + 1), // amount
                cursor.getDouble(offset + 2), // amountMtd
                cursor.getDouble(offset + 3), // amountYtd
                cursor.getString(offset + 4)// name
        );
        return entity;
    }

    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TableAmount entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setAmount(cursor.getInt(offset + 1));
        entity.setAmountMtd(cursor.getDouble(offset + 2));
        entity.setAmountYtd(cursor.getDouble(offset + 3));
        entity.setName(cursor.getString(offset + 4));

    }

    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(TableAmount entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /** @inheritdoc */
    @Override
    public Long getKey(TableAmount entity) {
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
