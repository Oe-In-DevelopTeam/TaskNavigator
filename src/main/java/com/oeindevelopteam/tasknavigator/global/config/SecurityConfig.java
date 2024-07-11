package com.oeindevelopteam.tasknavigator.global.config;

import com.oeindevelopteam.tasknavigator.domain.user.repository.UserRepository;
import com.oeindevelopteam.tasknavigator.domain.user.security.UserDetailsServiceImpl;
import com.oeindevelopteam.tasknavigator.global.dto.SecurityResponse;
import com.oeindevelopteam.tasknavigator.global.jwt.JwtProvider;
import com.oeindevelopteam.tasknavigator.global.security.CustomAuthenticationEntryPoint;
import com.oeindevelopteam.tasknavigator.global.security.JwtAuthenticationFilter;
import com.oeindevelopteam.tasknavigator.global.security.JwtAuthorizationFilter;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

  private final JwtProvider jwtProvider;
  private final UserDetailsServiceImpl userDetailsService;
  private final AuthenticationConfiguration authenticationConfiguration;
  private final UserRepository userRepository;
  private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
  private final SecurityResponse securityResponse;

  public SecurityConfig(JwtProvider jwtProvider, UserDetailsServiceImpl userDetailsService,
      AuthenticationConfiguration authenticationConfiguration, UserRepository userRepository,
      CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
      SecurityResponse securityResponse) {
    this.jwtProvider = jwtProvider;
    this.userDetailsService = userDetailsService;
    this.authenticationConfiguration = authenticationConfiguration;
    this.userRepository = userRepository;
    this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    this.securityResponse = securityResponse;
  }


  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
      throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {

    JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtProvider, userRepository,
        securityResponse);
    filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));

    return filter;
  }

  @Bean
  public JwtAuthorizationFilter jwtAuthorizationFilter() {
    return new JwtAuthorizationFilter(jwtProvider, userDetailsService, securityResponse);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.csrf((csrf) -> csrf.disable());

    http.sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(
            SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
//            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//            .requestMatchers("/api/users/signup", "/api/users/token/refresh").permitAll()
//            .requestMatchers(HttpMethod.GET, "/api/**").authenticated()
//            .requestMatchers("/api/admin/**").hasAuthority("MANAGER")
//            .anyRequest().authenticated())
            .anyRequest().permitAll()) // 모든 요청에 접근 허용, 개발 완료 후 수정 예정
        .exceptionHandling((exceptionHandling) -> {
          exceptionHandling.authenticationEntryPoint(customAuthenticationEntryPoint);
        })
        .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        .addFilterAfter(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);

    return http.build();
  }

}
