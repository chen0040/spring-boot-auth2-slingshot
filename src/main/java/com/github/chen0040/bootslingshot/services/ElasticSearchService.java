package com.github.chen0040.bootslingshot.services;


import com.github.chen0040.bootslingshot.models.IndexMap;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.List;


/**
 * Created by xschen on 16/7/2017.
 */
public interface ElasticSearchService {
   void index(String indexName) throws IOException;
   void index(Object source, String indexName, String typeName, String id) throws IOException;
   void indexMapping(String indexName, String typeName, IndexMap im) throws IOException;
   void create(Object source, String indexName, String typeName, String id) throws IOException;
   void update(Object source, String indexName, String typeName, String id) throws IOException;

   <T> T get(String id, String indexName, String typeName, Class<T> clazz) throws IOException;
   boolean exists(String id, String indexName, String typeName) throws IOException;

   void delete(String indexName, String typeName, String id) throws IOException;

   <T> List<T> search(String indexName, String typeName, SearchSourceBuilder builder, Class<T> clazz) throws IOException;
}
