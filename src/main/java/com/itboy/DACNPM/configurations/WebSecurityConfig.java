package com.itboy.DACNPM.configurations;


import com.itboy.DACNPM.Enity.Role;
import com.itboy.DACNPM.Filter.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.http.HttpMethod.*;

@Configuration
//@EnableMethodSecurity
@EnableWebSecurity
@EnableWebMvc
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;
    @Value("${api.prefix}")
    private String apiPrefix;
    @Bean
    //Pair.of(String.format("%s/..", apiPrefix), "GET"),
    public SecurityFilterChain securityFilterChain(HttpSecurity http)  throws Exception{
        http

                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {
                    requests
                            .requestMatchers(
                                    //bo qua authen
                                    String.format("%s/users/register", apiPrefix),
                                    String.format("%s/users/roles", apiPrefix),
                                    String.format("%s/users/login", apiPrefix),
                                    String.format("%s/users/login", apiPrefix),
                                    String.format("%s/doc/getAll", apiPrefix)
                            ).permitAll()
                            .requestMatchers(HttpMethod.GET, apiPrefix + "/doc/**").permitAll() // Cho phép GET tới /api/doc/*
                            .requestMatchers(GET,
                                    String.format("%s/doc/file/**", apiPrefix)).permitAll()
                            .requestMatchers(GET,
                                    String.format("%s/doc/view/**", apiPrefix)).permitAll()
                            .anyRequest().authenticated();
                            //.anyRequest().permitAll();

                })
                .csrf(AbstractHttpConfigurer::disable);
//        http.cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
//            @Override
//            public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
//                CorsConfiguration configuration = new CorsConfiguration();
//                configuration.setAllowedOrigins(List.of("*"));
//                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
//                configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
//                configuration.setExposedHeaders(List.of("x-auth-token"));
//                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//                source.registerCorsConfiguration("/**", configuration);
//                httpSecurityCorsConfigurer.configurationSource(source);
//            }
//        });

        return http.build();
    }
}
