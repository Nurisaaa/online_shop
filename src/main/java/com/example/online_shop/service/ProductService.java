package com.example.online_shop.service;

import com.example.online_shop.dto.ProductRequest;
import com.example.online_shop.dto.ProductResponse;
import com.example.online_shop.dto.SimpleResponse;
import com.example.online_shop.entities.Product;
import com.example.online_shop.entities.User;
import com.example.online_shop.exceptions.NotFoundException;
import com.example.online_shop.repositories.CustomProductRepository;
import com.example.online_shop.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CustomProductRepository customProductRepository;

    public SimpleResponse save(ProductRequest productRequest) {
        Product product = new Product(productRequest);
        productRepository.save(product);
        return SimpleResponse.builder()
                .message("Product successfully saved!")
                .build();
    }

    @Transactional
    public SimpleResponse update(ProductRequest productRequest, Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("No product with such an id: %s", id))
        );
        product.setTitle(productRequest.getTitle());
        product.setPrice(productRequest.getPrice());
        product.setImages(productRequest.getImages());
        product.setColor(productRequest.getColor());
        product.setDateOfCreation(productRequest.getDateOfCreation());
        product.setCategory(productRequest.getCategory());
        product.setSizes(productRequest.getSizes());
        return SimpleResponse.builder()
                .message(String.format("Product with id: %s successfully updated!", id))
                .build();
    }

    public SimpleResponse delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException(String.format("No product with such an id: %s", id));
        }
        productRepository.deleteById(id);
        return SimpleResponse.builder()
                .message(String.format("Product with id: %s successfully deleted!", id))
                .build();
    }

    public ProductResponse findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("No product with such an id: %s", id))
        );
        return ProductResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .price(product.getPrice())
                .images(product.getImages())
                .category(product.getCategory())
                .sizes(product.getSizes())
                .color(product.getColor())
                .dateOfCreation(product.getDateOfCreation())
                .build();
    }

    public List<ProductResponse> findAll(String category, String size){
        return customProductRepository.getProducts(category, size);
    }

    public SimpleResponse addOrRemoveFromFavorites(Long id, Authentication authentication) {
        User user = (User) authentication.getAuthorities();
        String message = "";
        if (!user.getFavorites().isEmpty()) {
            for (Product product : user.getFavorites()) {
                if (product.getId().equals(id)) {
                    user.getFavorites().remove(product);
                    message = String.format("Product with id: %s successfully removed from favorites!", id);
                } else {
                    user.getFavorites().add(product);
                    message = String.format("Product with id: %s successfully added to favorites!", id);
                }
            }
        }else {
            Product product = productRepository.findById(id).orElseThrow(
                    () -> new NotFoundException(String.format("No product with such an id: %s", id))
            );
            user.getFavorites().add(product);
            message = String.format("Product with id: %s successfully added to favorites!", id);
        }
        return SimpleResponse.builder()
                .message(message)
                .build();
    }
}
