package com.mbopartners.mbomobile.data.db.generated.dao.payroll;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TablePersonDeposits;
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
 * Created by MboAdil on 26/7/16.
 */
public class TablePersonDepositsDao extends AbstractDao<TablePersonDeposits, Long> {

    public static final String TABLENAME = "TABLE_DEPOSITS";
    private Query<TablePersonDeposits> tablePersonDeposits_FieldsQuery;
    private DaoSession daoSession;

    /**
     * Properties of entity TableAmount.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property amount = new Property(1, Double.class, "amount", false, "amount");
        public final static Property name = new Property(2, String.class, "name", false, "name");
        public final static Property depositsRowId = new Property(3, long.class, "depositsRowId", false, "depositsRowId");
    };

    public TablePersonDepositsDao(DaoConfig config) {
        super(config);
    }

    public TablePersonDepositsDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession=daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TABLE_DEPOSITS\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"amount\" REAL NOT NULL," +// 1: amount
                "\"name\" TEXT ," +
                "\"depositsRowId\" INTEGER NOT NULL);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TABLE_DEPOSITS\"";
        db.execSQL(sql);
    }
    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TablePersonDeposits entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindDouble(2, entity.getAmount());
        stmt.bindString(3, entity.getName());
        stmt.bindLong(4, entity.getPaymentAmountRowId());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /** @inheritdoc */
    @Override
    public TablePersonDeposits readEntity(Cursor cursor, int offset) {
        TablePersonDeposits entity = new TablePersonDeposits( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.getDouble(offset + 1), // amount
                cursor.getString(offset + 2),// name
                cursor.getLong(offset + 3)

        );
        return entity;
    }

    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TablePersonDeposits entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setAmount(cursor.getDouble(offset + 1));
        entity.setName(cursor.getString(offset + 2));
        entity.setPaymentAmountRowId(cursor.getLong(offset + 3));

    }

    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(TablePersonDeposits entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /** @inheritdoc */
    @Override
    public Long getKey(TablePersonDeposits entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    protected void attachEntity(TablePersonDeposits entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }


    /** Internal query to resolve the "Fields" to-many relationship of TableDashboard. */
    public List<TablePersonDeposits> _queryTableDashboard_Fields(long depositsRowId) {
        synchronized (this) {
            if (tablePersonDeposits_FieldsQuery == null) {
                QueryBuilder<TablePersonDeposits> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.depositsRowId.eq(null));
                tablePersonDeposits_FieldsQuery = queryBuilder.build();
            }
        }
        Query<TablePersonDeposits> query = tablePersonDeposits_FieldsQuery.forCurrentThread();
        query.setParameter(0, depositsRowId);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getTablePersonWithHoldingDao().getAllColumns());
            builder.append(" FROM TABLE_DEPOSITS T");
            builder.append(" LEFT JOIN TABLE_DEPOSITS T0 ON T.\"depositsRowId\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }

    protected TablePersonDeposits loadCurrentDeep(Cursor cursor, boolean lock) {
        TablePersonDeposits entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        TablePersonalWithHolding tablePersonalWithHolding = loadCurrentOther(daoSession.getTablePersonWithHoldingDao(), cursor, offset);
        if(tablePersonalWithHolding != null) {
            entity.setTablePersonalWithHolding(tablePersonalWithHolding);
        }

        return entity;
    }

    public TablePersonDeposits loadDeep(Long key) {
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
    public List<TablePersonDeposits> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<TablePersonDeposits> list = new ArrayList<TablePersonDeposits>(count);

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

    protected List<TablePersonDeposits> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }


    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<TablePersonDeposits> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
}
