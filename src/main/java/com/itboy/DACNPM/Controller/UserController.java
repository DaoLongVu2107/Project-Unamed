package com.itboy.DACNPM.Controller;

import com.itboy.DACNPM.Enity.User;
import com.itboy.DACNPM.Service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.itboy.DACNPM.Service.DocumentService;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {

    @Autowired
    private UserSevice userSevice;

        @PostMapping("/login")                      
    public ResponseEntity<?> login(@RequestBody User loginUser) {
        User user = userSevice.findUserByEmail(loginUser.getEmail());
        if (user != null && user.getPassword().equals(loginUser.getPassword())) {
            // Xử lý đăng nhập thành công
            return ResponseEntity.ok().body("Đăng nhập thành công!");
        } else {
            // Xử lý đăng nhập thất bại
            return ResponseEntity.badRequest().body("Thông tin đăng nhập không chính xác!");
        }
    }
}