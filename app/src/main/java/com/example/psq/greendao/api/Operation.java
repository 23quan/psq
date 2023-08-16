package com.example.psq.greendao.api;

import java.util.List;

public interface Operation<T, K> {

    void insert(T t);

    void insertOrReplace(T t);

    void insertOrReplaceInTx(Iterable<T> entities);

    void deleteByKey(K k);

    void delete(T t);

    void deleteInTx(Iterable<T> entities);

    void deleteAll();

    void update(T t);

    T load(K key);

    List<T> loadAll();
}
