package com.itboy.DACNPM.Service;

import com.itboy.DACNPM.DTO.UpdateUserDTO;
import com.itboy.DACNPM.DTO.UpdateUserDTOByAdmin;
import com.itboy.DACNPM.DTO.UserDTO;
import com.itboy.DACNPM.Enity.Role;
import com.itboy.DACNPM.Enity.User;
import com.itboy.DACNPM.Repository.RoleRepository;
import com.itboy.DACNPM.Repository.UserRepository;
import com.itboy.DACNPM.components.JwtTokenUtils;
import com.itboy.DACNPM.exceptions.DataNotFoundException;
import com.itboy.DACNPM.exceptions.ExpiredTokenException;
import com.itboy.DACNPM.exceptions.PermissionDenyException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserSevice {
    public final UserRepository userRepository;
    private  final RoleRepository roleRepository;
    private  final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    public User createUser(User user){
        ///
        return userRepository.save(user) ;
    }
    public Optional<User> finUserby(Long id){
        return userRepository.findById(id);
    }
    public List<User> findallUser(){
        return userRepository.findAll();
    }
  

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public String  login(String email, String password,   Long roleId) throws Exception {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()) {
            throw new DataNotFoundException("WRONG_EMAIL_PASSWORD");
        }
        //return optionalUser.get();//muốn trả JWT token ?
        User existingUser = optionalUser.get();
        //check password
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if(optionalRole.isEmpty() || roleId!=existingUser.getRole().getIdRole()) {
                throw new DataNotFoundException("USER DOES NOT HAVE PERMISSIONS ");
        }
//        if(!optionalUser.get().isAtive()) {
//            throw new DataNotFoundException("USER_IS_LOCKED");
//        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                email, password,
                existingUser.getAuthorities()
        );

        //authenticate with Java Spring security
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }
    public User createUser(UserDTO userDTO) throws Exception {

        if (userRepository.existsByEmail(userDTO.getEmail())){
            throw new DataIntegrityViolationException("Email exists");
        }
        Role role =roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException(""));
        if(role.getNameRole().toUpperCase().equals(Role.ADMIN)) {
            throw new PermissionDenyException("You cannot register an admin account");
        }
        User newUser= User.builder()
                .fullName(userDTO.getFullName())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .phone(userDTO.getPhoneNumber())
                .active(true)
                .build();
        newUser.setRole(role);
        String password = userDTO.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        newUser.setPassword(encodedPassword);
        // Kiểm tra nếu có accountId, không yêu cầu password
        return userRepository.save(newUser);
    }
    public User getUserDetailsFromToken(String token) throws Exception {
        if(jwtTokenUtil.isTokenExpired(token)) {
            throw new ExpiredTokenException("Token is expired");
        }
        String subject = jwtTokenUtil.getSubject(token);
        Optional<User> user;
        user = userRepository.findByEmail(subject);
        return user.orElseThrow(() -> new Exception("User not found"));
    }
    public User updateUser(Long userId, UpdateUserDTO updatedUserDTO) throws Exception {
        // Find the existing user by userId
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        // Check if the phone number is being changed and if it already exists for another user

        String newPhoneNumber = updatedUserDTO.getPhoneNumber();
        if (!existingUser.getPhone().equals(newPhoneNumber) &&
                userRepository.existsByPhone(newPhoneNumber)) {
            throw new DataIntegrityViolationException("Phone number already exists");
        }

        // Update user information based on the DTO
        if (updatedUserDTO.getFullName() != null) {
            existingUser.setFullName(updatedUserDTO.getFullName());
        }

        if (newPhoneNumber != null) {
            existingUser.setPhone(newPhoneNumber);
        }
        // Update the password if it is provided in the DTO
        if (updatedUserDTO.getPassword() != null
                && !updatedUserDTO.getPassword().isEmpty()) {
            if(!updatedUserDTO.getPassword().equals(updatedUserDTO.getRetypePassword())) {
                throw new DataNotFoundException("Password and retype password not the same");
            }
            String newPassword = updatedUserDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(newPassword);
            existingUser.setPassword(encodedPassword);
        }
        //existingUser.setRole(updatedRole);
        // Save the updated user
        return userRepository.save(existingUser);
    }
    public User updateUserByAdmin(Long userId, UpdateUserDTOByAdmin updatedUserDTO) throws Exception {
        // Find the existing user by userId
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        // Check if the phone number is being changed and if it already exists for another user

        String newPhoneNumber = updatedUserDTO.getPhoneNumber();
        if (!existingUser.getPhone().equals(newPhoneNumber) &&
                userRepository.existsByPhone(newPhoneNumber)) {
            throw new DataIntegrityViolationException("Phone number already exists");
        }

        // Update user information based on the DTO
        if (updatedUserDTO.getFullName() != null) {
            existingUser.setFullName(updatedUserDTO.getFullName());
        }

        if (newPhoneNumber != null) {
            existingUser.setPhone(newPhoneNumber);
        }
        if (updatedUserDTO.getActive() != null) {
            existingUser.setActive(updatedUserDTO.getActive());
        }
        // Update the password if it is provided in the DTO
        if (updatedUserDTO.getPassword() != null
                && !updatedUserDTO.getPassword().isEmpty()) {
            if(!updatedUserDTO.getPassword().equals(updatedUserDTO.getRetypePassword())) {
                throw new DataNotFoundException("Password and retype password not the same");
            }
            String newPassword = updatedUserDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(newPassword);
            existingUser.setPassword(encodedPassword);
        }
        if (updatedUserDTO.getRoleId() != null) {
            Optional<Role> updatedRole =roleRepository.findById(updatedUserDTO.getRoleId());
            existingUser.setRole(updatedRole.get());
        }
        // Save the updated user
        return userRepository.save(existingUser);
    }

}