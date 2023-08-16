package com.example.psq.greendao.dao;

import com.alibaba.fastjson.annotation.JSONField;
import com.example.psq.greendao.database.DaoSession;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

import com.example.psq.greendao.database.TopicDAODao;
import com.example.psq.greendao.database.QBankDAODao;

/**
 * 题库
 */
@Entity
public class QBankDAO {
    @Id(autoincrement = true)
    Long id;

    private String bankId;
    private String title;
    @JSONField(name = "abstract")
    private String abstractX;
    private String sort;
    public String created_at;
    public Integer topicCount;
    @ToMany(referencedJoinProperty = "qBankId")
    private List<TopicDAO> topic;

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /**
     * Used for active entity operations.
     */
    @Generated(hash = 439091597)
    private transient QBankDAODao myDao;

    @Generated(hash = 1042318617)
    public QBankDAO(Long id, String bankId, String title, String abstractX,
                    String sort, String created_at, Integer topicCount) {
        this.id = id;
        this.bankId = bankId;
        this.title = title;
        this.abstractX = abstractX;
        this.sort = sort;
        this.created_at = created_at;
        this.topicCount = topicCount;
    }

    @Generated(hash = 1523253104)
    public QBankDAO() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankId() {
        return this.bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstractX() {
        return this.abstractX;
    }

    public void setAbstractX(String abstractX) {
        this.abstractX = abstractX;
    }

    public String getSort() {
        return this.sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Integer getTopicCount() {
        return this.topicCount;
    }

    public void setTopicCount(Integer topicCount) {
        this.topicCount = topicCount;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 712887820)
    public List<TopicDAO> getTopic() {
        if (topic == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TopicDAODao targetDao = daoSession.getTopicDAODao();
            List<TopicDAO> topicNew = targetDao._queryQBankDAO_Topic(id);
            synchronized (this) {
                if (topic == null) {
                    topic = topicNew;
                }
            }
        }
        return topic;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 259077635)
    public synchronized void resetTopic() {
        topic = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 1372849381)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getQBankDAODao() : null;
    }
}
