package com.springboot.sample.application.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByDataStatusNot(Character dataStatus);
    Optional<Product> findByIdAndDataStatusNot(Long id, Character dataStatus);
}
