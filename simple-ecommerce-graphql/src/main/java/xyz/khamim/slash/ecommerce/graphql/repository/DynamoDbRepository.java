package xyz.khamim.slash.ecommerce.graphql.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import reactor.core.publisher.Mono;
import xyz.khamim.slash.ecommerce.graphql.dynamodb.AmazonDynamoDBFactory;
import xyz.khamim.slash.ecommerce.graphql.model.DynamoItem;
import xyz.khamim.slash.ecommerce.graphql.repository.util.ModelMapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DynamoDbRepository<T extends DynamoItem> {

    private static final String ID = "id";

    final AmazonDynamoDB amazonDynamoDB;

    final DynamoDB dynamoDB;

    public DynamoDbRepository() {

        amazonDynamoDB = AmazonDynamoDBFactory.getAmazonDynamoDB();
        dynamoDB = new DynamoDB(amazonDynamoDB);
    }

    public Mono<T> create(T item) {
        PutItemRequest putItemRequest = new PutItemRequest()
                .withTableName(getTableName())
                .withItem(ModelMapper.mapToAttributes(item));

        return Mono.fromRunnable(() -> amazonDynamoDB.putItem(putItemRequest))
                .thenReturn(item);
    }

    public Mono<T> get(String id) {
        GetItemRequest getItemRequest = new GetItemRequest()
                .withTableName(getTableName())
                .withKey(Collections.singletonMap(ID, new AttributeValue(id)));

        return Mono
            .fromCallable(() -> amazonDynamoDB.getItem(getItemRequest))
            .flatMap(result -> result == null ? Mono.empty() :
                    Mono.just(ModelMapper.mapToModel(result.getItem(), getTableClass())));
    }

    public Mono<T> update(T item) {

        Map<String, AttributeValue> attributeMap = ModelMapper.mapToAttributes(item);
        Map<String, AttributeValueUpdate> itemUpdateMap = new HashMap<>();
        for (String key : attributeMap.keySet()) {
            if(!ID.equals(key)) {
                itemUpdateMap.put(key, new AttributeValueUpdate().withValue(attributeMap.get(key)));
            }
        }
        UpdateItemRequest updateItemRequest = new UpdateItemRequest()
                .withTableName(getTableName())
                .withKey(Map.of(ID, new AttributeValue(item.getId())))
                .withAttributeUpdates(itemUpdateMap);

        amazonDynamoDB.updateItem(updateItemRequest);

        return Mono.just(item);
    }

    public void delete(String id) {
        Map<String, AttributeValue> key = Collections.singletonMap(ID, new AttributeValue(id));

        DeleteItemRequest deleteItemRequest = new DeleteItemRequest()
                .withTableName(getTableName())
                .withKey(key);

        amazonDynamoDB.deleteItem(deleteItemRequest);
    }

    @SuppressWarnings("unchecked")
    public Class<T> getTableClass() {
        Type superClass = getClass().getGenericSuperclass();
        Class<T> tableClazz = null;
        if (superClass instanceof ParameterizedType) {
            Type[] typeArgs = ((ParameterizedType) superClass).getActualTypeArguments();
            if (typeArgs.length > 0) {
                Type typeArg = typeArgs[0];
                if (typeArg instanceof Class) {
                    tableClazz = (Class<T>) typeArg;
                }
            }
        }

        return tableClazz;
    }

    public String getTableName() {

        return getTableClass().getSimpleName().toLowerCase();
    }
}