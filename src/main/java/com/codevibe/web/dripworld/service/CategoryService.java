package com.codevibe.web.dripworld.service;

import com.codevibe.web.dripworld.dao.CategoryDAO;
import com.codevibe.web.dripworld.entities.CategoriesEntity;
import com.codevibe.web.dripworld.handler.types.ValidationFailedException;
import com.codevibe.web.dripworld.repositories.CategoryRepositories;
import jakarta.inject.Inject;
import org.apache.tomcat.util.http.fileupload.impl.InvalidContentTypeException;

import java.util.ArrayList;
import java.util.List;

public class CategoryService {
    @Inject
    private CategoryRepositories categoryRepositories;

    public List<CategoryDAO> getAll(){

        final List<CategoryDAO> list = new ArrayList<>();
        categoryRepositories.findAll().forEach(category->{
            final CategoryDAO dao = new CategoryDAO(category.getId(),category.getName(), category.getDescription());
            list.add(dao);
        });
        return list;

    }

    public void save(CategoryDAO categoryDAO){
        if(categoryDAO == null|| categoryDAO.getName() == null || categoryDAO.getName().isBlank() || categoryDAO.getDescription() == null || categoryDAO.getDescription().isBlank())
            throw new ValidationFailedException();
        if(categoryRepositories.findByName(categoryDAO.getName()).isPresent())
            throw new ValidationFailedException("Category Exist");

        CategoriesEntity category = new CategoriesEntity(categoryDAO.getName(),categoryDAO.getDescription());

        categoryRepositories.save(category);
    }

}
