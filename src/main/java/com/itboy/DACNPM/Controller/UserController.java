package com.itboy.DACNPM.Controller;

import com.itboy.DACNPM.DTO.LoginDTO;
import com.itboy.DACNPM.DTO.UpdateUserDTO;
import com.itboy.DACNPM.DTO.UpdateUserDTOByAdmin;
import com.itboy.DACNPM.DTO.UserDTO;
import com.itboy.DACNPM.Enity.User;
import com.itboy.DACNPM.Service.UserSevice;
import com.itboy.DACNPM.reponses.ApiResponse;
import com.itboy.DACNPM.reponses.LoginReponse;
import com.itboy.DACNPM.reponses.RegisterResponse;
import com.itboy.DACNPM.reponses.UserResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    @Autowired
    private UserSevice userSevice;
    @PostMapping("/register")
    //can we register an "admin" user ?
    public ResponseEntity<RegisterResponse> createUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult result
    ) {
        RegisterResponse registerResponse = new RegisterResponse();

        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            registerResponse.setMessage(errorMessages.toString());
            return ResponseEntity.badRequest().body(registerResponse);
        }

        if (!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
            registerResponse.setMessage("RetypePassword that does not match");
            return ResponseEntity.badRequest().body(registerResponse);
        }

        try {
            User user = userSevice.createUser(userDTO);
            registerResponse.setMessage("REGISTER_SUCCESSFULLY");
            registerResponse.setUser(user);
            return ResponseEntity.ok(registerResponse);
        } catch (Exception e) {
            registerResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(registerResponse);
        }
    }
        @PostMapping("/login")                      
    public ResponseEntity<?> login(@RequestBody LoginDTO loginUser) {
            try {
                String token = userSevice.login(
                        loginUser.getEmail(),
                        loginUser.getPassword(),
                        loginUser.getPassword() == null ? 1 : loginUser.getRoleId()
                );
                // Trả về token trong response
                return ResponseEntity.ok(LoginReponse.builder()
                        .message("LOGIN_SUCCESSFULLY")
                        .token(token)
                        .build());

            } catch (Exception e) {
                return ResponseEntity.badRequest().body(
                        LoginReponse.builder()
                                .message( e.getMessage())
                                .build()
                );
            }
    }
    @PostMapping("/details")

    public ResponseEntity<ApiResponse> getUserDetails(
            @RequestHeader("Authorization") String authorizationHeader
    ) throws Exception {
        String extractedToken = authorizationHeader.substring(7); // Loại bỏ "Bearer " từ chuỗi token
        User user = userSevice.getUserDetailsFromToken(extractedToken);
        return ResponseEntity.ok().body(
                ApiResponse.builder()
                        .message("Get user's detail successfully")
                        .data(UserResponse.fromUser(user))
                        .build()
        );
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id,
                                        @RequestBody UpdateUserDTO updatedUserDTO) throws Exception {
        return ResponseEntity.ok().body(userSevice.updateUser(id,updatedUserDTO));
    }
    @PutMapping("/updateByAdmin/{id}")//Admin
    public ResponseEntity<?> updateUserByAdmin(@PathVariable("id") Long id,
                                               @RequestBody UpdateUserDTOByAdmin updatedUserDTO) throws Exception {
        return ResponseEntity.ok().body(userSevice.updateUserByAdmin(id,updatedUserDTO));
    }
    @GetMapping("/getAll")//admin
    public ResponseEntity<?> getAllUser(){
        return ResponseEntity.ok().body(userSevice.findallUser());
    }
}