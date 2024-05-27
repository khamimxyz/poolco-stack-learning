package xyz.khamim.slash.ecommerce.graphql.kafka.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

  public static <T> String convertToJson(T obj) throws JsonProcessingException {

    ObjectMapper mapper = new ObjectMapper();

    return mapper.writeValueAsString(obj);
  }

  public static <T> T convertToObj(String json, Class<T> objClass) throws JsonProcessingException {

    ObjectMapper mapper = new ObjectMapper();

    return mapper.readValue(json, objClass);
  }
}
