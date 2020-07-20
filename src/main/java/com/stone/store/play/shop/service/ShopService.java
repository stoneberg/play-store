package com.stone.store.play.shop.service;

import com.stone.store.play.shop.domain.Cart;
import com.stone.store.play.shop.domain.CartProduct;
import com.stone.store.play.shop.domain.Product;
import com.stone.store.play.shop.payload.ShopRes.ProductDto;
import com.stone.store.play.shop.repository.CartRepository;
import com.stone.store.play.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ShopService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    // Order
    public Long createCart(Long productId, int quantity) {
        // find product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("not exist product id: " + productId));

        // create cart_product
        CartProduct cartProduct = CartProduct.createCartProduct(product, product.getPrice(),quantity);

        // create cart
        Cart cart = Cart.createCart(cartProduct);

        // save cart
        cartRepository.save(cart);

        return cart.getId();
    }


    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(ProductDto::new).collect(Collectors.toList());
    }
}
