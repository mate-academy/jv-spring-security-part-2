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
                .antMatchers(HttpMethod.GET,"/cinema-halls**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST,"/cinema-halls**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/movies**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/movies**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/movie-session/available**")
                .hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/movie-session**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/movie-session/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/movie-session/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/orders/complete**").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/shopping-carts/movie-sessiion**").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/shopping-carts/by-user").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "users/by-email").hasRole("ADMIN")
                .antMatchers("/user").hasRole("USER")
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
