package com.itboy.DACNPM.Controller;

import com.itboy.DACNPM.Service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.itboy.DACNPM.Service.DocumentService;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {

    @Autowired
    private UserSevice userSevice;
    //    @GetMapping("/Login")
//    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest) {
//        int userId = userSevice.check(loginRequest.getEmail(), loginRequest.getPassword());
//        if (userId != 0) {
//            Optional<User> user = userSevice.finUserby(userId);
//            if (user.isPresent()) {
//                return ResponseEntity.ok(user.get());
//            }
//        }
//        return ResponseEntity.notFound().build();
//    }
    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        int userId = userSevice.check(email, password);

        if (userId != 0) {
            return ResponseEntity.ok("Login successful. User ID: " + userId);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

}