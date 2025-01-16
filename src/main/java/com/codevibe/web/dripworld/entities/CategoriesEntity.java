package com.codevibe.web.dripworld.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "categories", schema = "dripwrld_db", catalog = "")
public class CategoriesEntity extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;


    public CategoriesEntity(){

    }
    public CategoriesEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }
    @OneToMany(mappedBy = "categoriesByCategoriesId")
    private List<ProductEntity> productsById;

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


    public List<ProductEntity> getProductsById() {
        return productsById;
    }

    public void setProductsById(List<ProductEntity> productsById) {
        this.productsById = productsById;
    }
}
