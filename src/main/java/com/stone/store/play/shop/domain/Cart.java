package com.stone.store.play.shop.domain;

import com.stone.store.play.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "carts")
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartProduct> cartProducts = new ArrayList<>();

    // create Cart
    public static Cart createCart(CartProduct... cartProducts) {
        Cart cart = new Cart();
        for (CartProduct cartProduct : cartProducts) {
            cart.addCartProduct(cartProduct);
        }
        return cart;
    }

    public void addCartProduct(CartProduct cartProduct) {
        this.cartProducts.add(cartProduct);
        if (cartProduct.getCart() != this) {
            cartProduct.updateCart(this);
        }
    }

}
