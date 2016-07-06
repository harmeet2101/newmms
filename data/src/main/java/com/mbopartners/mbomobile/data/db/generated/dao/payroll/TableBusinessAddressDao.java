package com.mbopartners.mbomobile.data.db.generated.dao.payroll;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TableBusinessAddress;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * Created by MboAdil on 1/7/16.
 */
public class TableBusinessAddressDao extends AbstractDao<TableBusinessAddress, Long> {

    public static final String TABLENAME = "TABLE_BUSINESS_ADDRESS";

    /**
     * Properties of entity TableBusinessAddress.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Line1 = new Property(1, String.class, "Line1", false, "LINE1");
        public final static Property Line2 = new Property(2, String.class, "Line2", false, "LINE2");
        public final static Property City = new Property(3, String.class, "City", false, "CITY");
        public final static Property State = new Property(4, String.class, "State", false, "STATE");
        public final static Property PostalCode = new Property(5, String.class, "PostalCode", false, "POSTAL_CODE");
    };

    public TableBusinessAddressDao(DaoConfig config) {
        super(config);
    }

    public TableBusinessAddressDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TABLE_BUSINESS_ADDRESS\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"LINE1\" TEXT NOT NULL ," + // 1: Line1
                "\"LINE2\" TEXT NOT NULL ," + // 2: Line2
                "\"CITY\" TEXT NOT NULL ," + // 3: City
                "\"STATE\" TEXT NOT NULL ," + // 4: State
                "\"POSTAL_CODE\" TEXT NOT NULL );" ); // 5: PostalCode
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TABLE_BUSINESS_ADDRESS\"";
        db.execSQL(sql);
    }
    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TableBusinessAddress entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getLine1());
        stmt.bindString(3, entity.getLine2());
        stmt.bindString(4, entity.getCity());
        stmt.bindString(5, entity.getState());
        stmt.bindString(6, entity.getPostalCode());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /** @inheritdoc */
    @Override
    public TableBusinessAddress readEntity(Cursor cursor, int offset) {
        TableBusinessAddress entity = new TableBusinessAddress( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.getString(offset + 1), // Line1
                cursor.getString(offset + 2), // Line2
                cursor.getString(offset + 3),//City
                cursor.getString(offset + 4),//State
                cursor.getString(offset + 5)// Postalcode
        );
        return entity;
    }

    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TableBusinessAddress entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setLine1(cursor.getString(offset + 1));
        entity.setLine2(cursor.getString(offset + 2));
        entity.setCity(cursor.getString(offset + 3));
        entity.setState(cursor.getString(offset + 4));
        entity.setPostalCode(cursor.getString(offset + 5));

    }

    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(TableBusinessAddress entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /** @inheritdoc */
    @Override
    public Long getKey(TableBusinessAddress entity) {
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
