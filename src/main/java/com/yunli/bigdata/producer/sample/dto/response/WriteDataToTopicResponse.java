package com.yunli.bigdata.producer.sample.dto.response;

import com.yunli.bigdata.infrastructure.foundation.Response;

/**
 * @author : yunli
 */
public class WriteDataToTopicResponse extends Response {
  private Integer count;

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public WriteDataToTopicResponse() {
  }

  public WriteDataToTopicResponse(Integer count) {
    this.count = count;
  }

  public WriteDataToTopicResponse(int code, String error) {
    super(code, error);
  }
}
