package com.springboot.sample.application.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByDataStatusNot(Character dataStatus);
    Optional<User> findByIdAndDataStatusNot(Long id, Character dataStatus);
    Optional<User> findByEmailAndDataStatusNot(String email, Character dataStatus);
    Optional<User> findByUsernameAndDataStatusNot(String email, Character dataStatus);
    Optional<User> findByEmailAndIdNotAndDataStatusNot(String email, Long id, Character dataStatus);
    Optional<User> findByUsernameAndIdNotAndDataStatusNot(String username, Long id, Character dataStatus);
}
