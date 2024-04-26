package xyz.khamim.slash.ecommerce.graphql.repository.util;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Stream;

public class ModelMapper {

    @SuppressWarnings("unchecked")
    public static <T> Map<String, AttributeValue> mapToAttributes(T item) {
        Field[] fields = Stream.concat(
                Arrays.stream(item.getClass().getSuperclass().getDeclaredFields()),
                Arrays.stream(item.getClass().getDeclaredFields())
        ).toArray(Field[]::new);

        final Map<String, AttributeValue> itemMap = new HashMap<>();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                if (field.getType().equals(Integer.class)) {
                    itemMap.put(field.getName(), new AttributeValue().withN(String.valueOf(field.get(item))));
                } else if(field.getType().equals(String.class)){
                    itemMap.put(field.getName(), new AttributeValue((String) field.get(item)));
                } else if(field.getType().equals(List.class)) {
                    itemMap.put(field.getName(), new AttributeValue((List<String>) field.get(item)));
                }
            } catch (IllegalAccessException ignored) {}
        }

        return itemMap;
    }

    @SuppressWarnings("unchecked")
    public static <T> T mapObjectToModel(Map<String, Object> item, Class<T> tClass) {

        Map<String, AttributeValue> attributeValueMap = new HashMap<>();
        item.keySet().forEach(key -> {
            Object value = item.get(key);
            if (value instanceof Collection<?>) {
               attributeValueMap.put(key, new AttributeValue().withSS((Collection<String>) item.get(key)));
            } else if (value instanceof String) {
                attributeValueMap.put(key, new AttributeValue().withS(String.valueOf(item.get(key))));
            } else if (value instanceof Number) {
                attributeValueMap.put(key, new AttributeValue().withN(String.valueOf(item.get(key))));
            }
        });

        return mapToModel(attributeValueMap, tClass);
    }

    public static <T> T mapToModel(Map<String, AttributeValue> item, Class<T> tClass) {
        T obj = null;
        try {
            Constructor<T> noArgsConstructor = tClass.getConstructor();
            obj = noArgsConstructor.newInstance();
            for(Field field : tClass.getDeclaredFields()) {
                field.setAccessible(true);
                if(field.getType().equals(Integer.class)) {
                    field.set(obj, Integer.parseInt(item.get(field.getName()).getN()));
                } else if(field.getType().equals(String.class)) {
                    field.set(obj, item.get(field.getName()).getS());
                } else if(field.getType().equals(List.class)) {
                    field.set(obj, item.get(field.getName()).getSS());
                }
            }
        } catch (Exception ignored) {}

        return obj;
    }
}
