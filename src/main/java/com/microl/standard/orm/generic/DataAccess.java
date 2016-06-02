package com.microl.standard.orm.generic;

import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessResourceFailureException;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by vietlk on 01/06/2016.
 */

public interface DataAccess<T extends RootEntity> {
    T get(long id);
    T load(long id);
    List<T> loadAll();
    void addAll(Collection<T> entityList);
    List<T> getAll(Collection<Long> idList);
    Long save(T entity);
    String saveString(T entity);
    List<Long> saveAll(Collection<T> entityList);
    void saveOrUpdate(T entity);
    void saveOrUpdateAll(Collection<T> entityList);
    void update(T entity);
    T merge(T entity);
    void evict(T entity);
    void evict(Object entity);
    void remove(long id);
    void remove(T entity);
    void removeAll() throws HibernateException;
    void removeAll(Collection<T> entityList) throws HibernateException;
    RootEntity get(String entityName, long id);
    Long save(String entityName, RootEntity model);
    List loadAll(String entityName) throws HibernateException, ClassNotFoundException;
    <S> List<S> loadAll(Class<S> entityName) throws HibernateException, ClassNotFoundException;

    void remove(String entityName, RootEntity model);
    long getTotalRecordCount(String propertyName)
                throws DataAccessResourceFailureException, HibernateException,
                        IllegalStateException, ClassNotFoundException;
    <S> S merge(String entityName, RootEntity model);
    void clearAllCaches();
}
