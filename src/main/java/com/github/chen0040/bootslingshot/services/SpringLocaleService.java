package com.github.chen0040.bootslingshot.services;


import com.github.chen0040.bootslingshot.utils.ResourceFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by xschen on 11/12/2016.
 */
@Service
public class SpringLocaleService {

   private static final Logger logger = LoggerFactory.getLogger(SpringLocaleService.class);
   private final Map<String, Map<String, String>> dictionary = new HashMap<>();

   private final Set<String> sources = new HashSet<>();
   private final List<String> locales;

   public List<String> getSources(){
      return sources.stream().collect(Collectors.toList());
   }

   public List<String> getLocales() {
      return locales;
   }

   public SpringLocaleService(){
      locales = Arrays.asList("en", "cn");
      for(String locale : locales) {
         List<String> lines = ResourceFileUtils.lines("i18n/messages_" + locale + ".properties");
         for(String line : lines){

            if(!line.contains("=")) continue;

            int index = line.indexOf('=');
            String source = line.substring(0, index).trim();

            String translated = line.substring(index + 1, line.length()).trim();
            setText(source, locale, translated);
            sources.add(source);
         }

      }
   }

   private void setText(String source, String locale, String translated) {
      Map<String, String> words;
      if(dictionary.containsKey(source)){
         words = dictionary.get(source);
      } else {
         words = new HashMap<>();
         dictionary.put(source, words);
      }
      words.put(locale, translated);
   }

   public String getText(String source, String locale) {
      Map<String, String> words = dictionary.getOrDefault(source, new HashMap<>());
      return words.getOrDefault(locale, source);
   }


   public Map<String, Map<String, String>> getVocabulary() {
      return dictionary;
   }
}
