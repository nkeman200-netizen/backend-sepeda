package com.kampus.sepedaweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kampus.sepedaweb.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    User findUserById(Integer  id);
    User findUserByUsername(String username);
}
