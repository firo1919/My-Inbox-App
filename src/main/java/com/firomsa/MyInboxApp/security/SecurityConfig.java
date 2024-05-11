package com.firomsa.MyInboxApp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                            .authorizeHttpRequests(auth-> auth
                            .requestMatchers("/").permitAll()
                            .anyRequest().authenticated()
                            )   
                            .logout(l -> l
                            .logoutSuccessUrl("/").permitAll()
                            )
                            .csrf(c -> c
                                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                            .oauth2Login(withDefaults())
                            .formLogin(withDefaults())       
                            .build();
    }
}
