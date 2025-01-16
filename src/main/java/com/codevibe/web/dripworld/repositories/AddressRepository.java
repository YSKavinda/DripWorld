package com.codevibe.web.dripworld.repositories;

import com.codevibe.web.dripworld.entities.AddressEntity;
import com.codevibe.web.dripworld.entities.OrdersEntity;
import com.codevibe.web.dripworld.util.HibernateUtil;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class AddressRepository {
    public List<AddressEntity> findAddressByOrderId(Long id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("SELECT a FROM AddressEntity a WHERE a.order.id=:id",AddressEntity.class)
                    .setParameter("id",id)
                    .getResultList();
        }
    }


    @Transactional(rollbackOn = {Exception.class})
    public void save(AddressEntity address){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.persist(address);
            transaction.commit();
        }
    }

    @Transactional(rollbackOn = {Exception.class})
    public void update(AddressEntity address){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.merge(address);
            transaction.commit();
        }
    }


}
