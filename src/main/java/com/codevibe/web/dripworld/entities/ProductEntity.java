package com.codevibe.web.dripworld.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "product", schema = "dripwrld_db", catalog = "")
public class ProductEntity extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "description")
    private String description;


//    @ManyToOne
//    @JoinColumn(name = "vendors_id", referencedColumnName = "id", nullable = false)
//    private VendorsEntity vendorsByVendorsId;
    @ManyToOne
    @JoinColumn(name = "categories_id", referencedColumnName = "id", nullable = false)
    private CategoriesEntity categoriesByCategoriesId;
    @OneToMany(mappedBy = "productByProductId")
    private Collection<StockEntity> stocksById;

    @CollectionTable(name = "product_image", joinColumns = @JoinColumn(name = "product_id"))
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "image")
    private List<String> images;

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
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



//    public VendorsEntity getVendorsByVendorsId() {
//        return vendorsByVendorsId;
//    }
//
//    public void setVendorsByVendorsId(VendorsEntity vendorsByVendorsId) {
//        this.vendorsByVendorsId = vendorsByVendorsId;
//    }

    public CategoriesEntity getCategoriesByCategoriesId() {
        return categoriesByCategoriesId;
    }

    public void setCategoriesByCategoriesId(CategoriesEntity categoriesByCategoriesId) {
        this.categoriesByCategoriesId = categoriesByCategoriesId;
    }

    public Collection<StockEntity> getStocksById() {
        return stocksById;
    }

    public void setStocksById(Collection<StockEntity> stocksById) {
        this.stocksById = stocksById;
    }
}
