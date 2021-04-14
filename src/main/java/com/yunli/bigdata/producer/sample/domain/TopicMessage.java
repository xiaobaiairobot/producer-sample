package com.yunli.bigdata.producer.sample.domain;

import java.util.List;
import java.util.Map;

/**
 * @author david
 * @date 2021/4/14 9:51 上午
 */
public class TopicMessage {
  /**
   * 消息类别
   */
  private String type;

  /**
   * 消息内容
   */
  private List<Map<String, Object>> data;

  public TopicMessage() {
  }

  public TopicMessage(String type, List<Map<String, Object>> data) {
    this.type = type;
    this.data = data;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public List<Map<String, Object>> getData() {
    return data;
  }

  public void setData(List<Map<String, Object>> data) {
    this.data = data;
  }
}
