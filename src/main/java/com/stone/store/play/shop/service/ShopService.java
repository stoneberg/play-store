package com.stone.store.play.shop.service;

import com.stone.store.play.shop.domain.Cart;
import com.stone.store.play.shop.domain.CartProduct;
import com.stone.store.play.shop.domain.Product;
import com.stone.store.play.shop.payload.ShopReq;
import com.stone.store.play.shop.payload.ShopReq.AddCartDto;
import com.stone.store.play.shop.payload.ShopRes;
import com.stone.store.play.shop.payload.ShopRes.ProductDto;
import com.stone.store.play.shop.repository.CartProductRepository;
import com.stone.store.play.shop.repository.CartQuerydslRepository;
import com.stone.store.play.shop.repository.CartRepository;
import com.stone.store.play.shop.repository.ProductRepository;
import com.stone.store.play.user.domain.User;
import com.stone.store.play.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.stone.store.play.shop.payload.ShopRes.*;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ShopService {

    private static final String username = "stoneberg";
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartProductRepository cartProductRepository;
    private final CartQuerydslRepository cartQuerydslRepository;

    /**
     * 모든 상품 조회
     *
     * @return
     */
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(ProductDto::new).collect(Collectors.toList());
    }

    /**
     * 상품 단건 조회
     *
     * @param productId
     * @return
     */
    public ProductDto getProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Not Found Such Id Product: " + productId));
        return ProductDto.builder().entity(product).build();
    }

    /**
     * 선택된 상품 장바구니에 담기
     *
     * @param dto
     * @return
     */
    @Transactional
    public Long addProductToCart(AddCartDto dto) {
        Long productId = dto.getProductId();
        Integer quantity = dto.getQuantity();

        // find login user
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not exists | username: " + username));

        // find login user's cart(user has only one cart in this demo)
        Cart cart = cartRepository.findByUser(user).orElseGet(() -> Cart.builder().user(user).build());

        // save cart
        cartRepository.save(cart);

        // find product to add into cart
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not exists | id: " + productId));

        // create cart_product
        CartProduct cartProduct = CartProduct.createCartProduct(product, product.getPrice(), quantity);

        // add products to cart (dirty checking)
        cart.addCartProduct(cartProduct);

        return cart.getId();
    }

    /**
     * 장바구니에 담긴 상품 조회
     *
     * @return
     */
    public List<CartItemDto> getCartItems() {
        // find login user
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not exists | username: " + username));

        return cartQuerydslRepository.findCartItemsByUser(user);
    }

    /**
     * 장바구니에서 주문 정보 제거
     *
     * @param productId
     * @return
     */
    @Transactional
    public void removeItemFromCart(Long productId) {
        // find login user
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not exists | username: " + username));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("User cart not exists"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not exists | productId: " + productId));

        cartProductRepository.deleteByCartAndProduct(cart, product);
        // cartProductRepository.deleteByCartIdAndProductId(cart.getId(), productId);
    }

    /**
     * 장바구니에서 주문 정보 모두 제거
     */
    @Transactional
    public void clearCartItems() {
        // find login user
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not exists | username: " + username));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("User cart not exists"));

        cartRepository.delete(cart);
    }
}
