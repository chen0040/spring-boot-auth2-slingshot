package com.github.chen0040.bootslingshot.configs;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.chen0040.bootslingshot.components.SpringAuthentication;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.web.client.RestTemplate;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.concurrent.Executors;


/**
 * Created by xschen on 13/9/2017.
 */
class FacebookOAuth2ClientAuthenticationProcessingAndSavingFilter extends OAuth2ClientAuthenticationProcessingFilter {

   private static final Logger logger = LoggerFactory.getLogger(FacebookOAuth2ClientAuthenticationProcessingAndSavingFilter.class);

   @Autowired
   private SpringAuthentication authentication;



   private ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

   public FacebookOAuth2ClientAuthenticationProcessingAndSavingFilter(String defaultFilterProcessesUrl) {
      super(defaultFilterProcessesUrl);
   }

   @Override
   protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
           FilterChain chain, Authentication authResult) throws IOException, ServletException {
      super.successfulAuthentication(request, response, chain, authResult);


      String username = SecurityContextHolder.getContext().getAuthentication().getName();

      //logger.info("facebook email: {}", facebook.userOperations().getUserProfile().getEmail());

      Facebook facebook = new FacebookTemplate(restTemplate.getAccessToken().getValue());
      User profile = facebook.userOperations().getUserProfile();
      logger.info("name: {}", profile.getName());
      logger.info("profile: \n{}", JSON.toJSONString(profile, SerializerFeature.PrettyFormat));


      logger.info("facebook user: {}", username);

      SecurityContext context =  SecurityContextHolder.getContext();
   }
}
