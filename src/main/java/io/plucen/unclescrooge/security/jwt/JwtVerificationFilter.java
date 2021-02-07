package io.plucen.unclescrooge.security.jwt;

import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.io.IOException;
import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtVerificationFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    final String authorization = request.getHeader("Authorization");
    if (Strings.isNullOrEmpty(authorization) || !authorization.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    try {

      final SecretKey secretKey =
          Keys.hmacShaKeyFor("secretsecretsecretsecretsecretsecret".getBytes());

      final String token = authorization.replace("Bearer ", "");
      final Claims body =
          Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
      final String username = body.getSubject();
      SecurityContextHolder.getContext()
          .setAuthentication(new UsernamePasswordAuthenticationToken(username, null, null));
    } catch (JwtException exception) {
      throw new RuntimeException();
    }

    filterChain.doFilter(request, response);
  }
}
