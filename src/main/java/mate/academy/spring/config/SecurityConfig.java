package mate.academy.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .passwordEncoder(getEncoder())
                .withUser("1@123").password(getEncoder().encode("123")).roles("USER");
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/register").anonymous()
                .antMatchers(HttpMethod.GET, "/cinema-halls",
                        "/movies", "/movie-sessions/available")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/users/by-email")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/cinema-halls",
                        "/movies", "/movie-sessions")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/movie-sessions/**")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/movie-sessions/**")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/orders", "/sopping-carts/by-user")
                .hasRole("USER")
                .antMatchers(HttpMethod.POST, "/orders/complete")
                .hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/shopping-carts/movie-sessions")
                .hasRole("USER")
                .and()
                .formLogin()
                .permitAll()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}
