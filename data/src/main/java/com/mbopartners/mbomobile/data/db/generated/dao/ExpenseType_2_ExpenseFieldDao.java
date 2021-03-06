package com.mbopartners.mbomobile.data.db.generated.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.mbopartners.mbomobile.data.db.generated.model.ExpenseType_2_ExpenseField;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "EXPENSE_TYPE_2__EXPENSE_FIELD".
*/
public class ExpenseType_2_ExpenseFieldDao extends AbstractDao<ExpenseType_2_ExpenseField, Long> {

    public static final String TABLENAME = "EXPENSE_TYPE_2__EXPENSE_FIELD";

    /**
     * Properties of entity ExpenseType_2_ExpenseField.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
    };


    public ExpenseType_2_ExpenseFieldDao(DaoConfig config) {
        super(config);
    }
    
    public ExpenseType_2_ExpenseFieldDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"EXPENSE_TYPE_2__EXPENSE_FIELD\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT );"); // 0: id
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"EXPENSE_TYPE_2__EXPENSE_FIELD\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, ExpenseType_2_ExpenseField entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public ExpenseType_2_ExpenseField readEntity(Cursor cursor, int offset) {
        ExpenseType_2_ExpenseField entity = new ExpenseType_2_ExpenseField( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0) // id
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, ExpenseType_2_ExpenseField entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(ExpenseType_2_ExpenseField entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(ExpenseType_2_ExpenseField entity) {
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
