# Getting Started
数据中台实时数据生产使用示例，适用于数据中台V2.1.0版本及以后

## 获取您的token
见：aksk-sample

## 请求REST接口
消息内容为加密方式，加密前格式为：
```shell script
{
  "type": "rain",
  "data": [
    {
      "stcd": "12345678",
      "tm": "2021-03-01 08:00:00",
      "drp": 3.5
    } 
  ]
}
```
