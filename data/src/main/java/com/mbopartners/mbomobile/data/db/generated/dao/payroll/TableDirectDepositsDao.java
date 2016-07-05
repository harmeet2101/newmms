package com.mbopartners.mbomobile.data.db.generated.dao.payroll;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TableDirectDeposits;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * Created by MboAdil on 5/7/16.
 */
public class TableDirectDepositsDao extends AbstractDao<TableDirectDeposits, Long> {


    public static final String TABLENAME = "TABLE_DIRECT_DEPOSIT";

    /**
     * Properties of entity TableDirectDeposits.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property BusinessCenterId = new Property(1, Integer.class, "amount", false, "amount");
        public final static Property Name = new Property(2, String.class, "bankAccountId", false, "bankAccountId");
        public final static Property MboId = new Property(3, Integer.class, "order", false, "order");
        public final static Property Balance = new Property(4, Boolean.class, "payrollRemainderFlag", false, "payrollRemainderFlag");
    };

    public TableDirectDepositsDao(DaoConfig config) {
        super(config);
    }

    public TableDirectDepositsDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TABLE_DIRECT_DEPOSIT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"amount\" INTEGER NOT NULL,"+// 1: amount
                "\"bankAccountId\" TEXT NOT NULL ," + // 2: bankaccountId
                "\"order\" INTEGER NOT NULL ," + // 3: order
                "\"payrollRemainderFlag\" INTEGER NOT NULL );" ); // 4: payrollRemainderflag
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TABLE_DIRECT_DEPOSIT\"";
        db.execSQL(sql);
    }
    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TableDirectDeposits entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getAmount());
        stmt.bindString(3, entity.getBankAccountId());
        stmt.bindLong(4, entity.getOrder());
        stmt.bindLong(5, entity.isPayrollRemainderFlag()?1L:0L);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /** @inheritdoc */
    @Override
    public TableDirectDeposits readEntity(Cursor cursor, int offset) {
        TableDirectDeposits entity = new TableDirectDeposits( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.getInt(offset + 1), // amount
                cursor.getString(offset + 2), // bankAccountId
                cursor.getInt(offset + 3), // order
                cursor.getLong(offset + 4)!=0// payrollRemainderflag
        );
        return entity;
    }

    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TableDirectDeposits entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setAmount(cursor.getInt(offset + 1));
        entity.setBankAccountId(cursor.getString(offset + 2));
        entity.setOrder(cursor.getInt(offset + 3));
        entity.setPayrollRemainderFlag(cursor.getLong(offset + 4)!=0);

    }

    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(TableDirectDeposits entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /** @inheritdoc */
    @Override
    public Long getKey(TableDirectDeposits entity) {
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
