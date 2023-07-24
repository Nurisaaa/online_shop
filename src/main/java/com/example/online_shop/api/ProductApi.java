package com.example.online_shop.api;

import com.example.online_shop.dto.ProductRequest;
import com.example.online_shop.dto.ProductResponse;
import com.example.online_shop.dto.SimpleResponse;
import com.example.online_shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductApi {

    private final ProductService productService;

    @PostAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    SimpleResponse saveProduct(@RequestBody ProductRequest productRequest) {
        return productService.save(productRequest);
    }

    @PostAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    SimpleResponse deleteProduct(@PathVariable Long id) {
        return productService.delete(id);
    }

    @PostAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    SimpleResponse updateProduct(@RequestBody ProductRequest productRequest, @PathVariable Long id) {
        return productService.update(productRequest, id);
    }

    @PostAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
    @GetMapping("/{id}")
    ProductResponse findById(@PathVariable Long id) {
        return productService.findById(id);
    }
}
