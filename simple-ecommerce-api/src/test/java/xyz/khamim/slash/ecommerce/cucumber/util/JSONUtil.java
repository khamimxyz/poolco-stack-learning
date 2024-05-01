package xyz.khamim.slash.ecommerce.cucumber.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtil {

    public static String convertToJson(Object obj) {
        String json = "{}";
        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException ignored) {}

        return json;
    }

    public static <T> T convertToObject(String json, Class<T> tClass) {
        T obj = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            obj = mapper.readValue(json, tClass);
        } catch (JsonProcessingException ignored) {}

        return obj;
    }
}
