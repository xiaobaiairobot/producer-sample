package com.yunli.bigdata.producer.sample.util;

import org.springframework.web.util.UriComponentsBuilder;

import com.yunli.bigdata.infrastructure.foundation.util.StringUtil;

/**
 * @author : yunli
 */
public class UriComponentsBuilderUtil {
  public static void addQueryParam(UriComponentsBuilder builder, Object... params) {
    for (int i = 0; i < params.length; i += 2) {
      if (params[i + 1] != null) {
        if (!params[i + 1].getClass().equals(String.class) || StringUtil.hasText((String) params[i + 1])) {
          builder.queryParam(params[i].toString(), params[i + 1]);
        }
      }
    }
  }
}
