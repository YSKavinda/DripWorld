package com.codevibe.web.dripworld.config;

import com.codevibe.web.dripworld.entities.CategoriesEntity;
import com.codevibe.web.dripworld.entities.ProductEntity;
import com.codevibe.web.dripworld.repositories.*;
import com.codevibe.web.dripworld.service.*;
import com.codevibe.web.dripworld.settingsRepository.SettingsRepository;
import com.codevibe.web.dripworld.util.Encryption;
import com.codevibe.web.dripworld.util.JwtTokenUtil;
import jakarta.inject.Singleton;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class DependencyBinder extends AbstractBinder {
    @Override
    protected void configure() {
        System.out.println("------------------Dependency Binder Working---------------");
        bind(Encryption.class).to(Encryption.class).in(Singleton.class);
        bind(JwtTokenUtil.class).to(JwtTokenUtil.class);

        bind(SettingsRepository.class).to(SettingsRepository.class);
        bind(UserRepository.class).to(UserRepository.class);
        bind(CategoryRepositories.class).to(CategoryRepositories.class);
        bind(ProductRepository.class).to(ProductRepository.class);
        bind(StockRepository.class).to(StockRepository.class);
        bind(CartRepository.class).to(CartRepository.class);
        bind(OrdersRepository.class).to(OrdersRepository.class);
        bind(AddressRepository.class).to(AddressRepository.class);
        bind(OrderItemsRepository.class).to(OrderItemsRepository.class);


        bind(UserService.class).to(UserService.class);
        bind(FileUploadService.class).to(FileUploadService.class);
        bind(CategoryService.class).to(CategoryService.class);
        bind(AuthService.class).to(AuthService.class);
        bind(ProductService.class).to(ProductService.class);
        bind(StockService.class).to(StockService.class);
        bind(CartService.class).to(CartService.class);
        bind(OrderService.class).to(OrderService.class);
        bind(SettingService.class).to(SettingService.class);



    }


}
