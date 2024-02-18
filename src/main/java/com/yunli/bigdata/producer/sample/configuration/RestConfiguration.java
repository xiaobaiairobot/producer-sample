package com.yunli.bigdata.producer.sample.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author david
 * @date 2021/4/14 11:37 上午
 */
@Component
public class RestConfiguration {
  @Bean("httpTemplate")
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean("httpsTemplate")
  public RestTemplate httpsRestTemplate() {
    return new RestTemplate(new HttpsClientRequestFactory());
  }
}
