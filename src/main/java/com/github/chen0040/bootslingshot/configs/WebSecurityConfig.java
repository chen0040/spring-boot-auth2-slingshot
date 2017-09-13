package com.github.chen0040.bootslingshot.configs;

import com.github.chen0040.bootslingshot.components.SpringAuthenticationSuccessHandler;
import com.github.chen0040.bootslingshot.services.SpringUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 15/10/2016.
 */

@Configuration
@EnableOAuth2Client
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

   @Autowired
   private SpringUserDetailService userDetailService;

   @Autowired
   private OAuth2ClientContext oauth2ClientContext;


   @Autowired
   private SpringAuthenticationSuccessHandler authenticationSuccessHandler;


   @Override
   protected void configure(HttpSecurity http) throws Exception {




      http
              .authorizeRequests()
              .antMatchers("/", "/login**", "/webjars/**").permitAll()
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
              .and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
              .csrf()
              .disable();
              //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
   }

   @Autowired
   public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {


      auth.userDetailsService(userDetailService)
              .passwordEncoder(new BCryptPasswordEncoder());
   }

   private Filter ssoFilter() {

      CompositeFilter filter = new CompositeFilter();
      List<Filter> filters = new ArrayList<>();


      FacebookOAuth2ClientAuthenticationProcessingAndSavingFilter facebookFilter = new FacebookOAuth2ClientAuthenticationProcessingAndSavingFilter("/login/facebook");
      OAuth2RestTemplate facebookTemplate = new OAuth2RestTemplate(facebook(), oauth2ClientContext);
      facebookFilter.setRestTemplate(facebookTemplate);
      UserInfoTokenServices tokenServices = new UserInfoTokenServices(facebookResource().getUserInfoUri(), facebook().getClientId());
      tokenServices.setRestTemplate(facebookTemplate);
      facebookFilter.setTokenServices(tokenServices);
      filters.add(facebookFilter);

      GithubOAuth2ClientAuthenticationProcessingAndSavingFilter githubFilter = new GithubOAuth2ClientAuthenticationProcessingAndSavingFilter("/login/github");
      OAuth2RestTemplate githubTemplate = new OAuth2RestTemplate(github(), oauth2ClientContext);
      githubFilter.setRestTemplate(githubTemplate);
      tokenServices = new UserInfoTokenServices(githubResource().getUserInfoUri(), github().getClientId());
      tokenServices.setRestTemplate(githubTemplate);
      githubFilter.setTokenServices(tokenServices);
      filters.add(githubFilter);

      filter.setFilters(filters);
      return filter;
   }

   @Bean
   @ConfigurationProperties("facebook.client")
   public AuthorizationCodeResourceDetails facebook() {
      return new AuthorizationCodeResourceDetails();
   }

   @Bean
   @ConfigurationProperties("facebook.resource")
   public ResourceServerProperties facebookResource() {
      return new ResourceServerProperties();
   }

   @Bean
   @ConfigurationProperties("github.client")
   public AuthorizationCodeResourceDetails github() {
      return new AuthorizationCodeResourceDetails();
   }

   @Bean
   @ConfigurationProperties("github.resource")
   public ResourceServerProperties githubResource() {
      return new ResourceServerProperties();
   }

   @Bean
   public FilterRegistrationBean oauth2ClientFilterRegistration(
           OAuth2ClientContextFilter filter) {
      FilterRegistrationBean registration = new FilterRegistrationBean();
      registration.setFilter(filter);
      registration.setOrder(-100);
      return registration;
   }
}
