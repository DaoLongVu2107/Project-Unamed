package com.itboy.DACNPM.reponses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itboy.DACNPM.Enity.Role;
import com.itboy.DACNPM.Enity.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("fullname")
    private String fullName;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("email")
    private String email;

    @JsonProperty("role")
    private Role role;
    public static UserResponse fromUser(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhone())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
