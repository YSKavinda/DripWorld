package com.codevibe.web.dripworld.repositories;

import com.codevibe.web.dripworld.entities.OrderItemsEntity;
import com.codevibe.web.dripworld.entities.OrdersEntity;
import com.codevibe.web.dripworld.util.HibernateUtil;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class OrderItemsRepository {

    public List<OrderItemsEntity> getOrderItemsByOrderId(Long id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
           return session.createQuery("SELECT i FROM OrderItemsEntity i WHERE i.ordersByOrdersId.id=:id")
                    .setParameter("id",id)
                    .getResultList();

        }
    }

    @Transactional(rollbackOn = {Exception.class})
    public void save(OrderItemsEntity orderItem){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.persist(orderItem);
            transaction.commit();
        }
    }

}
