package com.epam.trial.user.service;

import com.epam.trial.configuration.security.JwtUtil;
import com.epam.trial.user.api.payload.LoginPayload;
import com.epam.trial.user.api.payload.LoginResponse;
import com.epam.trial.user.domain.Role;
import com.epam.trial.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements LogoutHandler {

  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;

  @Transactional(readOnly = true)
  public LoginResponse authenticate(LoginPayload payload) {
    var authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(payload.id(), payload.password()));
    var id = UUID.fromString(authentication.getName());
    var role = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .map(String::toUpperCase)
        .map(Role::valueOf)
        .findFirst()
        .orElseThrow(
            () -> new IllegalArgumentException(
                "Authority not found or not mapped to existing roles"));

    var user = new User(id, role, "");
    var token = jwtUtil.createToken(user);
    return new LoginResponse(id, token);
  }

  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) {
    final String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return;
    }
    SecurityContextHolder.clearContext();
  }
}
