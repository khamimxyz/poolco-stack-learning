package xyz.khamim.slash.ecommerce.graphql.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import lombok.RequiredArgsConstructor;
import xyz.khamim.slash.ecommerce.graphql.constant.DynamoDbConstant;
import xyz.khamim.slash.ecommerce.graphql.model.DynamoItem;
import xyz.khamim.slash.ecommerce.graphql.repository.util.ModelMapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class DynamoDbRepository<T extends DynamoItem> {

    private static final String PK = "pk";
    private static final String SK = "sk";
    
    final AmazonDynamoDB amazonDynamoDB;

    public T create(T item) {
      final PutItemRequest putItemRequest = new PutItemRequest()
        .withTableName(DynamoDbConstant.TABLE_NAME)
        .withItem(ModelMapper.mapToAttributes(item));

      amazonDynamoDB.putItem(putItemRequest);

      return item;
    }

    public T get(String id) {
      final String entity = getEntityName();
      final GetItemRequest getItemRequest = new GetItemRequest()
        .withTableName(DynamoDbConstant.TABLE_NAME)
        .withKey(
          Map.of(
            PK, new AttributeValue().withS(entity),
            SK, new AttributeValue().withS(id)
          )
        );

      final GetItemResult result = amazonDynamoDB.getItem(getItemRequest);

      return ModelMapper.mapToModel(result.getItem(), getEntityClass());
    }

    public List<T> getAll() {

      final QueryRequest queryRequest = new QueryRequest()
        .withTableName(DynamoDbConstant.TABLE_NAME)
        .withKeyConditionExpression("#pk = :val")
        .withExpressionAttributeNames(Map.of("#pk", "pk"))
        .withExpressionAttributeValues(Map.of(":val", new AttributeValue().withS(getEntityName())));

      final QueryResult result = amazonDynamoDB.query(queryRequest);
      final List<T> list = new ArrayList<>();
      result.getItems().forEach(item -> list.add(ModelMapper.mapToModel(item, getEntityClass())));

      return list;
    }

    @SuppressWarnings("unchecked")
    public Class<T> getEntityClass() {
      final Type superClass = getClass().getGenericSuperclass();
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

    public String getEntityName() {

      return getEntityClass().getSimpleName().toLowerCase();
    }

    public String getTableName() {

      return DynamoDbConstant.TABLE_NAME;
    }
}