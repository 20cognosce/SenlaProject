package com.senla.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationProvider authenticationProvider;
    private final TokenAuthFilter tokenAuthFilter;

    /*
    * По эндпоинту /register в GuestController передается GuestCreationDTO
    * из DTO в GuestConverter собирается Guest с хэшированным паролем и ролью USER (если логин != "admin", его надо зарезервировать)
    * Guest сохраняется в таблице guest БД.
    *
    * По эндпоинту /login в LoginController передается LoginDTO (login, password)
    * DTO попадает в LoginService, где по таким credentials ищется Guest
    * Если Guest найден по логину и совпадению хэшей паролей,
    * то для него создается Token, который сохраняется в таблице token БД,
    * а на клиент возвращается значение Токена, которое клиенту надо сохранить.
    *
    * При отправке запроса внутри него должен содержаться токен
    * запрос попадает в TokenAuthFilter
    * там создается объект TokenAuthentication из переданного токена
    * И этот Authentication устанавливается в контекст SpringSecurity
    * и дальше из нашего фильтра запрос передается дальше в FilterChain
    *
    * Далее в TokenAuthProvider в методе authenticate ловится наш TokenAuthentication
    * из него берется токен и по нему создается UserDetails (из гостя, найденного по логину в методе UserDetailsService)
    * в TokenAuthentication устанавливается этот UserDetails и флажок на успешную аутентификацию
    *
    * И после всего в SecurityConfig конфигурируем http, устанавливая своего провайдера и свой фильтр.
    *
    * https://www.youtube.com/watch?v=dAUTSfdGyLU
    * */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(32);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http
                .csrf().disable()
                *//*.httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()*//*
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
                //.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        */
        http
                .csrf().disable()
                .antMatcher("/**")
                .authenticationProvider(authenticationProvider)
                .authorizeRequests()
                .antMatchers("/users/**").hasAuthority("USER")
                .antMatchers("/login").permitAll()
                .and()
                .addFilterBefore(tokenAuthFilter, BasicAuthenticationFilter.class);
    }
}
