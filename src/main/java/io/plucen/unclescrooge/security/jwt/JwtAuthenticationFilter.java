package io.plucen.unclescrooge.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    try {
      final UsernameAuthRequest usernameAuthRequest =
          new ObjectMapper().readValue(request.getInputStream(), UsernameAuthRequest.class);
      return authenticationManager.authenticate(usernameAuthRequest.getAuthenticationToken());
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult)
      throws IOException, ServletException {

    final SecretKey secretKey =
        Keys.hmacShaKeyFor("secretsecretsecretsecretsecretsecret".getBytes());

    final String token =
        Jwts.builder()
            .setSubject(authResult.getName())
            .claim("authorities", authResult.getAuthorities())
            .setIssuedAt(Date.valueOf(LocalDate.now()))
            .setExpiration(Date.valueOf(LocalDate.now().plusWeeks(2)))
            .signWith(secretKey)
            .compact();
    response.addHeader("Authorization", "Bearer " + token);
  }

  @Data
  public static class UsernameAuthRequest {
    private String username;
    private String password;

    public UsernamePasswordAuthenticationToken getAuthenticationToken() {
      return new UsernamePasswordAuthenticationToken(username, password);
    }
  }
}
