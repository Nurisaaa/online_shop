package com.example.online_shop.repositories;

import com.example.online_shop.dto.ProductResponse;
import com.example.online_shop.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select new com.example.online_shop.dto.ProductResponse(" +
            "p.id, " +
            "p.title, " +
            "p.price, " +
            "p.images, " +
            "p.category, " +
            "p.sizes, " +
            "p.color, " +
            "p.dateOfCreation) from Product p inner join p.sizes s where s = ?2 and p.category = ?1")
    List<ProductResponse> findByCategoryAndSizes(String category, String size);
}