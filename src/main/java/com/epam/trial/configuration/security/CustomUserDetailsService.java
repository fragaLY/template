package com.epam.trial.configuration.security;

import com.epam.trial.user.repository.UserRepository;
import java.util.Set;
import java.util.UUID;
import javax.naming.AuthenticationException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository repository;

  @SneakyThrows
  @Override
  public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
    var user = repository.findById(UUID.fromString(id)).orElseThrow(
        () -> new AuthenticationException("The user with id [%s] was not found".formatted(id)));
    var authorities = Set.of(new SimpleGrantedAuthority(user.getRole().name()));
    return new org.springframework.security.core.userdetails.User(id, user.getPassword(), true,
        true, true, true, authorities);
  }
}
