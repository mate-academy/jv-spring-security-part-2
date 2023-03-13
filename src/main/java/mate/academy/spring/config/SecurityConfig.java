package mate.academy.spring.config;

import static mate.academy.spring.model.Role.RoleName.ADMIN;
import static mate.academy.spring.model.Role.RoleName.USER;

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
    private static final String ADMIN_ROLE_NAME = ADMIN.name();
    private static final String USER_ROLE_NAME = USER.name();
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
                .antMatchers(HttpMethod.GET,"/register").permitAll()
                .antMatchers(HttpMethod.GET, "/cinema-halls/**")
                .hasAnyRole(ADMIN_ROLE_NAME,USER_ROLE_NAME)
                .antMatchers(HttpMethod.POST, "/cinema-halls/**").hasRole(ADMIN_ROLE_NAME)
                .antMatchers(HttpMethod.GET, "/movies").hasAnyRole(ADMIN_ROLE_NAME,USER_ROLE_NAME)
                .antMatchers(HttpMethod.POST, "/movies").hasRole(ADMIN_ROLE_NAME)
                .antMatchers(HttpMethod.GET, "/movie-sessions/**").hasRole(ADMIN_ROLE_NAME)
                .antMatchers(HttpMethod.POST, "/movie-sessions/**").hasRole(ADMIN_ROLE_NAME)
                .antMatchers(HttpMethod.PUT, "/movie-sessions/**").hasRole(ADMIN_ROLE_NAME)
                .antMatchers(HttpMethod.DELETE, "/movie-sessions/**").hasRole(ADMIN_ROLE_NAME)
                .antMatchers(HttpMethod.GET,"/users/by-email").hasRole(ADMIN_ROLE_NAME)
                .antMatchers(HttpMethod.GET,"/shopping-carts/**").hasRole(USER_ROLE_NAME)
                .antMatchers(HttpMethod.GET, "/orders/**")
                .hasAnyRole(ADMIN_ROLE_NAME,USER_ROLE_NAME)
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
