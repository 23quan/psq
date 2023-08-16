package com.example.psq.greendao.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.psq.greendao.dao.AnsweredDAO;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ANSWERED_DAO".
*/
public class AnsweredDAODao extends AbstractDao<AnsweredDAO, Long> {

    public static final String TABLENAME = "ANSWERED_DAO";

    /**
     * Properties of entity AnsweredDAO.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property BankId = new Property(1, String.class, "bankId", false, "BANK_ID");
        public final static Property Title = new Property(2, String.class, "title", false, "TITLE");
        public final static Property Name = new Property(3, String.class, "name", false, "NAME");
        public final static Property AnswerTime = new Property(4, String.class, "answerTime", false, "ANSWER_TIME");
        public final static Property Upload = new Property(5, boolean.class, "upload", false, "UPLOAD");
    }

    private DaoSession daoSession;


    public AnsweredDAODao(DaoConfig config) {
        super(config);
    }
    
    public AnsweredDAODao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ANSWERED_DAO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"BANK_ID\" TEXT," + // 1: bankId
                "\"TITLE\" TEXT," + // 2: title
                "\"NAME\" TEXT," + // 3: name
                "\"ANSWER_TIME\" TEXT," + // 4: answerTime
                "\"UPLOAD\" INTEGER NOT NULL );"); // 5: upload
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ANSWERED_DAO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, AnsweredDAO entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String bankId = entity.getBankId();
        if (bankId != null) {
            stmt.bindString(2, bankId);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(3, title);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
 
        String answerTime = entity.getAnswerTime();
        if (answerTime != null) {
            stmt.bindString(5, answerTime);
        }
        stmt.bindLong(6, entity.getUpload() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, AnsweredDAO entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String bankId = entity.getBankId();
        if (bankId != null) {
            stmt.bindString(2, bankId);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(3, title);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
 
        String answerTime = entity.getAnswerTime();
        if (answerTime != null) {
            stmt.bindString(5, answerTime);
        }
        stmt.bindLong(6, entity.getUpload() ? 1L: 0L);
    }

    @Override
    protected final void attachEntity(AnsweredDAO entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public AnsweredDAO readEntity(Cursor cursor, int offset) {
        AnsweredDAO entity = new AnsweredDAO( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // bankId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // title
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // name
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // answerTime
            cursor.getShort(offset + 5) != 0 // upload
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, AnsweredDAO entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setBankId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTitle(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setAnswerTime(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setUpload(cursor.getShort(offset + 5) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(AnsweredDAO entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(AnsweredDAO entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(AnsweredDAO entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}