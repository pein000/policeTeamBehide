package com.fee.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by qiuwei on 2016/3/3.
 */
@Repository
public class BaseRepository {

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession(){
        return this.sessionFactory.getCurrentSession();
    }
}
