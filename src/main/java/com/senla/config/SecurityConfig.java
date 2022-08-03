package com.senla.config;

import com.senla.security.basic.TokenAuthFilter;
import com.senla.security.basic.TokenAuthProvider;
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

@RequiredArgsConstructor
@EnableWebSecurity
@ComponentScan("com.senla")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenAuthProvider tokenAuthProvider;
    private final TokenAuthFilter tokenAuthFilter;
    private final JwtAuthProvider jwtAuthProvider;
    private final JwtAuthTokenFilter jwtAuthTokenFilter;

    /* Базовый вариант при котором токен - RandomStringUtils.random(10, true, true), не JWT; токен хранится в БД
    *
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
    * При отправке запроса в его заголовке должен содержаться токен Authorization : token
    * запрос попадает в TokenAuthFilter
    * там создается объект TokenAuthentication из переданного токена
    * И этот Authentication устанавливается в контекст SpringSecurity
    * затем из нашего фильтра запрос передается дальше в FilterChain
    *
    * Далее в TokenAuthProvider в методе authenticate ловится наш TokenAuthentication
    * из него берется токен и по нему создается UserDetails (из гостя, найденного по логину в методе UserDetailsService)
    * в TokenAuthentication устанавливается этот UserDetails и флажок на успешную аутентификацию
    *
    * И после всего в SecurityConfig конфигурируем http, устанавливая свой провайдер и фильтр.
    * */



    /* JWT вариант - токен генерируется через io.jsonwebtoken.Jwts,
    и хранится интересно где (не в БД, не в рантайме и не в /target. А как тогда он валидируется? Я поискал, и что-то не нашел, а интересно)
    *
    * По принципу работы почти всё то же самое
    * */

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
        /*http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(tokenAuthFilter, BasicAuthenticationFilter.class)
                .authenticationProvider(tokenAuthProvider)
                .authorizeRequests()
                .antMatchers("/guests/**", "/rooms/**", "/maintenances/**").authenticated()
                .antMatchers("/login/**", "/custom_logout/**").permitAll();*/

        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(jwtAuthProvider)
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/guests/**", "/rooms/**", "/maintenances/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/guests/**", "/rooms/**", "/maintenances/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/guests/**", "/rooms/**", "/maintenances/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/guests/**", "/rooms/**", "/maintenances/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/guests/**", "/rooms/**", "/maintenances/**").hasRole("ADMIN")
                .antMatchers("/login/**", "/logout/**", "guests/register/**").permitAll()
                .and()
                .logout().disable();
    }
}
