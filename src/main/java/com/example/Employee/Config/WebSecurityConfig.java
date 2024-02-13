package com.example.Employee.Config;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;



@Configuration
@EnableWebSecurity

public class WebSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.withUsername("user")
                .password(passwordEncoder.encode("user123"))
                .roles("USER")
                .build();
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeRequests()
                .requestMatchers(HttpMethod.GET,"/employees/hello").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/employees/hello1").permitAll()
                .requestMatchers(HttpMethod.GET,"/employees/{id}").permitAll()
                .requestMatchers(HttpMethod.POST,"/employees/create").permitAll()
                .requestMatchers(HttpMethod.DELETE,"/employees/deleteall").permitAll()
               .requestMatchers(HttpMethod.DELETE,"/employees/{id}").permitAll()
               .anyRequest().authenticated()
               .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .and().oauth2Login(Customizer.withDefaults())
               .formLogin(Customizer.withDefaults())
                .build();

    }
@Bean
public AccessDeniedHandler accessDeniedHandler() {
    return new CustomAccessDeniedHandler();
}
}