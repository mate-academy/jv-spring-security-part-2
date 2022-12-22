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
    private static final String ADMIN_ROLE = "ADMIN";
    private static final String USER_ROLE = "USER";
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
        http.authorizeRequests()
            .antMatchers(HttpMethod.POST, "/orders/**").hasAnyRole(ADMIN_ROLE, USER_ROLE)
            .antMatchers("/users/**").hasRole(ADMIN_ROLE)
            .antMatchers("/shopping-carts/**").hasAnyRole(ADMIN_ROLE, USER_ROLE)
            .antMatchers(HttpMethod.GET).hasAnyRole(ADMIN_ROLE, USER_ROLE)
            .antMatchers("/register").permitAll()
            .antMatchers("/**").hasRole(ADMIN_ROLE)
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
