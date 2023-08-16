package com.example.psq.greendao.operation;

import com.example.psq.greendao.api.Operation;

import org.greenrobot.greendao.AbstractDao;

import java.util.List;

public abstract class BaseOperation<T, K, R extends AbstractDao<T, K>> implements Operation<T, K> {

    public abstract R getDao();

    @Override
    public void insert(T t) {
        if (t != null) {
            getDao().insert(t);
        }
    }

    @Override
    public void insertOrReplace(T t) {
        if (t != null) {
            getDao().insertOrReplace(t);
        }
    }

    @Override
    public void insertOrReplaceInTx(Iterable<T> entities) {
        if (entities != null) {
            getDao().insertOrReplaceInTx(entities);
        }
    }

    @Override
    public void deleteByKey(K k) {
        if (k != null) {
            getDao().deleteByKey(k);
        }
    }

    @Override
    public void delete(T t) {
        if (t != null) {
            getDao().delete(t);
        }
    }

    @Override
    public void deleteInTx(Iterable<T> entities) {
        if (entities != null) {
            getDao().deleteInTx(entities);
        }
    }

    @Override
    public void deleteAll() {
        getDao().deleteAll();
    }

    @Override
    public void update(T t) {
        if (t != null) {
            getDao().update(t);
        }
    }

    @Override
    public T load(K key) {
        if (key != null) {
            return getDao().load(key);
        }
        return null;
    }

    @Override
    public List<T> loadAll() {
        return getDao().loadAll();
    }
}
