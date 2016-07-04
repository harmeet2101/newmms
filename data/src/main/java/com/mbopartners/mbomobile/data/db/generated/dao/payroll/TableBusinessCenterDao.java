package com.mbopartners.mbomobile.data.db.generated.dao.payroll;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.data.db.generated.model.TableBusinessManager;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TableBusinessCenter;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * Created by MboAdil on 1/7/16.
 */
public class TableBusinessCenterDao extends AbstractDao<TableBusinessCenter, Long> {


    public static final String TABLENAME = "TABLE_BUSINESS_CENTER";

    /**
     * Properties of entity TableBusinessCenter.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property BusinessCenterId = new Property(1, String.class, "businessId", false, "businessid");
        public final static Property Name = new Property(2, String.class, "Name", false, "NAME");
        public final static Property MboId = new Property(3, String.class, "MboId", false, "MBO_ID");
        public final static Property Balance = new Property(4, Double.class, "Balance", false, "BALANCE");
    };

    public TableBusinessCenterDao(DaoConfig config) {
        super(config);
    }

    public TableBusinessCenterDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TABLE_BUSINESS_CENTER\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"businessid\" TEXT NOT NULL,"+// 1: businessid
                "\"NAME\" TEXT NOT NULL ," + // 2: Name
                "\"MBO_ID\" TEXT NOT NULL ," + // 3: MBO_ID
                "\"BALANCE\" REAL NOT NULL );" ); // 4: Balance
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TABLE_BUSINESS_CENTER\"";
        db.execSQL(sql);
    }
    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TableBusinessCenter entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getBusinessId());
        stmt.bindString(3, entity.getName());
        stmt.bindString(4, entity.getMboId());
        stmt.bindDouble(5, entity.getBalance());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /** @inheritdoc */
    @Override
    public TableBusinessCenter readEntity(Cursor cursor, int offset) {
        TableBusinessCenter entity = new TableBusinessCenter( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.getString(offset + 1), // businessId
                cursor.getString(offset + 2), // Name
                cursor.getString(offset + 3), // mboId
                cursor.getDouble(offset + 4)// Balance
        );
        return entity;
    }

    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TableBusinessCenter entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setBusinessId(cursor.getString(offset + 1));
        entity.setName(cursor.getString(offset + 2));
        entity.setMboId(cursor.getString(offset + 3));
        entity.setBalance(cursor.getDouble(offset + 4));

    }

    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(TableBusinessCenter entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /** @inheritdoc */
    @Override
    public Long getKey(TableBusinessCenter entity) {
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
