package course.courseWork.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import course.courseWork.model.UserAuthority;

@Slf4j
@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(expressionInterceptUrlRegistry ->
                        expressionInterceptUrlRegistry
                                .requestMatchers("/registration", "/login").permitAll() // Доступно всем
                                .requestMatchers(HttpMethod.POST, "/task/**", "/category/**", "/tag/**").authenticated()
                                .requestMatchers(HttpMethod.GET, "/task/**", "/category/**", "/tag/**").authenticated()
                                .requestMatchers(HttpMethod.PUT, "/task/**", "/category/**", "/tag/**").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/task/**", "/category/**", "/tag/**").authenticated()
                                .requestMatchers(HttpMethod.GET, "/admin/**").hasAuthority(UserAuthority.ROLE_ADMIN.getAuthority())
                                .requestMatchers(HttpMethod.DELETE, "/admin/**").hasAuthority(UserAuthority.ROLE_ADMIN.getAuthority())
                     
                )
                .formLogin(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
