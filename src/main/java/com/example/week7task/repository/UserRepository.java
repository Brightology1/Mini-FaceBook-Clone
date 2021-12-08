package com.example.week7task.repository;

import com.example.week7task.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findPersonByEmail(String email);
}
