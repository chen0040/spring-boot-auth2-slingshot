package com.github.chen0040.bootslingshot.configs;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;


/**
 * Created by xschen on 16/10/2016.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {



   @Override
   public void addViewControllers(ViewControllerRegistry registry) {
      registry.addViewController("/home").setViewName("home");
      registry.addViewController("/").setViewName("home");

      registry.addViewController("/app").setViewName("app");
      registry.addViewController("/login").setViewName("login");
      registry.addViewController("/403").setViewName("403");

      registry.addViewController("/html/admin/user-detail").setViewName("html/admin/user-detail");
      registry.addViewController("/html/admin/user-form").setViewName("html/admin/user-form");
      registry.addViewController("/html/admin/user-management").setViewName("html/admin/user-management");


      registry.addViewController("/html/commons/account").setViewName("html/commons/account");

   }

   @Bean
   public LocaleResolver localeResolver() {
      CookieLocaleResolver localeResolver = new CookieLocaleResolver();
      localeResolver.setDefaultLocale(new Locale("en"));


      return localeResolver;
   }

   @Bean
   public LocaleChangeInterceptor localeChangeInterceptor() {
      LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
      lci.setParamName("lang");

      return lci;
   }


   @Override
   public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(localeChangeInterceptor());
   }

   @Bean
   public ReloadableResourceBundleMessageSource messageSource() {
      ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
      messageSource.setBasename("classpath:i18n/messages");
      messageSource.setCacheSeconds(10); //reload messages every 10 seconds
      return messageSource;
   }
}
