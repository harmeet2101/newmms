package com.mbopartners.mbomobile.data.db.generated.dao.payroll;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TableBusinessWithHolding;
import com.mbopartners.mbomobile.data.db.generated.model.payroll.TablePersonalWithHolding;
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
 * Created by MboAdil on 14/7/16.
 */
public class TablePersonWithHoldingDao extends AbstractDao<TablePersonalWithHolding, Long> {

    public static final String TABLENAME = "TABLE_PERSON_HOLDING";
    private Query<TablePersonalWithHolding> tablePersonWithHolding_FieldsQuery;
    private DaoSession daoSession;

    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property personWithHoldingRowId = new Property(1, long.class, "personWithHoldingRowId", false, "personWithHoldingRowId");
    };

    public TablePersonWithHoldingDao(DaoConfig config) {
        super(config);
    }

    public TablePersonWithHoldingDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession=daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TABLE_PERSON_HOLDING\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"personWithHoldingRowId\" INTEGER NOT NULL );");
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TABLE_PERSON_HOLDING\"";
        db.execSQL(sql);
    }
    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TablePersonalWithHolding entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getPersonWithHoldingRowId());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /** @inheritdoc */
    @Override
    public TablePersonalWithHolding readEntity(Cursor cursor, int offset) {
        TablePersonalWithHolding entity = new TablePersonalWithHolding( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.getLong(offset + 1)
        );
        return entity;
    }

    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TablePersonalWithHolding entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPersonWithHoldingRowId(cursor.getLong(offset + 1));

    }

    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(TablePersonalWithHolding entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /** @inheritdoc */
    @Override
    public Long getKey(TablePersonalWithHolding entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }
    @Override
    protected void attachEntity(TablePersonalWithHolding entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }

    /** Internal query to resolve the "Fields" to-many relationship of TableDashboard. */
    public List<TablePersonalWithHolding> _queryTableDashboard_Fields(long personWithHoldingRowId) {
        synchronized (this) {
            if (tablePersonWithHolding_FieldsQuery == null) {
                QueryBuilder<TablePersonalWithHolding> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.personWithHoldingRowId.eq(null));
                tablePersonWithHolding_FieldsQuery = queryBuilder.build();
            }
        }
        Query<TablePersonalWithHolding> query = tablePersonWithHolding_FieldsQuery.forCurrentThread();
        query.setParameter(0, personWithHoldingRowId);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getTablePreviousPaymentDao().getAllColumns());
            builder.append(" FROM TABLE_PERSON_HOLDING T");
            builder.append(" LEFT JOIN TABLE_PERSON_HOLDING T0 ON T.\"personWithHoldingRowId\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }

    protected TablePersonalWithHolding loadCurrentDeep(Cursor cursor, boolean lock) {
        TablePersonalWithHolding entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        TablePreviousPayment tablePreviousPayment = loadCurrentOther(daoSession.getTablePreviousPaymentDao(), cursor, offset);
        if(tablePreviousPayment != null) {
            entity.setTablePreviousPayment(tablePreviousPayment);
        }

        return entity;
    }

    public TablePersonalWithHolding loadDeep(Long key) {
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
    public List<TablePersonalWithHolding> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<TablePersonalWithHolding> list = new ArrayList<TablePersonalWithHolding>(count);

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

    protected List<TablePersonalWithHolding> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }


    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<TablePersonalWithHolding> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
}
