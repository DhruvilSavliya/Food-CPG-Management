package com.food.cpg.services;

import com.food.cpg.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUserRepository extends JpaRepository<User, Integer>{
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    public User findByEmail(String email);
}
