package com.food.cpg.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

import com.food.cpg.handlers.AccessDeniedExceptionHandler;
import com.food.cpg.handlers.AuthenticationByRoleSuccessHandler;

@Configuration
@EnableWebSecurity
public class AuthenticationConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    AuthenticationByRoleSuccessHandler authenticationByRoleSuccessHandler;

    @Autowired
    AccessDeniedExceptionHandler accessDeniedExceptionHandler;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery("select email, password, '1' as status from users where status = 'Approved' and email = ?")
                .authoritiesByUsernameQuery("select email, role from users where status = 'Approved' and email = ?")
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/css/**", "/image/**", "/js/**", "/webjars/**", "/favicon.ico", "/*-error")
                .permitAll()
                .antMatchers("/admin*", "/approve-manufacturer/*", "/block-manufacturer/*", "/manufacturer-details/*")
                .hasAnyAuthority("ADMIN")
                .anyRequest()
                .hasAnyAuthority("MANUFACTURER")
                .and()
                .formLogin().loginPage("/login").successHandler(authenticationByRoleSuccessHandler).permitAll()
                .and().exceptionHandling().accessDeniedHandler(accessDeniedExceptionHandler)
                .and()
                .logout().permitAll();
    }
}