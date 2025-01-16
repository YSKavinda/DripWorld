package com.codevibe.web.dripworld.repositories;

import com.codevibe.web.dripworld.entities.UsersEntity;
import com.codevibe.web.dripworld.util.HibernateUtil;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;

public class UserRepository {

    public Optional<UsersEntity> findUserById(Long id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.byId(UsersEntity.class).loadOptional(id);
        }
    }


    public Optional<UsersEntity> findByEmail(String email){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("SELECT u FROM UsersEntity u WHERE u.email=:email",UsersEntity.class)
                    .setParameter("email",email)
                    .uniqueResultOptional();
        }
    }

    public Optional<UsersEntity> findByEmailOrContact(String email,String contact){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("SELECT u FROM UsersEntity u WHERE u.email=:email OR u.contact=:contact",UsersEntity.class)
                    .setParameter("email",email)
                    .setParameter("contact",contact)
                    .uniqueResultOptional();
        }
    }

    public Optional<UsersEntity> findByEmailAndPassword(String email,String password){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("SELECT u FROM UsersEntity u WHERE u.email=:email AND u.password=:password",UsersEntity.class)
                    .setParameter("email",email)
                    .setParameter("password",password)
                    .uniqueResultOptional();
        }
    }

    public Optional<UsersEntity> findByVerificationCode(String code){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("SELECT u FROM UsersEntity u WHERE u.verificationCode=:code",UsersEntity.class)
                    .setParameter("code",code)
                    .uniqueResultOptional();
        }
    }

    @Transactional(rollbackOn = {Exception.class})
    public void save(UsersEntity user){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        }
    }

    @Transactional(rollbackOn = {Exception.class})
    public void update(UsersEntity user){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
        }
    }

}
