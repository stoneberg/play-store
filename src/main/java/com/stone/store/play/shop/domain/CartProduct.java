package com.stone.store.play.shop.domain;

import com.stone.store.play.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "cart_product")
public class CartProduct extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "integer default 0")
    private Integer price;

    @Column(columnDefinition = "integer default 0")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public CartProduct(Product product, Integer price, Integer quantity) {
        this.product = product;
        this.price = price;
        this.quantity = quantity;
    }

    // create cartProduct
    public static CartProduct createCartProduct(Product product, int price, int quantity) {
        return CartProduct.builder()
                .product(product)
                .price(price)
                .quantity(quantity)
                .build();
    }

    public void updateCart(Cart cart) {
        this.cart = cart;
    }

}
