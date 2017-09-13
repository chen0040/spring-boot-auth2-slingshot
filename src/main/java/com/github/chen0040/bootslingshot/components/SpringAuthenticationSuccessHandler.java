package com.github.chen0040.bootslingshot.components;


import com.github.chen0040.bootslingshot.models.SpringUserDetails;
import com.google.common.util.concurrent.*;
import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.Executors;


/**
 * Created by xschen on 21/12/2016.
 */
@Component
public class SpringAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

   private static final Logger logger = LoggerFactory.getLogger(SpringAuthenticationSuccessHandler.class);

   private ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

   public static UserAgentStringParser parser = UADetectorServiceFactory.getResourceModuleParser();

   public SpringAuthenticationSuccessHandler(){
      super();
   }

   @Autowired
   private SpringRequestHelper springRequestHelper;


   private String getUserAgent(HttpServletRequest request) {
      return request.getHeader("user-agent");
   }

   @Override
   public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
           throws IOException, ServletException {
      final String userAgent = getUserAgent(request);
      ListenableFuture<String> future = service.submit(() -> {
         ReadableUserAgent ua =  parser.parse(userAgent);
         String key = "NA";
         if(ua != null) {
            String browser = ua.getName();
            logger.info("login user agent is {}", browser);

            SpringUserDetails user = (SpringUserDetails) authentication.getPrincipal();

            logger.info("login user id: {}", user.getUserId());
         }

         return key;
      });

      Futures.addCallback(future, new FutureCallback<String>() {
         @Override public void onSuccess(String s) {
            if(s != null && !s.equals("NA")) {
               logger.info("successfully log metric: {}", s);
            }
         }


         @Override public void onFailure(Throwable throwable) {
            logger.error("Failed to log metric", throwable);
         }
      });

      super.onAuthenticationSuccess(request, response, authentication);
   }



}
