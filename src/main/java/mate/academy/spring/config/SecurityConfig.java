package mate.academy.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_USER = "USER";
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/register").permitAll()
                .antMatchers(HttpMethod.POST, "/cinema-halls").hasAuthority(ROLE_ADMIN)
                .antMatchers(HttpMethod.GET, "/cinema-halls").permitAll()
                .antMatchers(HttpMethod.POST, "/movies").hasAuthority(ROLE_ADMIN)
                .antMatchers(HttpMethod.GET, "/movies").permitAll()
                .antMatchers(HttpMethod.GET, "/movie-sessions").permitAll()
                .antMatchers(HttpMethod.POST, "/movie-sessions").hasAuthority(ROLE_ADMIN)
                .antMatchers(HttpMethod.PUT, "/movie-sessions").hasAuthority(ROLE_ADMIN)
                .antMatchers(HttpMethod.DELETE, "/movie-sessions").hasAuthority(ROLE_ADMIN)
                .antMatchers("/orders/*").hasAuthority(ROLE_USER)
                .antMatchers("/shopping-cart/*").hasAuthority(ROLE_USER)
                .antMatchers("/by-email").hasAuthority(ROLE_ADMIN)
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
