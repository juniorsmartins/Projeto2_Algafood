package io.algafoodapi.business.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends SecurityConfigurerAdapter {

  @Bean
  private SecurityFilterChain configure(HttpSecurity http) throws Exception {
    http.httpBasic()
      .and()
      .authorizeRequests()
      .anyRequest()
      .authenticated();

    return http.build();
  }
}

