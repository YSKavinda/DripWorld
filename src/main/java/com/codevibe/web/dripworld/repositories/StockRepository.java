package com.codevibe.web.dripworld.repositories;

import com.codevibe.web.dripworld.entities.ProductEntity;
import com.codevibe.web.dripworld.entities.StockEntity;
import com.codevibe.web.dripworld.util.HibernateUtil;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class StockRepository {

    public List<StockEntity> findAll(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
         return session.createQuery("SELECT s FROM StockEntity s",StockEntity.class).getResultList();
        }
    }

    public List<StockEntity> findAll(String query,Integer pageNo,Integer pageSize){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery(query,StockEntity.class)
                    .setFirstResult((pageNo-1)*pageSize)
                    .setMaxResults(pageSize)
                    .getResultList();
        }
    }

    public List<StockEntity> findAll(String query){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery(query, StockEntity.class)
                    .getResultList();
        }
    }

    public List<StockEntity> findByProduct(ProductEntity product){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("SELECT s FROM StockEntity s WHERE s.productByProductId=:product",StockEntity.class)
                    .setParameter("product",product).getResultList();
        }

    }

    public List<StockEntity> inStockList(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("SELECT s FROM StockEntity s WHERE qty>0",StockEntity.class).getResultList();
        }
    }

    public Optional<StockEntity> findById(Long id){
        System.out.println("STOCK_ID::::::::::::"+id);
        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            return session.byId(StockEntity.class).loadOptional(id);
        }
    }


    @Transactional(rollbackOn = {Exception.class})
    public void save(StockEntity stockEntity){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.persist(stockEntity);
            transaction.commit();
        }
    }

    @Transactional(rollbackOn = {Exception.class})
    public void update(StockEntity stockEntity){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.merge(stockEntity);
            transaction.commit();
        }
    }

}
