package xyz.khamim.slash.ecommerce.graphql.repository.util;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Stream;

public class ModelMapper {

    private static final String INTERNAL_PACKAGE = "xyz.khamim";

    public static <T> Map<String, AttributeValue> mapToAttributes(T item) {
        Field[] fields = Stream.concat(
                Arrays.stream(item.getClass().getSuperclass().getDeclaredFields()),
                Arrays.stream(item.getClass().getDeclaredFields())
        ).toArray(Field[]::new);

        final Map<String, AttributeValue> itemMap = new HashMap<>();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object itemValue = field.get(item);
                if(itemValue != null) {
                    if (field.getType().equals(Integer.class)) {
                        itemMap.put(field.getName(), new AttributeValue().withN(String.valueOf(field.get(item))));
                    } else if (field.getType().equals(String.class)) {
                        itemMap.put(field.getName(), new AttributeValue((String) field.get(item)));
                    } else if (field.getType().getPackageName().startsWith(INTERNAL_PACKAGE)) {
                        ObjectMapper mapper = new ObjectMapper();
                        String json = mapper.writeValueAsString(field.get(item));
                        itemMap.put(field.getName(), new AttributeValue().withS(json));
                    }
                }
            } catch (IllegalAccessException | JsonProcessingException ignored) {}
        }

        return itemMap;
    }

    public static <T> T mapToModel(Map<String, AttributeValue> item, Class<T> tClass) {
        T obj = null;
        try {
            Constructor<T> noArgsConstructor = tClass.getConstructor();
            obj = noArgsConstructor.newInstance();
            Field[] fields = Stream.concat(
                    Arrays.stream(tClass.getSuperclass().getDeclaredFields()),
                    Arrays.stream(tClass.getDeclaredFields())
            ).toArray(Field[]::new);
            for(Field field : fields) {
                field.setAccessible(true);
                if(field.getType().equals(Integer.class)) {
                    field.set(obj, Integer.parseInt(item.get(field.getName()).getN()));
                } else if(field.getType().equals(String.class)) {
                    field.set(obj, item.get(field.getName()).getS());
                } else if(field.getType().getPackageName().startsWith(INTERNAL_PACKAGE)) {
                    ObjectMapper mapper = new ObjectMapper();
                    Object data = mapper.readValue(item.get(field.getName()).getS(), field.getType());
                    field.set(obj, data);
                }
            }
        } catch (Exception ignored) {}

        return obj;
    }
}
