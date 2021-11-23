package mate.academy.spring.config;

import mate.academy.spring.security.CustomUserDetailsService;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService,
                          PasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/register", "/login")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/cinema-halls/**", "/movies/**", "/movie-sessions/**")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/users/**")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,
                        "/cinema-halls/**", "/movies/**", "/movie-sessions/**")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/movie-sessions/**")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/movie-sessions/**")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/admin")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/orders/**", "/shopping-carts/**")
                .hasRole("USER")
                .antMatchers(HttpMethod.POST, "/orders/**")
                .hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/shopping-carts/**")
                .hasRole("USER")
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
