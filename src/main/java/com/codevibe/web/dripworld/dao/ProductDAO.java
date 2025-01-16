package com.codevibe.web.dripworld.dao;



import com.codevibe.web.dripworld.entities.CategoriesEntity;

import java.util.List;

public class ProductDAO {

    private Long id;

    private String name;

    private String description;

    private List<String> images;

    private CategoryDAO categoriesByCategoriesId;

    public ProductDAO(Long id, String name, String description, List<String> images, CategoryDAO categoriesByCategoriesId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.images = images;
        this.categoriesByCategoriesId = categoriesByCategoriesId;
    }

    public ProductDAO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public CategoryDAO getCategoriesByCategoriesId() {
        return categoriesByCategoriesId;
    }

    public void setCategoriesByCategoriesId(CategoryDAO categoriesByCategoriesId) {
        this.categoriesByCategoriesId = categoriesByCategoriesId;
    }
}
