package com.stone.store.play.user.repository;

import com.stone.store.play.shop.domain.Cart;
import com.stone.store.play.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
