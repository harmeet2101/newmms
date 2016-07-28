package com.mbopartners.mbomobile.data.db.generated.dao.payroll;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TablePersonAfterDeductions;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TablePersonalWithHolding;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by MboAdil on 28/7/16.
 */
public class TablePersonAfterDeductionsDao extends AbstractDao<TablePersonAfterDeductions, Long> {

    public static final String TABLENAME = "TABLE_AFTER_DEDUCTIONS";
    private Query<TablePersonAfterDeductions> tablePersonDeposits_FieldsQuery;
    private DaoSession daoSession;

    /**
     * Properties of entity TableAmount.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property amount = new Property(1, Double.class, "amount", false, "amount");
        public final static Property amountMtd = new Property(2, Double.class, "amountMtd", false, "amountMtd");
        public final static Property amountYtd = new Property(3, Double.class, "amountYtd", false, "amountYtd");
        public final static Property name = new Property(4, String.class, "name", false, "name");
        public final static Property afterDeductionsRowId = new Property(5, long.class, "afterDeductionsRowId", false, "afterDeductionsRowId");
    };

    public TablePersonAfterDeductionsDao(DaoConfig config) {
        super(config);
    }

    public TablePersonAfterDeductionsDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession=daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TABLE_AFTER_DEDUCTIONS\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"amount\" REAL NOT NULL," +// 1: amount
                "\"amountMtd\" REAL NOT NULL ," + // 2: amountMtd
                "\"amountYtd\" REAL NOT NULL ," + // 3: amountYtd
                "\"name\" TEXT ," +
                "\"afterDeductionsRowId\" INTEGER NOT NULL);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TABLE_AFTER_DEDUCTIONS\"";
        db.execSQL(sql);
    }
    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TablePersonAfterDeductions entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindDouble(2, entity.getAmount());
        stmt.bindDouble(3, entity.getAmountMtd());
        stmt.bindDouble(4, entity.getAmountYtd());
        stmt.bindString(5, entity.getName());
        stmt.bindLong(6, entity.getPaymentAmountRowId());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /** @inheritdoc */
    @Override
    public TablePersonAfterDeductions readEntity(Cursor cursor, int offset) {
        TablePersonAfterDeductions entity = new TablePersonAfterDeductions( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.getDouble(offset + 1), // amount
                cursor.getDouble(offset + 2), // amountMtd
                cursor.getDouble(offset + 3), // amountYtd
                cursor.getString(offset + 4),// name
                cursor.getLong(offset + 5)
        );
        return entity;
    }

    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TablePersonAfterDeductions entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setAmount(cursor.getDouble(offset + 1));
        entity.setName(cursor.getString(offset + 2));
        entity.setPaymentAmountRowId(cursor.getLong(offset + 3));

    }

    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(TablePersonAfterDeductions entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /** @inheritdoc */
    @Override
    public Long getKey(TablePersonAfterDeductions entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    protected void attachEntity(TablePersonAfterDeductions entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }


    /** Internal query to resolve the "Fields" to-many relationship of TableDashboard. */
    public List<TablePersonAfterDeductions> _queryTableDashboard_Fields(long afterDeductionsRowId) {
        synchronized (this) {
            if (tablePersonDeposits_FieldsQuery == null) {
                QueryBuilder<TablePersonAfterDeductions> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.afterDeductionsRowId.eq(null));
                tablePersonDeposits_FieldsQuery = queryBuilder.build();
            }
        }
        Query<TablePersonAfterDeductions> query = tablePersonDeposits_FieldsQuery.forCurrentThread();
        query.setParameter(0, afterDeductionsRowId);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getTablePersonWithHoldingDao().getAllColumns());
            builder.append(" FROM TABLE_AFTER_DEDUCTIONS T");
            builder.append(" LEFT JOIN TABLE_AFTER_DEDUCTIONS T0 ON T.\"afterDeductionsRowId\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }

    protected TablePersonAfterDeductions loadCurrentDeep(Cursor cursor, boolean lock) {
        TablePersonAfterDeductions entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        TablePersonalWithHolding tablePersonalWithHolding = loadCurrentOther(daoSession.getTablePersonWithHoldingDao(), cursor, offset);
        if(tablePersonalWithHolding != null) {
            entity.setTablePersonalWithHolding(tablePersonalWithHolding);
        }

        return entity;
    }

    public TablePersonAfterDeductions loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();

        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);

        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }

    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<TablePersonAfterDeductions> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<TablePersonAfterDeductions> list = new ArrayList<TablePersonAfterDeductions>(count);

        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }

    protected List<TablePersonAfterDeductions> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }


    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<TablePersonAfterDeductions> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
}

