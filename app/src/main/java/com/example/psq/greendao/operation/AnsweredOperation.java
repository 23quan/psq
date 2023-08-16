package com.example.psq.greendao.operation;

import com.example.psq.greendao.DaoManager;
import com.example.psq.greendao.dao.AnswerDAO;
import com.example.psq.greendao.dao.AnsweredDAO;
import com.example.psq.greendao.database.AnswerDAODao;
import com.example.psq.greendao.database.AnsweredDAODao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class AnsweredOperation extends BaseOperation<AnsweredDAO, Long, AnsweredDAODao> {
    private AnswerDAODao answerDAODao;

    public AnsweredOperation() {
        answerDAODao = DaoManager.getInstance().getDaoSession().getAnswerDAODao();
    }

    @Override
    public AnsweredDAODao getDao() {
        return DaoManager.getInstance().getDaoSession().getAnsweredDAODao();
    }

    public void insert(AnsweredDAO answeredDAO, List<AnswerDAO> answerDAOS) {
        Long id = getDao().insert(answeredDAO);
        if (answerDAOS == null || answerDAOS.size() == 0) return;
        for (AnswerDAO answerDAO : answerDAOS) {
            answerDAO.setAnsweredId(id);
            answerDAODao.insert(answerDAO);
        }
    }

    public void insertAnswerList(List<AnswerDAO> answerDAOS) {
        for (AnswerDAO answerDAO : answerDAOS) {
            answerDAODao.insert(answerDAO);
        }
    }

    public void update(List<AnswerDAO> answerDAOs) {
        for (AnswerDAO answerDAO : answerDAOs) {
            answerDAODao.update(answerDAO);
        }
    }

    public List<AnsweredDAO> load(String name, int offset) {
        QueryBuilder<AnsweredDAO> daoList = getDao()
                .queryBuilder()
                .where(AnsweredDAODao.Properties.Name.eq(name))
                .offset(offset)//表示查询的起始位置
                .limit(10);//表示总共获取的对象数量
        return daoList.list();
    }

    public List<AnsweredDAO> loadPage(int offset) {
        QueryBuilder<AnsweredDAO> daoList = getDao()
                .queryBuilder()
                .offset(offset * 10)//表示查询的起始位置
                .limit(10);//表示总共获取的对象数量
        return daoList.list();
    }

    @Override
    public List<AnsweredDAO> loadAll() {
        return getDao().loadAll();
    }

    @Override
    public void delete(AnsweredDAO answeredDAO) {
        super.delete(answeredDAO);
        for (AnswerDAO answerDAO : answeredDAO.getAnswerDAOS()) {
            answerDAODao.delete(answerDAO);
        }
    }

    @Override
    public void deleteAll() {
        getDao().deleteAll();
        answerDAODao.deleteAll();
    }
}
