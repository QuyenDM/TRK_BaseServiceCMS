package com.vfw5.base.dao;

import java.sql.Connection;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.internal.SessionFactoryImpl;

/**
 * Created by sadupa on 8/6/14.
 */
public class HibernateSessionFactoryImpl implements HibernateSessionFactory {

    private SessionFactory sessionFactory;

    @Override
    public Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        
     
    }
    
    @Override
    public Session openSession() {
        return this.sessionFactory.openSession();
    }
}
