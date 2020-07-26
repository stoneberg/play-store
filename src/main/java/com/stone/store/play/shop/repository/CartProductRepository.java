package com.stone.store.play.shop.repository;

import com.stone.store.play.shop.domain.Cart;
import com.stone.store.play.shop.domain.CartProduct;
import com.stone.store.play.shop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    void deleteByCartIdAndProductId(Long cartid, Long productId);
    void deleteByCartAndProduct(Cart cart, Product product);
}
