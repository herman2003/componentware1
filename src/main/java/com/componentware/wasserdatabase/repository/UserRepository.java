package com.componentware.wasserdatabase.repository;

import com.componentware.wasserdatabase.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
    List<User> findAll();
    boolean existsByEmail(String email);
}
