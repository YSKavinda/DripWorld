package com.codevibe.web.dripworld.repositories;

import com.codevibe.web.dripworld.entities.CategoriesEntity;
import com.codevibe.web.dripworld.util.HibernateUtil;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class CategoryRepositories {
    public List<CategoriesEntity> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT c FROM CategoriesEntity c", CategoriesEntity.class)
                    .getResultList();
        }
    }

    public Optional<CategoriesEntity> findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.byId(CategoriesEntity.class).loadOptional(id);
        }
    }

    public Optional<CategoriesEntity> findByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT c FROM CategoriesEntity c WHERE c.name=:name")
                    .setParameter("name", name)
                    .uniqueResultOptional();
        }
    }

    @Transactional(rollbackOn = {Exception.class})
    public void save(CategoriesEntity category) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(category);
            transaction.commit();
        }
    }


}
