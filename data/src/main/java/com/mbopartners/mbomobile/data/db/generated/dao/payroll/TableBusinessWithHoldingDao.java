package com.mbopartners.mbomobile.data.db.generated.dao.payroll;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TableBusinessWithHolding;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TablePreviousPayment;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by MboAdil on 13/7/16.
 */
public class TableBusinessWithHoldingDao extends AbstractDao<TableBusinessWithHolding, Long> {

    public static final String TABLENAME = "TABLE_BUSINESS_HOLDING";
    private Query<TableBusinessWithHolding> tableBusinessWithHolding_FieldsQuery;
    private DaoSession daoSession;

    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property businessWithHoldingRowId = new Property(1, long.class, "businessWithHoldingRowId", false, "businessWithHoldingRowId");
    };

    public TableBusinessWithHoldingDao(DaoConfig config) {
        super(config);
    }

    public TableBusinessWithHoldingDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TABLE_BUSINESS_HOLDING\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"businessWithHoldingRowId\" INTEGER NOT NULL );");
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TABLE_BUSINESS_HOLDING\"";
        db.execSQL(sql);
    }
    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TableBusinessWithHolding entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getBusinessWithHoldingRowId());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /** @inheritdoc */
    @Override
    public TableBusinessWithHolding readEntity(Cursor cursor, int offset) {
        TableBusinessWithHolding entity = new TableBusinessWithHolding( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.getLong(offset + 1)
        );
        return entity;
    }

    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TableBusinessWithHolding entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setBusinessWithHoldingRowId(cursor.getLong(offset + 1));

    }

    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(TableBusinessWithHolding entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /** @inheritdoc */
    @Override
    public Long getKey(TableBusinessWithHolding entity) {
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

    /** Internal query to resolve the "Fields" to-many relationship of TableDashboard. */
    public List<TableBusinessWithHolding> _queryTableDashboard_Fields(long businessWithHoldingRowId) {
        synchronized (this) {
            if (tableBusinessWithHolding_FieldsQuery == null) {
                QueryBuilder<TableBusinessWithHolding> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.businessWithHoldingRowId.eq(null));
                tableBusinessWithHolding_FieldsQuery = queryBuilder.build();
            }
        }
        Query<TableBusinessWithHolding> query = tableBusinessWithHolding_FieldsQuery.forCurrentThread();
        query.setParameter(0, businessWithHoldingRowId);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getTablePreviousPaymentDao().getAllColumns());
            builder.append(" FROM TABLE_BUSINESS_HOLDING T");
            builder.append(" LEFT JOIN TABLE_BUSINESS_HOLDING T0 ON T.\"businessWithHoldingRowId\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }

    protected TableBusinessWithHolding loadCurrentDeep(Cursor cursor, boolean lock) {
        TableBusinessWithHolding entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        TablePreviousPayment tablePreviousPayment = loadCurrentOther(daoSession.getTablePreviousPaymentDao(), cursor, offset);
        if(tablePreviousPayment != null) {
            entity.setTablePreviousPayment(tablePreviousPayment);
        }

        return entity;
    }

    public TableBusinessWithHolding loadDeep(Long key) {
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
    public List<TableBusinessWithHolding> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<TableBusinessWithHolding> list = new ArrayList<TableBusinessWithHolding>(count);

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

    protected List<TableBusinessWithHolding> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }


    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<TableBusinessWithHolding> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
}
