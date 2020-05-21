package com.spring.ibm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//Password encoder is an interface which is used through the authorization process. The encode function shall be used to encode
// your password and the matches function will check if your raw password matches the encoded password. ... Let's try to create a password encoder bean.
    @Bean
    public PasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;


    }

    // In this example we do not use Security.
    // Override this method with empty code
    // to disable the default Spring Boot security.

    protected  void configure(HttpSecurity http) throws Exception
    {
        //Empty code because we are not using spring security this is the spring security concept
    }


}
