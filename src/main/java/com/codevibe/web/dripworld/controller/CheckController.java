package com.codevibe.web.dripworld.controller;

import com.codevibe.web.dripworld.entities.CategoriesEntity;
import com.codevibe.web.dripworld.util.HibernateUtil;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.hibernate.Session;

import java.util.List;

@Path("/test")
public class CheckController {
    @GET
    public Response test(){

        try{
            Session session = HibernateUtil.getSessionFactory().openSession();
            List<CategoriesEntity> categories = session.createQuery("SELECT c FROM CategoriesEntity c", CategoriesEntity.class).getResultList();
            categories.forEach(category->{
                System.out.println(category.getName());
            });

        }catch (Exception ex){

        }

        return Response.ok().entity("Test").build();
    }
}
