package io.plucen.unclescrooge.security;

import io.plucen.unclescrooge.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  public static final int B_CRYPT_STRENGTH = 10;
  public static final BCryptPasswordEncoder B_CRYPT_PASSWORD_ENCODER =
      new BCryptPasswordEncoder(B_CRYPT_STRENGTH);

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return B_CRYPT_PASSWORD_ENCODER;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilter(new JwtAuthenticationFilter())
        .authorizeRequests()
        .anyRequest()
        .authenticated();
  }

  @Bean
  @Override
  protected UserDetailsService userDetailsService() {
    return new InMemoryUserDetailsManager(
        User.builder()
            .username("daniel")
            .password(getPasswordEncoder().encode("b"))
            .roles("ADMIN")
            .build(),
        User.builder()
            .username("priscilla")
            .password(getPasswordEncoder().encode("b"))
            .roles("ADMIN")
            .build());
  }
}
