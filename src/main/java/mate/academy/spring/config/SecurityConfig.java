package mate.academy.spring.config;

import mate.academy.spring.model.Roles;
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
                .antMatchers(HttpMethod.POST, "/register").permitAll()
                .antMatchers(HttpMethod.GET, "/cinema-halls/**")
                .hasAnyRole(Roles.ADMIN.name(), Roles.USER.name())
                .antMatchers(HttpMethod.POST, "/cinema-halls").hasRole(Roles.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/movies/**")
                .hasAnyRole(Roles.ADMIN.name(), Roles.USER.name())
                .antMatchers(HttpMethod.POST, "/movies").hasRole(Roles.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/movie-sessions/**")
                .hasAnyRole(Roles.ADMIN.name(), Roles.USER.name())
                .antMatchers(HttpMethod.POST, "/movies-sessions").hasRole(Roles.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/movies-sessions/*").hasRole(Roles.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/movies-sessions/*").hasRole(Roles.ADMIN.name())
                .antMatchers("/orders/**").hasRole(Roles.USER.name())
                .antMatchers("/shopping-carts/**").hasRole(Roles.USER.name())
                .antMatchers(HttpMethod.GET, "/users/by-email*").hasRole(Roles.ADMIN.name())
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
