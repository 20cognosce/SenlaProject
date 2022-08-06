package com.senla.config;

import com.senla.security.jwt.JwtAuthProvider;
import com.senla.security.jwt.JwtAuthTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.senla.model.Role.ADMIN;
import static com.senla.model.Role.USER;

@RequiredArgsConstructor
@EnableWebSecurity
@ComponentScan("com.senla")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthProvider jwtAuthProvider;
    private final JwtAuthTokenFilter jwtAuthTokenFilter;

    /* JWT вариант - токен генерируется через io.jsonwebtoken.Jwts
    (в предыдущем коммите токен был сущностью, генерировался с помощью RandomStringUtils и хранился в БД)
    а тут токен хранится интересно где - не в БД, не в рантайме и не в /target. А как тогда он валидируется?
    Я поискал, и что-то не нашел, а интересно
    */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(16);
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(jwtAuthProvider)
                .authorizeRequests()
                .antMatchers("/login/**", "/logout/**", "/guests/register/**").permitAll()
                .antMatchers(HttpMethod.GET, "/guests/**", "/rooms/**", "/maintenances/**").hasAnyAuthority(USER.name(), ADMIN.name())
                .antMatchers(HttpMethod.POST, "/guests/**", "/rooms/**", "/maintenances/**").hasAuthority(ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/guests/**", "/rooms/**", "/maintenances/**").hasAuthority(ADMIN.name())
                .antMatchers(HttpMethod.PATCH, "/guests/**", "/rooms/**", "/maintenances/**").hasAuthority(ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/guests/**", "/rooms/**", "/maintenances/**").hasAuthority(ADMIN.name())
                .and()
                .logout().disable();
    }
}
