package com.epam.trial.configuration.security;

import static com.epam.trial.user.domain.Role.ADMINISTRATOR;
import static com.epam.trial.user.domain.Role.OFFICIAL;
import static com.epam.trial.user.domain.Role.SYSTEM;
import static com.epam.trial.user.domain.Role.USER;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  private static final String[] AUTH_WHITELIST = {
      "/v2/api-docs",
      "/v3/api-docs",
      "/v3/api-docs/**",
      "/swagger-resources",
      "/swagger-resources/**",
      "/configuration/ui",
      "/configuration/security",
      "/webjars/**",
      "/swagger-ui.html",
      "/actuator/**",
      "/v3/**",
      "/api/v1/login",
      "/swagger-ui/**",
      "/error"
  };

  @Bean
  public static PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationProvider authenticationProvider(CustomUserDetailsService service) {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(service);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }


  @Bean
  public SecurityFilterChain filterSecurity(HttpSecurity http,
      AuthenticationProvider authenticationProvider,
      JwtAuthenticationFilter authenticationFilter,
      LogoutHandler logoutHandler) throws Exception {

    return http
        .authorizeHttpRequests(
            authorize -> authorize.requestMatchers(AUTH_WHITELIST)
                .permitAll()
                .requestMatchers(PUT, "/api/v1/notices/**")
                .hasAnyAuthority(ADMINISTRATOR.name(), OFFICIAL.name(), SYSTEM.name())
                .requestMatchers(POST, "/api/v1/notices").hasAnyAuthority(USER.name())
                .anyRequest()
                .authenticated())
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .logout(logout ->
            logout.logoutUrl("/api/v1/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler(
                    (request, response, authentication) -> SecurityContextHolder.clearContext())
        )
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .csrf(AbstractHttpConfigurer::disable)
        .build();
  }

}
