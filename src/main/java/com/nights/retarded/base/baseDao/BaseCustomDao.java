package com.nights.retarded.base.baseDao;

import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public abstract class BaseCustomDao {

    @Autowired
    protected EntityManager entityManager;

    protected <T> Query query(String sql, Class<T> clazz){
            Query query = entityManager.createNativeQuery(sql)
                    .unwrap(NativeQueryImpl.class)
                    .setResultTransformer(Transformers.aliasToBean(clazz));
            return query;
    }

    protected <T> Query query(StringBuffer sql, Class<T> clazz){
        return query(sql.toString(), clazz);
    }

    protected <T> List<T> queryResultList(StringBuffer sql, Class<T> clazz) {
        return query(sql, clazz).getResultList();
    }

    protected <T> T querySingleResult(StringBuffer sql, Class<T> clazz) {
        return (T)query(sql, clazz).getSingleResult();
    }

    protected <T> List<T> queryResultList(String sql, Class<T> clazz) {
        return query(sql, clazz).getResultList();
    }

    protected <T> T querySingleResult(String sql, Class<T> clazz) {
        return (T)query(sql, clazz).getSingleResult();
    }

}
