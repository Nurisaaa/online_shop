package com.example.online_shop.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProductRequest {
    @NonNull
    @NotBlank
    private String title;
    @NotBlank
    private int price;
    private MultipartFile image;
    @NonNull
    @NotBlank
    private String category;
    @NonNull
    @NotBlank
    private List<String> sizes;
    private String color;
    @NonNull
    @NotBlank
    private LocalDate dateOfCreation;
}
