package com.usco.parcial.configuarcion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        RequestMatcher api = request -> request.getRequestURI().startsWith("/api/");

        http
            .csrf(csrf -> csrf.ignoringRequestMatchers(api))
            .exceptionHandling(e -> e
            	    .defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED), api)
            	    .defaultAccessDeniedHandlerFor(
            	        (req, res, ex) -> res.sendError(HttpServletResponse.SC_FORBIDDEN), api)
            	    .accessDeniedHandler((req, res, ex) -> res.sendRedirect("/403")))
            .authorizeHttpRequests(auth -> auth
                .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                .requestMatchers("/login", "/403", "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                .requestMatchers("/admin/**", "/api/admin/**").hasRole("ADMINISTRADOR")
                .requestMatchers("/medico/**", "/api/medico/**").hasRole("MEDICO")
                .requestMatchers("/paciente/**", "/api/paciente/**").hasRole("PACIENTE")
                .anyRequest().authenticated())
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler((req, res, auth) -> {
                    String rol = auth.getAuthorities().iterator().next().getAuthority();
                    res.sendRedirect(switch (rol) {
                        case "ROLE_ADMINISTRADOR" -> "/admin/inicio";
                        case "ROLE_MEDICO" -> "/medico/inicio";
                        default -> "/paciente/inicio";
                    });
                })
                .failureUrl("/login?error")
                .permitAll())
            .logout(logout -> logout.logoutSuccessUrl("/login?logout"));

        return http.build();
    }
}