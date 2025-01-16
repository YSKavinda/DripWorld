package com.codevibe.web.dripworld.repositories;

import com.codevibe.web.dripworld.constants.SettingType;
import com.codevibe.web.dripworld.entities.SettingsEntity;
import com.codevibe.web.dripworld.util.HibernateUtil;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class SettingsRepository {
    public List<SettingsEntity> findAll(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("SELECT s FROM SettingsEntity s",SettingsEntity.class)
                    .getResultList();
        }
    }
    public Optional<SettingsEntity> findByName(SettingType name){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("SELECT s FROM SettingsEntity s WHERE s.name=:name",SettingsEntity.class)
                    .setParameter("name",name)
                    .uniqueResultOptional();
        }
    }

    @Transactional(rollbackOn = {Exception.class})
    public void save(SettingsEntity settings){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.persist(settings);
            transaction.commit();
        }
    }


}
