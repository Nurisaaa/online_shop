package com.example.online_shop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
public class ProductResponse {
    private Long id;
    private String title;
    private int price;
    private List<String> images;
    private String category;
    private List<String> sizes;
    private String color;
    private LocalDate dateOfCreation;
}
