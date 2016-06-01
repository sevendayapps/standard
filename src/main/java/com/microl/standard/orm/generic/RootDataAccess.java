package com.microl.standard.orm.generic;

import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessResourceFailureException;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by vietlk on 01/06/2016.
 */
public abstract class RootDataAccess<T extends RootEntity>
                implements DataAccess<T> {
    private Class<T> persistentClass;
    private String entityName;

    public RootDataAccess() {
    }

    public RootDataAccess(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    public RootDataAccess(String entityName, Class<T> persistentClass) {
        this.persistentClass = persistentClass;
        this.entityName = entityName;
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    public void setPersistentClass(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    abstract Object getSession();

    abstract void flushSession();

    abstract void closeSession();
}
