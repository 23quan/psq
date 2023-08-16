package com.example.psq.greendao.operation;

import com.example.psq.bean.QBankBean;
import com.example.psq.greendao.DaoManager;
import com.example.psq.greendao.dao.QBankDAO;
import com.example.psq.greendao.dao.TopicDAO;
import com.example.psq.greendao.database.QBankDAODao;
import com.example.psq.greendao.database.TopicDAODao;

import java.util.List;

public class QBankOperation extends BaseOperation<QBankDAO, Long, QBankDAODao> {
    public TopicDAODao topicDao;

    public QBankOperation() {
        topicDao = DaoManager.getInstance().getDaoSession().getTopicDAODao();
    }

    @Override
    public QBankDAODao getDao() {
        return DaoManager.getInstance().getDaoSession().getQBankDAODao();
    }

    public void insertQBank(QBankBean qBankBean) {
        QBankDAO qBankDAO = new QBankDAO();
        qBankDAO.setBankId(qBankBean.id);
        qBankDAO.setTitle(qBankBean.title);
        qBankDAO.setAbstractX(qBankBean.abstractX);
        qBankDAO.setSort(qBankBean.sort);
        qBankDAO.setCreated_at(qBankBean.created_at);
        qBankDAO.setTopicCount(qBankBean.topicCount);

        Long id = getDao().insert(qBankDAO);
        if (qBankBean.topic == null || qBankBean.topic.size() == 0) return;
        for (QBankBean.TopicDTO topicDAO : qBankBean.topic) {
            TopicDAO dao = new TopicDAO();
            dao.setQBankId(id);
            dao.setTopicId(topicDAO.id);
            dao.setBankId(topicDAO.question_bank_id);
            dao.setType(topicDAO.type);
            dao.setTitle(topicDAO.title);
            dao.setContent(topicDAO.content);
            dao.setSort(topicDAO.sort);
            topicDao.insert(dao);
        }
    }

    public void deleteAllQBank() {
        topicDao.deleteAll();
        getDao().deleteAll();
    }

    public List<QBankDAO> getAllQBank() {
        return getDao().loadAll();
    }
}
