package com.practice.configuration;

import com.juric.carbon.api.user.UserPasswordService;
import com.juric.carbon.api.user.UserService;
import com.practice.authentication.UserAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Eric on 10/18/2015.
 */
@Configuration
@Import(WCMConfiguration.class)
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource(name = "userPasswordService")
    private UserPasswordService userPasswordService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .and()
                .logout()
                    .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(userAuthenticationProvider());
    }

    @Bean
    UserAuthenticationProvider userAuthenticationProvider() {
        UserAuthenticationProvider userAuthenticationProvider = new UserAuthenticationProvider();
        userAuthenticationProvider.setUserPasswordService(userPasswordService);
        return userAuthenticationProvider ;
    }
}
