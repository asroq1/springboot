package me.hyunjung.config;


import lombok.RequiredArgsConstructor;
import me.hyunjung.service.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {

    private final UserDetailService userService;

    // 특정 경로의 요청에 대해 스프링 시큐리티 비활성화
    // 이전: WebSecurityCustomizer, web.ignoring()
    // 최신: SecurityFilterChain 내에서 requestMatchers(...).permitAll()로 처리하는 것을 권장
    // 하지만 h2-console, static 리소스는 보통 이 방식으로 분리합니다.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // 1. authorizeRequests() -> authorizeHttpRequests() 변경
                // 2. 람다식으로 변경 및 .and() 제거
                .authorizeHttpRequests(auth -> auth
                        // h2-console, static 리소스, 특정 경로에 대한 접근 허용
                        .requestMatchers(toH2Console()).permitAll()
                        .requestMatchers("/static/**", "/login", "/signup", "/user").permitAll()
                        // 그 외 모든 요청은 인증 필요
                        .anyRequest().authenticated()
                )
                // 3. formLogin() -> 람다식으로 변경
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/articles")
                )
                // 4. logout() -> 람다식으로 변경
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                )
                // 5. csrf().disable() -> 람다식으로 변경
                .csrf(AbstractHttpConfigurer::disable) // CSRF 비활성화
                .build();
    }

    // 6. AuthenticationManager 빈은 이제 직접 설정할 필요 없음
    // UserDetailsService와 PasswordEncoder를 빈으로 등록하면 스프링이 자동으로 관리합니다.
    // 따라서 기존의 authenticationManager 빈은 삭제합니다.

    // 패스워드 인코더로 사용할 빈 등록 (기존과 동일)
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}