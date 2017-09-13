package com.github.chen0040.bootslingshot.configs;

import com.github.chen0040.bootslingshot.components.SpringAuthenticationSuccessHandler;
import com.github.chen0040.bootslingshot.services.SpringUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * Created by xschen on 15/10/2016.
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

   @Autowired
   private SpringUserDetailService userDetailService;


   @Autowired
   private SpringAuthenticationSuccessHandler authenticationSuccessHandler;


   @Override
   protected void configure(HttpSecurity http) throws Exception {




      http
              .authorizeRequests()

              .antMatchers("/js/client/**").hasAnyRole("USER", "ADMIN")
              .antMatchers("/js/admin/**").hasAnyRole("ADMIN")
              .antMatchers("/admin/**").hasAnyRole("ADMIN")
              .antMatchers("/html/**").hasAnyRole("USER", "ADMIN")
              .antMatchers("/js/commons/**").permitAll()
              .antMatchers("/img/**").permitAll()
              .antMatchers("/css/**").permitAll()
              .antMatchers("/jslib/**").permitAll()
              .antMatchers("/webjars/**").permitAll()
              .antMatchers("/V1/rest/**").permitAll()
              .antMatchers("/bundle/**").permitAll()
              .antMatchers("/fonts/*.*").permitAll()
              .antMatchers("/signup").permitAll()
              .antMatchers("/locales").permitAll()
              .antMatchers("/locales/**").permitAll()
              .antMatchers("/privacy-policy").permitAll()
              .antMatchers("/change-locale").permitAll()
              .antMatchers("/link-cache").permitAll()
              .antMatchers("/signup-success").permitAll()
              .antMatchers("/terms-of-use").permitAll()
              .antMatchers("/contact-us").permitAll()
              .antMatchers("/forgot-password").permitAll()
              .antMatchers("/about-us").permitAll()
              .anyRequest().authenticated()
              .and()
              .formLogin()
              .loginPage("/login")
              .defaultSuccessUrl("/home")
              .successHandler(authenticationSuccessHandler)
              .permitAll()
              .and()
              .logout()
              .permitAll()
              .and()
              .csrf()
              .disable();
              //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
   }

   @Autowired
   public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {


      auth.userDetailsService(userDetailService)
              .passwordEncoder(new BCryptPasswordEncoder());
   }
}
