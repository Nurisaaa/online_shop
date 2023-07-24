package com.example.online_shop.entities;

import com.example.online_shop.dto.ProductRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "products")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_seq", allocationSize = 1, initialValue = 6)
    private Long id;
    private String title;
    private int price;
    @ElementCollection
    private List<String> images;
    private String category;
    @ElementCollection
    private List<String> sizes;
    private String color;
    private LocalDate dateOfCreation;

    public Product(ProductRequest productRequest) {
        this.title = productRequest.getTitle();
        this.price = productRequest.getPrice();
        this.images = productRequest.getImages();
        this.category = productRequest.getCategory();
        this.sizes = productRequest.getSizes();
        this.color = productRequest.getColor();
        this.dateOfCreation = productRequest.getDateOfCreation();
    }
}
