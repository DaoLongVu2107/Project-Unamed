package com.itboy.DACNPM.Repository;


import com.itboy.DACNPM.Enity.Document;
import com.itboy.DACNPM.Enity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface    UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    boolean existsByPhone(String newPhoneNumber);
}