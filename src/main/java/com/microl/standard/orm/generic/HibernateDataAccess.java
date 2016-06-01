package com.microl.standard.orm.generic;

import org.hibernate.*;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;

import javax.persistence.Entity;
import java.util.*;

/**
 * Created by vietlk on 01/06/2016.
 */
public class HibernateDataAccess<T extends RootEntity> extends RootDataAccess<T> {

    @Autowired
    private SessionFactory sessionFactory;

    public HibernateDataAccess(String entityName, Class<T> persistentClass) {
        super(entityName, persistentClass);
    }

    public HibernateDataAccess(Class<T> persistentClass) {
        super(persistentClass);
    }

    @Override
    Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    void flushSession() {
        getSession().flush();
    }

    @Override
    void closeSession() {
        getSession().close();
    }

    public T get(long id) {
        return (T) getSession().get(super.getPersistentClass(), new Long(id));
    }

    public T load(long id) {
        return (T) getSession().load(super.getPersistentClass(), new Long(id));
    }

    public List<T> loadAll() {
        return (List<T>) getSession().createCriteria(super.getPersistentClass())
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    public void addAll(Collection<T> entityList) {
        for (Iterator<T> itr = entityList.iterator(); itr.hasNext();) {
            getSession().save(itr.next());
        }
    }

    public List<T> getAll(Collection<Long> idList) {
        return (List<T>) getSession().createCriteria(super.getPersistentClass())
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.in("id", idList)).list();
    }

    public Long save(T entity) {
        return (Long) getSession().save(entity);
    }

    public String saveString(T entity) {
        return (String) getSession().save(entity);
    }

    public List<Long> saveAll(Collection<T> entityList) {
        List<Long> longs = new ArrayList<Long>();
        for (Iterator<T> itr = entityList.iterator(); itr.hasNext();) {
            long id = (Long) getSession().save(itr.next());
            longs.add(id);
        }
        return longs;
    }

    public void saveOrUpdate(T entity) {
        getSession().saveOrUpdate(entity);
    }

    public void saveOrUpdateAll(Collection<T> entityList) {
        for (Iterator<T> itr = entityList.iterator(); itr.hasNext();) {
            getSession().saveOrUpdate(itr.next());
        }
    }

    public void update(T entity) {
        getSession().update(entity);
    }

    public T merge(T entity) {
        return (T) getSession().merge(entity);
    }

    public void evict(T entity) {
        getSession().evict(entity);
    }

    public void evict(Object entity) {
        getSession().evict(entity);
    }

    public void remove(long id) {
        getSession().delete(this.get(id));
    }

    public void remove(T entity) {
        getSession().delete(entity);
    }

    public void removeAll() throws HibernateException {
        List<T> completeList = loadAll();
        for (Iterator<T> itr = completeList.iterator(); itr.hasNext();) {
            getSession().delete(itr.next());
        }
    }

    public void removeAll(Collection<T> entityList) throws HibernateException {
        for (Iterator<T> itr = entityList.iterator(); itr.hasNext();) {
            getSession().delete(itr.next());
        }
    }

    public RootEntity get(String entityName, long id) {
        return (RootEntity) getSession().get(entityName, id);
    }

    public Long save(String entityName, RootEntity model) {
        return (Long) getSession().save(entityName, model);
    }

    public List loadAll(String entityName) throws HibernateException, ClassNotFoundException {
        return getSession().createCriteria(Class.forName(entityName))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    public <S> List<S> loadAll(Class<S> entityName) throws HibernateException, ClassNotFoundException {
        return getSession().createCriteria(entityName)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    public void remove(String entityName, RootEntity model) {
        getSession().delete(entityName, model);
    }

    public long getTotalRecordCount(String propertyName)
            throws DataAccessResourceFailureException, HibernateException,
            IllegalStateException, ClassNotFoundException {

        Long count = (Long) getSession().createCriteria(super.getPersistentClass())
                                        .setProjection(Projections.max(propertyName))
                                        .uniqueResult();
        return count.longValue();
    }

    public <S> S merge(String entityName, RootEntity model) {
        return (S) getSession().merge(entityName, model);
    }

    public void clearAllCaches() {
        Session session = getSession();
        if (session != null) {
            session.clear(); // internal cache clear
        }

        Cache cache = sessionFactory.getCache();
        if (cache != null) {
            cache.evictCollectionRegions();
            cache.evictDefaultQueryRegion();
            cache.evictEntityRegions();
            cache.evictQueryRegions();
            cache.evictNaturalIdRegions();
        }
    }

    public List<Object> hqlSelectField(String queryString, String... parameterValues) {
        Query query = getSession().createQuery(queryString);
        for (int i=0; i<parameterValues.length; i++) {
            query.setString("parameter"+i, parameterValues[i]);
        }
        List list = query.list();
        return list;
    }

    public List<Object> hqlSelectFieldLimit(String queryString, Integer limit, String... parameterValues) {
        Query query = getSession().createQuery(queryString);
        for (int i=0; i<parameterValues.length; i++) {
            query.setString("parameter"+i, parameterValues[i]);
        }
        query.setMaxResults(limit);
        List list = query.list();
        return list;
    }

    public int hqlExecuteUpdate(String queryString, Map<String, Object> singleValueParamMap,
                         Map<String, Object[]> listValueParamMap) {
        Query query = getSession().createQuery(queryString);
        if(singleValueParamMap != null) {
            for(String key : singleValueParamMap.keySet()) {
                query.setParameter(key, singleValueParamMap.get(key));
            }
        }
        if(listValueParamMap != null) {
            for(String key : listValueParamMap.keySet()) {
                query.setParameterList(key, listValueParamMap.get(key));
            }
        }
        return query.executeUpdate();
    }

    public <S> List<S> findOtherClassByExample(S exampleInstance, String... excludeProperty) {
        Criteria crit = getSession().createCriteria(exampleInstance.getClass());
        Example example = Example.create(exampleInstance).ignoreCase().enableLike(MatchMode.ANYWHERE);
        for (String exclude : excludeProperty) {
            example.excludeProperty(exclude);
        }
        crit.add(example);
        return crit.list();
    }

    /* (non-Javadoc)
     * @see com.stixcloud.dao.common.IRootDAO#findByExample(com.stixcloud.domain.common.Root, java.lang.String[])
     */
    public List<T> findByExample(T exampleInstance, String... excludeProperty) {
        Criteria crit = getSession().createCriteria(super.getPersistentClass());
        Example example = Example.create(exampleInstance).ignoreCase().enableLike(MatchMode.START);
        for (String exclude : excludeProperty) {
            example.excludeProperty(exclude);
        }
        crit.add(example);
        return crit.list();
    }

    /* (non-Javadoc)
     * @see com.stixcloud.dao.common.IRootDAO#findByExampleMatchModeAnywhere(com.stixcloud.domain.common.Root, java.lang.String[])
     */
    public List<T> findByExampleMatchModeAnywhere(T exampleInstance, String... excludeProperty) {
        Criteria crit = getSession().createCriteria(super.getPersistentClass());
        Example example = Example.create(exampleInstance).ignoreCase().enableLike(MatchMode.ANYWHERE);
        for (String exclude : excludeProperty) {
            example.excludeProperty(exclude);
        }
        crit.add(example);
        return crit.list();
    }
}
