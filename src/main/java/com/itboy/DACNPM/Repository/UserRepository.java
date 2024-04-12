package com.itboy.DACNPM.Repository;

import com.itboy.DACNPM.Enity.Document;
import com.itboy.DACNPM.Enity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


}
