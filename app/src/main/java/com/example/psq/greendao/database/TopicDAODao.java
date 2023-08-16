package com.example.psq.greendao.database;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import com.example.psq.greendao.dao.TopicDAO;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TOPIC_DAO".
*/
public class TopicDAODao extends AbstractDao<TopicDAO, Long> {

    public static final String TABLENAME = "TOPIC_DAO";

    /**
     * Properties of entity TopicDAO.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property QBankId = new Property(1, Long.class, "qBankId", false, "Q_BANK_ID");
        public final static Property TopicId = new Property(2, String.class, "topicId", false, "TOPIC_ID");
        public final static Property BankId = new Property(3, String.class, "bankId", false, "BANK_ID");
        public final static Property Type = new Property(4, String.class, "type", false, "TYPE");
        public final static Property Title = new Property(5, String.class, "title", false, "TITLE");
        public final static Property Content = new Property(6, String.class, "content", false, "CONTENT");
        public final static Property Sort = new Property(7, String.class, "sort", false, "SORT");
    }

    private Query<TopicDAO> qBankDAO_TopicQuery;

    public TopicDAODao(DaoConfig config) {
        super(config);
    }
    
    public TopicDAODao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TOPIC_DAO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"Q_BANK_ID\" INTEGER," + // 1: qBankId
                "\"TOPIC_ID\" TEXT," + // 2: topicId
                "\"BANK_ID\" TEXT," + // 3: bankId
                "\"TYPE\" TEXT," + // 4: type
                "\"TITLE\" TEXT," + // 5: title
                "\"CONTENT\" TEXT," + // 6: content
                "\"SORT\" TEXT);"); // 7: sort
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TOPIC_DAO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, TopicDAO entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long qBankId = entity.getQBankId();
        if (qBankId != null) {
            stmt.bindLong(2, qBankId);
        }
 
        String topicId = entity.getTopicId();
        if (topicId != null) {
            stmt.bindString(3, topicId);
        }
 
        String bankId = entity.getBankId();
        if (bankId != null) {
            stmt.bindString(4, bankId);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(5, type);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(6, title);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(7, content);
        }
 
        String sort = entity.getSort();
        if (sort != null) {
            stmt.bindString(8, sort);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, TopicDAO entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long qBankId = entity.getQBankId();
        if (qBankId != null) {
            stmt.bindLong(2, qBankId);
        }
 
        String topicId = entity.getTopicId();
        if (topicId != null) {
            stmt.bindString(3, topicId);
        }
 
        String bankId = entity.getBankId();
        if (bankId != null) {
            stmt.bindString(4, bankId);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(5, type);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(6, title);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(7, content);
        }
 
        String sort = entity.getSort();
        if (sort != null) {
            stmt.bindString(8, sort);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public TopicDAO readEntity(Cursor cursor, int offset) {
        TopicDAO entity = new TopicDAO( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // qBankId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // topicId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // bankId
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // type
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // title
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // content
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7) // sort
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, TopicDAO entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setQBankId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setTopicId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setBankId(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setType(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setTitle(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setContent(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setSort(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(TopicDAO entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(TopicDAO entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(TopicDAO entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "topic" to-many relationship of QBankDAO. */
    public List<TopicDAO> _queryQBankDAO_Topic(Long qBankId) {
        synchronized (this) {
            if (qBankDAO_TopicQuery == null) {
                QueryBuilder<TopicDAO> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.QBankId.eq(null));
                qBankDAO_TopicQuery = queryBuilder.build();
            }
        }
        Query<TopicDAO> query = qBankDAO_TopicQuery.forCurrentThread();
        query.setParameter(0, qBankId);
        return query.list();
    }

}
