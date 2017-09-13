package com.github.chen0040.bootslingshot.components;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


/**
 * Created by xschen on 21/12/2016.
 */
@Component
public class SpringRequestHelper {

   private static final List<String> supportedLanguages = Arrays.asList("en", "cn");

   @Autowired
   private LocaleResolver localeResolver;

   public String getUserIpAddress(HttpServletRequest request) {
      String ipAddress = request.getHeader("X-FORWARDED-FOR");
      if (ipAddress == null) {
         ipAddress = request.getRemoteAddr();
      }
      return ipAddress;
   }

   public String getLanguage(HttpServletRequest request) {
      Locale locale = RequestContextUtils.getLocale(request);
      String language = locale.getLanguage();
      if(!supportedLanguages.contains(language)){
         language = "en";
      }

      return language;
   }
}
