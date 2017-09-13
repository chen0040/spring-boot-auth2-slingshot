package com.github.chen0040.bootslingshot.utils;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * Created by xschen on 9/7/2017.
 */
public class HttpUtils {
   private static CloseableHttpClient buildClient() {
      //HttpClientBuilder builder = HttpClientBuilder.create();

      int timeout = 60;
      RequestConfig config = RequestConfig.custom()
              .setSocketTimeout(timeout * 1000)
              .setConnectionRequestTimeout(timeout * 1000)
              .setConnectTimeout(timeout * 1000).build();


      return HttpClients.custom().setDefaultRequestConfig(config).build(); //builder.build();

   }

   public static byte[] getBytes(String url) throws IOException {

      HttpGet httpget = new HttpGet(url);
      CloseableHttpClient httpClient = buildClient();
      HttpResponse response = httpClient.execute(httpget);
      HttpEntity entity = response.getEntity();
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      entity.writeTo(baos);
      return baos.toByteArray();
   }

}
