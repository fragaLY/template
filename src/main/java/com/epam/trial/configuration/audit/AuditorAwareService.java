package com.epam.trial.configuration.audit;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareService implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    var authentication =
        SecurityContextHolder
            .getContext()
            .getAuthentication();
    if (authentication == null ||
        !authentication.isAuthenticated() ||
        authentication instanceof AnonymousAuthenticationToken
    ) {
      return Optional.empty();
    }

    return Optional.ofNullable(authentication.getPrincipal().toString());
  }
}
