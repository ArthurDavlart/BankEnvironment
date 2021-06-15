package com.bank.api.db.hibernate;

import com.bank.api.db.hibernate.entities.MClassEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HibernateTests {

//    <property name="hibernate.connection.driver_class">org.h2.Driver</property>
//		<property name="hibernate.connection.username">sa</property>
//		<property name="hibernate.connection.password"></property>
//		<property name="hibernate.connection.url">jdbc:h2:mem:test</property>
//		<property name="hibernate.dialect">org.hibernate.dialect.H2Dialect</property>
//		<property name="show_sql">true</property>
//		<property name="connection.pool_size">5</property>
//		<property name="hibernate.id.new_generator_mappings">false</property>
//		<property name="hibernate.hbm2ddl.auto">create</property><
    private static StandardServiceRegistryBuilder standardServiceRegistryBuilder;
    private ServiceRegistry serviceRegistry;

    private MetadataSources metadataSources;
    private MetadataBuilder metadataBuilder;

    private Metadata metadata;
    private SessionFactory sessionFactory;

    @BeforeAll
    static void initAll(){
        standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();

        standardServiceRegistryBuilder
                .applySetting("hibernate.connection.driver_class", "org.h2.Driver")
                .applySetting("hibernate.connection.username", "sa")
                .applySetting("hibernate.connection.password", "sa")
                .applySetting("hibernate.connection.url", "jdbc:h2:/Users/davletkalievartur/Desktop/mProjects/db/htests")
                .applySetting("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
                .applySetting("hibernate.current_session_context_class", "thread")
                .applySetting("hibernate.format_sql", "true")
                .applySetting("hibernate.use_sql_comments", "true")
                .applySetting("hibernate.hbm2ddl.auto", "create");

    }

    @BeforeEach
    void init(){
        serviceRegistry = standardServiceRegistryBuilder.build();
        metadataSources = new MetadataSources(serviceRegistry);

        metadataSources.addAnnotatedClass(com.bank.api.db.hibernate.entities.MClassEntity.class);

        metadataBuilder = metadataSources.getMetadataBuilder();
        metadata = metadataBuilder.build();
        sessionFactory = metadata.buildSessionFactory();
    }

    @Test
    void test(){
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        MClassEntity mClassEntity = new MClassEntity();
        mClassEntity.setIntField(111);

        session.persist(mClassEntity);

        tx.commit();
        session.close();

        session = sessionFactory.getCurrentSession();
        tx = session.beginTransaction();

        session.persist(mClassEntity);
    }
}
