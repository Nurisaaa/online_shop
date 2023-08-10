package com.example.online_shop.api;

import com.example.online_shop.dto.ProductRequest;
import com.example.online_shop.dto.ProductResponse;
import com.example.online_shop.dto.SimpleResponse;
import com.example.online_shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@CrossOrigin
public class ProductApi {

    private final ProductService productService;

    @PostAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    SimpleResponse saveProduct(@RequestBody ProductRequest productRequest) throws IOException {
        return productService.save(productRequest);
    }

    @PostAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    SimpleResponse deleteProduct(@PathVariable Long id) {
        return productService.delete(id);
    }

    @PostAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    SimpleResponse updateProduct(@RequestBody ProductRequest productRequest, @PathVariable Long id) throws IOException {
        return productService.update(productRequest, id);
    }

    @PostAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
    @GetMapping("/{id}")
    ProductResponse findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
    @GetMapping
    List<ProductResponse> findAll(@RequestParam(required = false) String category, @RequestParam(required = false) String size) {
        return productService.findAll(category, size);
    }

    @PostAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
    @PostMapping("/favorites")
    SimpleResponse addOrRemoveFromFavorites(@RequestParam Long id, Authentication authentication) {
        return productService.addOrRemoveFromFavorites(id, authentication);
    }

    List<ProductResponse> getFavorites(Authentication authentication) {
        return productService.getFavorites(authentication);
    }
}
