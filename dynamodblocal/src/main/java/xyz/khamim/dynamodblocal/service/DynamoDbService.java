package xyz.khamim.dynamodblocal.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.model.*;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.khamim.dynamodblocal.model.Student;

import java.util.*;

@Service
@Slf4j
public class DynamoDbService {

    private final AmazonDynamoDB amazonDynamoDB;

    private final DynamoDB dynamoDB;

    public DynamoDbService() {
        amazonDynamoDB = DynamoDBEmbedded.create().amazonDynamoDB();
        dynamoDB = new DynamoDB(amazonDynamoDB);
    }

    @PostConstruct
    private void createTableIfNotExists() {
        log.info("Creating dynamo db table");
        CreateTableRequest request = new CreateTableRequest()
                .withTableName("students")
                .withKeySchema(new KeySchemaElement("id", KeyType.HASH))
                .withAttributeDefinitions(new AttributeDefinition("id", ScalarAttributeType.S))
                .withProvisionedThroughput(new ProvisionedThroughput(5L, 5L)); // Adjust read and write capacity as needed

        amazonDynamoDB.createTable(request);
    }

    public Student createStudent(Student student) {
        String id = UUID.randomUUID().toString();
        student.setId(id);
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("id", new AttributeValue(student.getId()));
        item.put("name", new AttributeValue(student.getName()));
        item.put("age", new AttributeValue().withN(String.valueOf(student.getAge())));

        PutItemRequest putItemRequest = new PutItemRequest()
                .withTableName("students")
                .withItem(item);

        amazonDynamoDB.putItem(putItemRequest);

        return student;
    }

    public Student getStudent(String id) {
        GetItemRequest getItemRequest = new GetItemRequest()
                .withTableName("students")
                .withKey(Collections.singletonMap("id", new AttributeValue(id)));

        GetItemResult getItemResult = amazonDynamoDB.getItem(getItemRequest);
        if (getItemResult.getItem() != null) {
            Map<String, AttributeValue> item = getItemResult.getItem();
            return new Student(item.get("id").getS(), item.get("name").getS(), Integer.parseInt(item.get("age").getN()));
        }
        return null;
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        Table table = dynamoDB.getTable("students");

        table.scan().forEach(item -> {
            String id = item.getString("id");
            String name = item.getString("name");
            int age = item.getInt("age");

            students.add(new Student(id, name, age));
        });

        return students;
    }

    public void updateStudent(String id, Student updatedStudent) {
        Map<String, AttributeValue> key = Collections.singletonMap("id", new AttributeValue(id));
        Map<String, AttributeValueUpdate> attributeUpdates = new HashMap<>();
        attributeUpdates.put("name", new AttributeValueUpdate().withValue(new AttributeValue(updatedStudent.getName())).withAction(AttributeAction.PUT));
        attributeUpdates.put("age", new AttributeValueUpdate().withValue(new AttributeValue().withN(String.valueOf(updatedStudent.getAge()))).withAction(AttributeAction.PUT));

        UpdateItemRequest updateItemRequest = new UpdateItemRequest()
                .withTableName("students")
                .withKey(key)
                .withAttributeUpdates(attributeUpdates);

        amazonDynamoDB.updateItem(updateItemRequest);
    }

    public void deleteStudent(String id) {
        Map<String, AttributeValue> key = Collections.singletonMap("id", new AttributeValue(id));

        DeleteItemRequest deleteItemRequest = new DeleteItemRequest()
                .withTableName("students")
                .withKey(key);

        amazonDynamoDB.deleteItem(deleteItemRequest);
    }
}
