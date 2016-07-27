package com.mbopartners.mbomobile.data.db.generated.dao.payroll;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TableBusinessWithHolding;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TablePreviousPayment;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TableSummaryBusinessWithHolding;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by MboAdil on 27/7/16.
 */
public class TableSummaryBusinessWithHoldingDao extends AbstractDao<TableSummaryBusinessWithHolding, Long> {

    public static final String TABLENAME = "TABLE_SUMMARY_BUSINESS_HOLDING";
    private Query<TableSummaryBusinessWithHolding> tableBusinessWithHolding_FieldsQuery;
    private DaoSession daoSession;

    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property businessWithHoldingRowId = new Property(1, long.class, "businessWithHoldingRowId", false, "businessWithHoldingRowId");
    }

    public TableSummaryBusinessWithHoldingDao(DaoConfig config) {
        super(config);
    }

    public TableSummaryBusinessWithHoldingDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession=daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TABLE_SUMMARY_BUSINESS_HOLDING\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"businessWithHoldingRowId\" INTEGER NOT NULL );");
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TABLE_SUMMARY_BUSINESS_HOLDING\"";
        db.execSQL(sql);
    }
    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TableSummaryBusinessWithHolding entity) {
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
    public TableSummaryBusinessWithHolding readEntity(Cursor cursor, int offset) {
        TableSummaryBusinessWithHolding entity = new TableSummaryBusinessWithHolding( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.getLong(offset + 1)
        );
        return entity;
    }

    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TableSummaryBusinessWithHolding entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setBusinessWithHoldingRowId(cursor.getLong(offset + 1));

    }

    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(TableSummaryBusinessWithHolding entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /** @inheritdoc */
    @Override
    public Long getKey(TableSummaryBusinessWithHolding entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }
    @Override
    protected void attachEntity(TableSummaryBusinessWithHolding entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }

    /** Internal query to resolve the "Fields" to-many relationship of TableDashboard. */
    public List<TableSummaryBusinessWithHolding> _queryTableDashboard_Fields(long businessWithHoldingRowId) {
        synchronized (this) {
            if (tableBusinessWithHolding_FieldsQuery == null) {
                QueryBuilder<TableSummaryBusinessWithHolding> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.businessWithHoldingRowId.eq(null));
                tableBusinessWithHolding_FieldsQuery = queryBuilder.build();
            }
        }
        Query<TableSummaryBusinessWithHolding> query = tableBusinessWithHolding_FieldsQuery.forCurrentThread();
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
            builder.append(" FROM TABLE_SUMMARY_BUSINESS_HOLDING T");
            builder.append(" LEFT JOIN TABLE_SUMMARY_BUSINESS_HOLDING T0 ON T.\"businessWithHoldingRowId\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }

    protected TableSummaryBusinessWithHolding loadCurrentDeep(Cursor cursor, boolean lock) {
        TableSummaryBusinessWithHolding entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        TablePreviousPayment tablePreviousPayment = loadCurrentOther(daoSession.getTablePreviousPaymentDao(), cursor, offset);
        if(tablePreviousPayment != null) {
            entity.setTablePreviousPayment(tablePreviousPayment);
        }

        return entity;
    }

    public TableSummaryBusinessWithHolding loadDeep(Long key) {
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
    public List<TableSummaryBusinessWithHolding> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<TableSummaryBusinessWithHolding> list = new ArrayList<TableSummaryBusinessWithHolding>(count);

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

    protected List<TableSummaryBusinessWithHolding> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }


    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<TableSummaryBusinessWithHolding> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
}

