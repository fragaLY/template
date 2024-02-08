package com.epam.trial.configuration.security;

import com.epam.trial.user.domain.Role;
import com.epam.trial.user.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.crypto.SecretKey;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(
      Decoders.BASE64.decode("KZRl3HhgSbBfIeh9h2D2szH7qqXbaskjfnoAIm4PQVm"));
  private static final long ACCESS_TOKEN_VALIDITY = 60 * 60 * 1000;

  private final JwtParser jwtParser;

  private static final String TOKEN_HEADER = "Authorization";
  private static final String CLAIM_ROLES = "roles";


  public JwtUtil() {
    this.jwtParser = Jwts.parser().verifyWith(SECRET_KEY).build();
  }

  public String createToken(User user) {
    var claims = Jwts.claims().subject(user.getId().toString()).add(CLAIM_ROLES, user.getRole())
        .build();
    var tokenCreateTime = new Date();
    var tokenValidity = new Date(
        tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(ACCESS_TOKEN_VALIDITY));
    return Jwts.builder()
        .claims(claims)
        .expiration(tokenValidity)
        .signWith(SECRET_KEY)
        .compact();
  }

  private Claims parseJwtClaims(String token) {
    return jwtParser.parseSignedClaims(token).getPayload();
  }

  public Claims resolveClaims(HttpServletRequest req) {
    try {
      var token = resolveToken(req);
      if (token != null) {
        return parseJwtClaims(token);
      }
      return null;
    } catch (ExpiredJwtException ex) {
      req.setAttribute("expired", ex.getMessage());
      throw ex;
    } catch (Exception ex) {
      req.setAttribute("invalid", ex.getMessage());
      throw ex;
    }
  }

  public String resolveToken(HttpServletRequest request) {
    return request.getHeader(TOKEN_HEADER);
  }

  public boolean validateClaims(Claims claims) throws AuthenticationException {
    return claims.getExpiration().after(new Date());
  }

  public UUID getId(Claims claims) {
    return UUID.fromString(claims.getSubject());
  }

  public Role getClaimRoles(Claims claims) {
    return Arrays.stream(Role.values())
        .filter(it -> it.name().equals(claims.get(CLAIM_ROLES, String.class))).findAny()
        .orElseThrow(() -> new RuntimeException("The role was not found"));
  }


  @SuppressWarnings("unchecked")
  private List<String> getRoles(Claims claims) {
    return (List<String>) claims.get("roles");
  }


}
