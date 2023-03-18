package com.example.MyBookShopApp.config;

import com.example.MyBookShopApp.data.TestEntity;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.HibernateError;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

@Configuration
public class CommandLineRunnerImpl implements CommandLineRunner {
    EntityManagerFactory entityManagerFactory;

    @Autowired
    public CommandLineRunnerImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 5; i++) {
            createTestEntity(new TestEntity());
        }

        TestEntity readTestEntity = readTestEntityById(3L);
        if(readTestEntity != null){
            Logger.getLogger(CommandLineRunnerImpl.class.getSimpleName()).info("read " + readTestEntity.toString());
        }else {
            throw new NullPointerException();
        }

        TestEntity updatedTestEntity = updateTestEntity(5L);
        if(updatedTestEntity != null){
            Logger.getLogger(CommandLineRunnerImpl.class.getSimpleName()).info("update"+updatedTestEntity.toString());
        }else {
            throw new NullPointerException();
        }

        deleteTestEntityById(4L);
    }

    private void deleteTestEntityById(Long id)
    {
        Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            TestEntity findEntity = readTestEntityById(id);
            TestEntity mergedTestEntity = (TestEntity) session.merge(findEntity);
            session.remove(mergedTestEntity);
            tx.commit();
        } catch (HibernateException hex) {
            if (tx != null) {
                tx.rollback();
            } else {
                hex.printStackTrace();
            }
        } finally {
            session.close();
        }

    }

    private TestEntity updateTestEntity(Long id) {
        Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
        Transaction tx = null;
        TestEntity result = null;

        try {
            tx = session.beginTransaction();
            TestEntity findEntity = readTestEntityById(id);
            findEntity.setData("NEW DATA UPDATE");
            result = (TestEntity) session.merge(findEntity);
            tx.commit();
        } catch (HibernateException hex) {
            if (tx != null) {
                tx.rollback();
            } else {
                hex.printStackTrace();
            }
        } finally {
            session.close();
        }

        return result;
    }

    private TestEntity readTestEntityById(Long id) {
        Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
        Transaction tx = null;
        TestEntity result = null;

        try {
            tx = session.beginTransaction();
            result = session.find(TestEntity.class, id);
            tx.commit();
        } catch (HibernateException hex) {
            if (tx != null) {
                tx.rollback();
            } else {
                hex.printStackTrace();
            }
        } finally {
            session.close();
        }

        return result;
    }

    private void createTestEntity(TestEntity entity) {
        Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            entity.setData(entity.getClass().getSimpleName() + entity.hashCode());
            session.save(entity);
            tx.commit();
        } catch (HibernateException hex) {
            if (tx != null) {
                tx.rollback();
            } else {
                hex.printStackTrace();
            }
        } finally {
            session.close();
        }
    }
}
