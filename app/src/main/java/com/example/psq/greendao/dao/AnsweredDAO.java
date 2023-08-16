package com.example.psq.greendao.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

import com.example.psq.greendao.database.DaoSession;
import com.example.psq.greendao.database.AnswerDAODao;
import com.example.psq.greendao.database.AnsweredDAODao;

@Entity
public class AnsweredDAO {
    @Id(autoincrement = true)
    Long id;

    private String bankId;
    private String title;
    private String name;
    private String answerTime;
    private boolean upload;
    @ToMany(referencedJoinProperty = "answeredId")
    private List<AnswerDAO> answerDAOS;

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /**
     * Used for active entity operations.
     */
    @Generated(hash = 1229908787)
    private transient AnsweredDAODao myDao;

    @Generated(hash = 1038194985)
    public AnsweredDAO(Long id, String bankId, String title, String name,
                       String answerTime, boolean upload) {
        this.id = id;
        this.bankId = bankId;
        this.title = title;
        this.name = name;
        this.answerTime = answerTime;
        this.upload = upload;
    }

    @Generated(hash = 598349396)
    public AnsweredDAO() {
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnswerTime() {
        return this.answerTime;
    }

    public void setAnswerTime(String answerTime) {
        this.answerTime = answerTime;
    }

    public boolean getUpload() {
        return this.upload;
    }

    public void setUpload(boolean upload) {
        this.upload = upload;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1779575378)
    public List<AnswerDAO> getAnswerDAOS() {
        if (answerDAOS == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            AnswerDAODao targetDao = daoSession.getAnswerDAODao();
            List<AnswerDAO> answerDAOSNew = targetDao
                    ._queryAnsweredDAO_AnswerDAOS(id);
            synchronized (this) {
                if (answerDAOS == null) {
                    answerDAOS = answerDAOSNew;
                }
            }
        }
        return answerDAOS;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 1580824303)
    public synchronized void resetAnswerDAOS() {
        answerDAOS = null;
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
    @Generated(hash = 239655174)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getAnsweredDAODao() : null;
    }
}
