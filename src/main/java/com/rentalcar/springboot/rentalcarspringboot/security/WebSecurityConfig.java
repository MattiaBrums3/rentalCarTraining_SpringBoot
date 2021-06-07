package com.rentalcar.springboot.rentalcarspringboot.security;

import com.rentalcar.springboot.rentalcarspringboot.security.entity.UserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;

    private AuthTokenFilter authTokenFilter;

    private AuthEntryPointJwt authEntryPointJwt;

    public WebSecurityConfig(UserDetailsService userDetailsService, AuthTokenFilter authTokenFilter,
                             AuthEntryPointJwt authEntryPointJwt) {
        this.userDetailsService = userDetailsService;
        this.authTokenFilter = authTokenFilter;
        this.authEntryPointJwt = authEntryPointJwt;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    private static final String[] BOTH_URLS = {
            "/users/get/{id}", "/users/post/edit",
            "/vehicles/get/all", "/vehicles/get/{id}",
            "/categories/get/all",
            "/rentals/get-by-user/{id}", "/rentals/post/edit", "rentals/delete/{id}"
    };

    private static final String[] ADMIN_URLS = {
            "/users/get/all", "/users/delete/{id}",
            "/vehicles/post/edit", "/vehicles/delete/{id}", "/vehicles/get-by-category/{id}",
            "/categories/get/{id}", "/categories/post/edit", "/categories/delete/{id}",
            "/rentals/get-by-vehicle/{id}"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/auth/login").permitAll()
                .antMatchers(BOTH_URLS).hasAnyRole("ADMIN", "CUSTOMER")
                .antMatchers(ADMIN_URLS).hasRole("ADMIN")
                .anyRequest().authenticated().and()
                .exceptionHandling().authenticationEntryPoint(authEntryPointJwt).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
