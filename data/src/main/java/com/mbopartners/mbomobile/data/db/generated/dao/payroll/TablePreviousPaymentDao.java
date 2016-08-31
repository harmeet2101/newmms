package com.mbopartners.mbomobile.data.db.generated.dao.payroll;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TablePayrollSummary;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TablePreviousPayment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by MboAdil on 5/7/16.
 */
public class TablePreviousPaymentDao extends AbstractDao<TablePreviousPayment, Long> {

    public static final String TABLENAME = "TABLE_PREVIOUS_PAYMENT";
    private Query<TablePreviousPayment> tablePrevioustPayment_FieldsQuery;
    private DaoSession daoSession;
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
        public final static Property previousPaymentRowId = new Property(5, long.class, "previousPaymentRowId", false, "previousPaymentRowId");
    };

    public TablePreviousPaymentDao(DaoConfig config) {
        super(config);
    }

    public TablePreviousPaymentDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession=daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TABLE_PREVIOUS_PAYMENT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"businessCenterId\" TEXT,"+// 1: businessCenterId
                "\"date\" INTEGER," + // 2: date
                "\"previousPaymentId\" TEXT NOT NULL," + // 3: previousPaymentId
                "\"mboId\" TEXT," + // 4: mboId
                "\"previousPaymentRowId\" INTEGER TEXT NOT NULL);");
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
        stmt.bindLong(6, entity.getPreviousPaymentRowId());
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
                cursor.getString(offset + 4),// mboId
                cursor.getLong(offset + 5)
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
        entity.setPreviousPaymentRowId(cursor.getLong(offset + 5));

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

    /** Internal query to resolve the "Fields" to-many relationship of TableDashboard. */
    public List<TablePreviousPayment> _queryTableDashboard_Fields(long previousPaymentRowId) {
        synchronized (this) {
            if (tablePrevioustPayment_FieldsQuery == null) {
                QueryBuilder<TablePreviousPayment> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.previousPaymentRowId.eq(null));
                tablePrevioustPayment_FieldsQuery = queryBuilder.build();
            }
        }
        Query<TablePreviousPayment> query = tablePrevioustPayment_FieldsQuery.forCurrentThread();
        query.setParameter(0, previousPaymentRowId);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getTablePayrollSummaryDao().getAllColumns());
            builder.append(" FROM TABLE_NEXT_PAYMENT T");
            builder.append(" LEFT JOIN TABLE_NEXT_PAYMENT T0 ON T.\"previousPaymentRowId\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }

    protected TablePreviousPayment loadCurrentDeep(Cursor cursor, boolean lock) {
        TablePreviousPayment entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        TablePayrollSummary tablePayrollSummary = loadCurrentOther(daoSession.getTablePayrollSummaryDao(), cursor, offset);
        if(tablePayrollSummary != null) {
            entity.setTablePayrollSummary(tablePayrollSummary);
        }

        return entity;
    }

    @Override
    protected void attachEntity(TablePreviousPayment entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    public TablePreviousPayment loadDeep(Long key) {
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
    public List<TablePreviousPayment> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<TablePreviousPayment> list = new ArrayList<TablePreviousPayment>(count);

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

    protected List<TablePreviousPayment> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }


    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<TablePreviousPayment> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
}
