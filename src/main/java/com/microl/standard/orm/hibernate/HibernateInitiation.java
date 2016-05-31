package com.microl.standard.orm.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by vietlk on 31/05/2016.
 */


@Transactional
@EnableTransactionManagement
@ComponentScan("com.vsi.careersexpo.entity")
public class HibernateInitiation {
    public SessionFactory getSessionFactory() {
        Properties hibernateProperties = getHibernateProperties();
        DataSource dataSource = getDataSource();
        LocalSessionFactoryBean localSessionFactoryBean = generateSessionFactoryBean(new String[] { "com.vsi.careersexpo.entity" },
                dataSource, hibernateProperties);

        SessionFactory sessionFactory = localSessionFactoryBean.getObject();

        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);

        return sessionFactory;
    }

    private DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
        dataSource.setUrl("jdbc:oracle:thin:@192.168.0.66:1521/xe");
        dataSource.setUsername("wpsdb");
        dataSource.setPassword("Passw0rd");

        return dataSource;
    }

    private static LocalSessionFactoryBean generateSessionFactoryBean(String[] basePackages, DataSource dataSource,
                                                                      Properties hibernateProperties) {

        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource);
        localSessionFactoryBean.setPackagesToScan(basePackages);
        localSessionFactoryBean.setHibernateProperties(hibernateProperties);

        try {
            localSessionFactoryBean.afterPropertiesSet();
        } catch (IOException ex) {
            System.out.println("IOException");
        }

        return localSessionFactoryBean;
    }

    private static Properties getHibernateProperties() {

        Properties hibernateProperties = new Properties();

        hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
        hibernateProperties.put("hibernate.show_sql", false);
        hibernateProperties.put("hibernate.generate_statistics", false);
        hibernateProperties.put("hibernate.hbm2ddl.auto", "update");
        hibernateProperties.put("hibernate.use_sql_comments", false);

        return hibernateProperties;
    }
}
