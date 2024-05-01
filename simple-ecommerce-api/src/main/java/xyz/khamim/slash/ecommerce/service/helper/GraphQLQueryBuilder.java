package xyz.khamim.slash.ecommerce.service.helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GraphQLQueryBuilder {
    private final StringBuilder queryBuilder;

    public GraphQLQueryBuilder() {
        this.queryBuilder = new StringBuilder();
    }

    public GraphQLQueryBuilder mutation() {
        queryBuilder.append("mutation {").append(" ");
        return this;
    }

    public GraphQLQueryBuilder query() {
        queryBuilder.append("{").append(" ");
        return this;
    }

    @SuppressWarnings("unchecked")
    public GraphQLQueryBuilder fields(Object... fields) {

        List<String> fieldAsString = new ArrayList<>();
        for (Object field : fields) {
            if (field instanceof String) {
                fieldAsString.add((String) field);
            } else if (field instanceof Map<?,?>) {
                ((Map<?, ?>) field).keySet().forEach(key -> {
                    StringBuilder mapToFieldBuilder = new StringBuilder();
                    mapToFieldBuilder.append(key).append("{").append("\n");
                    List<String> details = (List<String>) ((Map<?, ?>) field).get(key);
                    details.forEach(detail -> mapToFieldBuilder.append(detail).append("\n"));
                    mapToFieldBuilder.append("}");
                    fieldAsString.add(mapToFieldBuilder.toString());
                });
            }
        }

        queryBuilder.append("{ ").append(String.join(" ", fieldAsString)).append(" } ");
        return this;
    }

    public GraphQLQueryBuilder operation(String operation, Map<String, Object> argsMap) {

        queryBuilder.append(operation);
        if(!argsMap.isEmpty()) {
            queryBuilder.append("(");
            int argCount = 0;
            for(Map.Entry<String, Object> entry : argsMap.entrySet()) {
                Object value = entry.getValue();
                queryBuilder.append(entry.getKey()).append(":").append(" ");
                if (value instanceof String) {
                    queryBuilder.append("\"").append(value).append("\"");
                } else if(value instanceof Number) {
                    queryBuilder.append(value);
                } else {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(JsonWriteFeature.QUOTE_FIELD_NAMES.mappedFeature(), false);
                    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                    try {
                        queryBuilder.append(mapper.writeValueAsString(value));
                    } catch (JsonProcessingException ignored) {}
                }
                ++argCount;
                if(argCount < argsMap.size()) {
                    queryBuilder.append(", ");
                } else {
                    queryBuilder.append(")").append(" ");
                }
            }
        }
        return this;
    }

    public String build() {
        return queryBuilder.append("}").toString();
    }
}