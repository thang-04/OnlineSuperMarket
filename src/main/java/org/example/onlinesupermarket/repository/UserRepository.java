package org.example.onlinesupermarket.repository;

import org.example.onlinesupermarket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserId(Integer userId);
}
