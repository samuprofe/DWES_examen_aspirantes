package com.dwes.examen.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize ->
                        authorize.anyRequest().permitAll()
                )
                .formLogin(withDefaults())  // Usa el formulario de login predeterminado
                .logout(withDefaults());   // Usa la funcionalidad de logout predeterminada

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Codificador seguro para las contraseñas
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Definir usuarios en memoria
        UserDetails user = User.withUsername("user")
                .password(passwordEncoder().encode("user")) // Contraseña codificada
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}