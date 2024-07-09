package com.itboy.DACNPM.reponses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itboy.DACNPM.Enity.User;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponse {
    @JsonProperty("message")
    private String message;

    @JsonProperty("user")
    private User user;
}
