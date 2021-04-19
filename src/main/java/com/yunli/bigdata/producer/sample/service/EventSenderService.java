package com.yunli.bigdata.producer.sample.service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunli.bigdata.infrastructure.foundation.CompressionAlgorithm;
import com.yunli.bigdata.infrastructure.foundation.CompressionProcessor;
import com.yunli.bigdata.infrastructure.foundation.CompressionProcessorFactory;
import com.yunli.bigdata.infrastructure.foundation.util.JsonUtil;
import com.yunli.bigdata.producer.sample.domain.TopicMessage;
import com.yunli.bigdata.producer.sample.dto.response.WriteDataToTopicResponse;
import com.yunli.bigdata.producer.sample.util.UriComponentsBuilderUtil;

/**
 * @author david
 * @date 2021/4/14 11:15 上午
 */
@Service
public class EventSenderService {

  private static final String SERVER_ADDRESS = "http://172.30.13.177:30003/x-storage-service";

//  private static final String SERVER_ADDRESS = "http://localhost:48183";

  private final RestTemplate restTemplate;

  private final Logger LOGGER = LoggerFactory.getLogger(EventSenderService.class);

  @Autowired
  public EventSenderService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public void sendMessage(Long topicId, String typeCode, String compressionAlgorithm, String token) {
    // ToStringSerializerBase toStringSerializerBase
    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(SERVER_ADDRESS)
        .pathSegment("storages", "topics", String.valueOf(topicId), "types", typeCode, "data");
    UriComponentsBuilderUtil.addQueryParam(builder, "compressionAlgorithm", compressionAlgorithm);
    byte[] data = this.generateData();

    HttpHeaders headers = new HttpHeaders();
    headers.add("x-token", token);
    WriteDataToTopicResponse response = this.restTemplate
        .exchange(builder.build().encode().toUri(), HttpMethod.POST, new HttpEntity<>(data, headers),
            WriteDataToTopicResponse.class).getBody();

    if (response == null) {
      throw new RuntimeException("the response is null");
    }
    if (response.isSuccess()) {
      LOGGER.info("请求成功，数据量: {}", response.getCount());
    } else {
      LOGGER.error(response.getError());
      throw new RuntimeException(response.getError());
    }
  }

  private byte[] generateData() {
    TopicMessage topicMessage = new TopicMessage();
    topicMessage.setType("rain");
    List<Map<String, Object>> data = new ArrayList<>();
    Map<String, Object> item1 = new HashMap<>();
    item1.put("stcd", "1000001");
    item1.put("tm", new Date());
    item1.put("drp", 18.1);
    item1.put("dyp", 19.3);
    data.add(item1);
    topicMessage.setData(data);

    String message = JsonUtil.writeValueAsString(topicMessage);
    System.out.println(message);

    return message.getBytes(StandardCharsets.UTF_8);
    // 下面是使用压缩算法进行压缩，可以有效降低消息体积。
//    CompressionProcessor compressionProcessor = CompressionProcessorFactory.get(CompressionAlgorithm.Snappy);
//    return compressionProcessor.compress(jsonString.getBytes(StandardCharsets.UTF_8));
  }
}
