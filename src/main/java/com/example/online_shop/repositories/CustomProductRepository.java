package com.example.online_shop.repositories;

import com.example.online_shop.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

//@Repository
//@RequiredArgsConstructor
//@Slf4j
//public class CustomProductRepository {
//    private final JdbcTemplate jdbcTemplate;
//    public List<ProductResponse> getProducts(String category, String size) {
//        String sql = """
//                    SELECT p.id as id,
//                 p.title as title,
//                 p.price as price,
//                 p.category as category,
//                 p.color as color,
//                 p.date_of_creation as dateOfCreation
//                FROM products p
//                LEFT JOIN product_sizes as ps ON p.id = ps.product_id
//                WHERE (:category IS NULL OR p.category = :category)
//                AND (:size IS NULL OR ps.sizes = :size)
//                GROUP BY p.id
//                ORDER BY p.date_of_creation DESC
//                                                   """;
//
//                List<ProductResponse> productResponse = jdbcTemplate.query(
//                sql,
//                (resultSet, row) -> ProductResponse.builder()
//                        .id(resultSet.getLong("id"))
//                        .title(resultSet.getString("title"))
//                        .price(resultSet.getInt("price"))
//                        .category(resultSet.getString("category"))
//                        .color(resultSet.getString("color"))
//                        .dateOfCreation(resultSet.getDate("dateOfCreation").toLocalDate())
//                        .build(),
//                category, size
//        );
//       String getSizes = """
//                SELECT sizes FROM product_sizes WHERE product_id = ?
//                """;
//       String getImages = """
//                SELECT images FROM product_images WHERE product_id = ?
//                """;
//        for (ProductResponse response : productResponse) {
//            List<String> sizes = jdbcTemplate.query(getSizes, (resultSet, row) ->
//                            resultSet.getString("sizes"),
//                    response.getId()
//            );
//            response.setSizes(sizes);
//            List<String> images = jdbcTemplate.query(getImages, (resultSet, row) ->
//                            resultSet.getString("images"),
//                    response.getId());
//            response.setImages(images);
//        }
//        return productResponse;
//    }
//}

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomProductRepository {
    private final JdbcTemplate jdbcTemplate;

    public CustomProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProductResponse> getProducts(String category, String size) {
        String sql = """
                SELECT p.id as id,
                       p.title as title,
                       p.price as price,
                       p.category as category,
                       p.color as color,
                       p.date_of_creation as dateOfCreation
                FROM products p
                LEFT JOIN product_sizes as ps ON p.id = ps.product_id
                WHERE (? IS NULL OR p.category = ?)
                AND (? IS NULL OR ps.sizes = ?)
                GROUP BY p.id
                ORDER BY p.date_of_creation DESC
        """;

        List<ProductResponse> productResponse = jdbcTemplate.query(
                sql,
                (resultSet, row) -> ProductResponse.builder()
                        .id(resultSet.getLong("id"))
                        .title(resultSet.getString("title"))
                        .price(resultSet.getInt("price"))
                        .category(resultSet.getString("category"))
                        .color(resultSet.getString("color"))
                        .dateOfCreation(resultSet.getDate("dateOfCreation").toLocalDate())
                        .build(),
                category,category,size,size
        );

        String getSizes = """
                SELECT sizes FROM product_sizes WHERE product_id = ?
        """;
        String getImages = """
                SELECT images FROM product_images WHERE product_id = ?
        """;

        for (ProductResponse response : productResponse) {
            // Pass response.getId() as a parameter for getSizes and getImages queries
            List<String> sizes = jdbcTemplate.query(getSizes, (resultSet, row) ->
                            resultSet.getString("sizes"),
                    response.getId()
            );
            response.setSizes(sizes);

            List<String> images = jdbcTemplate.query(getImages, (resultSet, row) ->
                            resultSet.getString("images"),
                    response.getId()
            );
            response.setImages(images);
        }

        return productResponse;
    }
}