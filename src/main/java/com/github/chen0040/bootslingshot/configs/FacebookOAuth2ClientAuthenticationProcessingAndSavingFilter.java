package com.github.chen0040.bootslingshot.configs;


import com.github.chen0040.bootslingshot.components.SpringAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.web.client.RestTemplate;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by xschen on 13/9/2017.
 */
class FacebookOAuth2ClientAuthenticationProcessingAndSavingFilter extends OAuth2ClientAuthenticationProcessingFilter {

   @Autowired
   private SpringAuthentication authentication;

   @Autowired
   private RestTemplate restTemplate;

   public FacebookOAuth2ClientAuthenticationProcessingAndSavingFilter(String defaultFilterProcessesUrl) {
      super(defaultFilterProcessesUrl);
   }

   @Override
   protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
           FilterChain chain, Authentication authResult) throws IOException, ServletException {
      super.successfulAuthentication(request, response, chain, authResult);



      SecurityContext context =  SecurityContextHolder.getContext();
   }
}
