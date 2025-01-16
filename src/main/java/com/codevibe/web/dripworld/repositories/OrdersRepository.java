package com.codevibe.web.dripworld.repositories;

import com.codevibe.web.dripworld.entities.OrdersEntity;
import com.codevibe.web.dripworld.entities.UsersEntity;
import com.codevibe.web.dripworld.util.HibernateUtil;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class OrdersRepository {
    public List<OrdersEntity> findAll(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("SELECT o FROM OrdersEntity o",OrdersEntity.class)
                    .getResultList();
        }
    }
    public List<OrdersEntity> findAllByUser(UsersEntity user){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("SELECT o FROM OrdersEntity o WHERE o.usersByUsersId=:user",OrdersEntity.class)
                    .setParameter("user",user)
                    .getResultList();
        }
    }

    public Optional<OrdersEntity> findByIdandUser(Long id,UsersEntity user){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("SELECT o FROM OrdersEntity o WHERE o.id=:id AND o.usersByUsersId=:user")
                    .setParameter("id",id)
                    .setParameter("user",user)
                    .uniqueResultOptional();
        }
    }

    public Optional<OrdersEntity> findByCodeAndUser(String code,UsersEntity user){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("SELECT o FROM OrdersEntity o WHERE o.code=:code AND o.usersByUsersId=:user")
                    .setParameter("code",code)
                    .setParameter("user",user)
                    .uniqueResultOptional();
        }
    }

    public Optional<OrdersEntity> findById(Long id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.byId(OrdersEntity.class).loadOptional(id);
        }
    }

    @Transactional(rollbackOn = {Exception.class})
    public void save(OrdersEntity order){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.persist(order);
            transaction.commit();
        }
    }

    @Transactional(rollbackOn = {Exception.class})
    public void update(OrdersEntity order){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.merge(order);
            transaction.commit();
        }
    }
}
