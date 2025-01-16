package com.codevibe.web.dripworld.repositories;

import com.codevibe.web.dripworld.entities.CartEntity;
import com.codevibe.web.dripworld.entities.ProductEntity;
import com.codevibe.web.dripworld.entities.StockEntity;
import com.codevibe.web.dripworld.entities.UsersEntity;
import com.codevibe.web.dripworld.handler.types.ValidationFailedException;
import com.codevibe.web.dripworld.util.HibernateUtil;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class CartRepository {

    public List<CartEntity> findAllByUser(UsersEntity user){
           try(Session session = HibernateUtil.getSessionFactory().openSession()){
               return session.createQuery("SELECT c FROM CartEntity c WHERE c.usersByUsersId=:user", CartEntity.class)
                       .setParameter("user",user)
                       .getResultList();
           }
    }

    public Optional<CartEntity> findById(Long id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.byId(CartEntity.class).loadOptional(id);
        }
    }

    public Optional<CartEntity> findByIdAndUser(Long id,UsersEntity user){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("SELECT c FROM CartEntity c WHERE c.id=:id AND c.usersByUsersId=:user")
                    .setParameter("id",id)
                    .setParameter("user",user)
                    .uniqueResultOptional();
        }
    }

    public Optional<CartEntity> findByUserAndStock(UsersEntity user, StockEntity stock){

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("SELECT c FROM CartEntity c WHERE c.usersByUsersId =: user AND c.stockByStockId=:stock")
                    .setParameter("user",user)
                    .setParameter("stock",stock)
                    .uniqueResultOptional();
        }

    }

    @Transactional(rollbackOn = {Exception.class})
    public void save(CartEntity cartEntity){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.persist(cartEntity);
            transaction.commit();
        }
    }

    @Transactional(rollbackOn = {Exception.class})
    public void update(CartEntity cartEntity){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.merge(cartEntity);
            transaction.commit();
        }
    }

    @Transactional(rollbackOn = {Exception.class})
    public void deleteById(Long id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            if(findById(id).isPresent()){

                final CartEntity cartEntity = findById(id).get();
                session.remove(cartEntity);


            }else{

                throw new ValidationFailedException("Cart Item Not Found");

            }
            transaction.commit();
        }
    }

    @Transactional(rollbackOn = {Exception.class})
    public void deleteUserCart(UsersEntity usersEntity){
        try(Session session= HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.createQuery("DELETE FROM CartEntity c WHERE c.usersByUsersId=:user")
                    .setParameter("user",usersEntity)
                    .executeUpdate();
            transaction.commit();
        }
    }


}
