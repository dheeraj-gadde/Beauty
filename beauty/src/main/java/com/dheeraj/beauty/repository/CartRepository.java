package com.dheeraj.beauty.repository;

import com.dheeraj.beauty.model.Cart;
import com.dheeraj.beauty.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
