package mate.academy.spring.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String ADMIN = "ADMIN";
    private static final String USER = "USER";
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder encoder;

    public SecurityConfig(UserDetailsService userDetailsService, PasswordEncoder encoder) {
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/register").anonymous()
                .antMatchers(HttpMethod.GET, "/cinema-halls/**").hasAnyRole(ADMIN,USER)
                .antMatchers(HttpMethod.POST, "/cinema-halls/**").hasRole(ADMIN)
                .antMatchers(HttpMethod.GET, "/movies").hasAnyRole(ADMIN, USER)
                .antMatchers(HttpMethod.POST, "/movies/**").hasRole(ADMIN)
                .antMatchers(HttpMethod.GET, "/movie-sessions/**").hasAnyRole(ADMIN,USER)
                .antMatchers(HttpMethod.POST, "/movie-sessions/**").hasRole(ADMIN)
                .antMatchers(HttpMethod.PUT, "/movie-sessions/**").hasRole(ADMIN)
                .antMatchers(HttpMethod.DELETE, "/movie-sessions/**").hasRole(ADMIN)
                .antMatchers(HttpMethod.POST, "/orders/**").hasRole(USER)
                .antMatchers(HttpMethod.GET, "/orders/**").hasRole(USER)
                .antMatchers(HttpMethod.GET, "/shopping-carts/**").hasRole(USER)
                .antMatchers(HttpMethod.PUT, "/shopping-carts/**").hasRole(USER)
                .antMatchers(HttpMethod.GET, "/users/**").hasRole(ADMIN)
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .permitAll()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }
}
