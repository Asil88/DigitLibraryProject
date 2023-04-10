package com.digitlibraryproject.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    CustomerUserDetailsService customerUserDetailsService;

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**"
    };

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers(AUTH_WHITELIST);
    }

    @Autowired
    public SecurityConfiguration(CustomerUserDetailsService customerUserDetailsService) {
        this.customerUserDetailsService = customerUserDetailsService;
    }

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customerUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/registration", "/swagger-ui/index.html").permitAll()

                .antMatchers("/registration/**").hasRole("ADMIN")

                .antMatchers(HttpMethod.DELETE,("/user/**")).hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT,("/user/**")).hasAnyRole("USER","ADMIN","SUBSCRIBER")
                .antMatchers(HttpMethod.GET,("/user/**")).hasAnyRole("USER","ADMIN","SUBSCRIBER")

                .antMatchers(HttpMethod.DELETE,("/order/**")).hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.POST,("/order/**")).hasAnyRole("USER","ADMIN","SUBSCRIBER")
                .antMatchers(HttpMethod.PUT,("/order/**")).hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET,("/order/**")).hasAnyRole("USER","ADMIN","SUBSCRIBER")

                .antMatchers(HttpMethod.DELETE,("/author/**")).hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.POST,("/author/**")).hasAnyRole("ADMIN","SUBSCRIBER","USER")
                .antMatchers(HttpMethod.PUT,("/author/**")).hasAnyRole("ADMIN","SUBSCRIBER","USER")
                .antMatchers(HttpMethod.GET,("/author/**")).hasAnyRole("USER","ADMIN","SUBSCRIBER")

                .antMatchers(HttpMethod.DELETE,("/article/**")).hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.POST,("/article/**")).hasAnyRole("USER","ADMIN","SUBSCRIBER")
                .antMatchers(HttpMethod.PUT,("/article/**")).hasAnyRole("USER","ADMIN","SUBSCRIBER")
                .antMatchers(HttpMethod.GET,("/article/**")).hasAnyRole("USER","ADMIN","SUBSCRIBER")

                .antMatchers(HttpMethod.GET,("/book/{id}")).hasAnyRole("USER","ADMIN","SUBSCRIBER")
                .antMatchers(HttpMethod.GET,("/book/findAll")).hasAnyRole("USER","ADMIN","SUBSCRIBER")
                .antMatchers(HttpMethod.GET,("/book/download")).hasAnyRole("ADMIN","SUBSCRIBER")
                .antMatchers(HttpMethod.POST,("/book/**")).hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT,("/book/**")).hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,("/book/**")).hasAnyRole("ADMIN")

                .antMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and().build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}




