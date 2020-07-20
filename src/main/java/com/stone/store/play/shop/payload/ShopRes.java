package com.stone.store.play.shop.payload;

import com.stone.store.play.shop.domain.Cart;
import com.stone.store.play.shop.domain.Product;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShopRes {

    @Data
    public static class ProductDto {
        private Long id;
        private String title;
        private String description;
        private String image;
        private Integer price;

        @Builder
        public ProductDto(Product entity) {
            this.id = entity.getId();
            this.title = entity.getTitle();
            this.description = entity.getDescription();
            this.image = entity.getImage();
            this.price = entity.getPrice();
        }

    }

//    @Data
//    public static class CartDto {
//        private Long id;
//        private List<ProductDto> products = new ArrayList<>();
//
//        @Builder
//        public CartDto(Cart entity) {
//            this.id = entity.getId();
//            this.products = entity.getCartProducts()
//                    .stream().map(ProductDto::new)
//                    .collect(Collectors.toList());
//
//        }
//    }

}
