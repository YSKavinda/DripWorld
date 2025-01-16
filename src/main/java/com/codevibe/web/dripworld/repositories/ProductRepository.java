package com.codevibe.web.dripworld.repositories;

import com.codevibe.web.dripworld.dao.ProductDAO;
import com.codevibe.web.dripworld.entities.ProductEntity;
import com.codevibe.web.dripworld.util.HibernateUtil;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class ProductRepository {

    public List<ProductEntity> findAll(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            return session.createQuery("SELECT p FROM ProductEntity p",ProductEntity.class)
                    .getResultList();

        }
    }

    public List<ProductEntity> findAll(String query,Integer pageNo,Integer pageSize){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
          return   session.createQuery(query, ProductEntity.class)
                    .setFirstResult((pageNo-1)*pageSize)
                    .setMaxResults(pageSize)
                    .getResultList();
        }
    }

    public List<ProductEntity> findAll(String query){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery(query, ProductEntity.class)
                    .getResultList();
        }
    }

//    public List<ProductEntity>


    public Optional<ProductEntity> findById(Long id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.byId(ProductEntity.class).loadOptional(id);
        }
    }

    public Optional<ProductEntity> findByName(String name){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("SELECT p FROM ProductEntity p WHERE p.name=:name", ProductEntity.class)
                    .setParameter("name",name)
                    .uniqueResultOptional();
        }
    }

    @Transactional(rollbackOn = {Exception.class})
    public void save(ProductEntity productEntity){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.persist(productEntity);
            transaction.commit();
        }
    }

    @Transactional(rollbackOn = {Exception.class})
    public void update(ProductEntity productEntity){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.merge(productEntity);
            transaction.commit();
        }
    }

}
