package xyz.khamim.dgsserver.fetcher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration;

import org.intellij.lang.annotations.Language;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.khamim.dgsserver.graphql.domain.Person;
import xyz.khamim.dgsserver.graphql.domain.PersonAddress;
import xyz.khamim.dgsserver.graphql.fetcher.PersonDataFetcher;
import xyz.khamim.dgsserver.graphql.repository.PersonRepository;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {
        DgsAutoConfiguration.class,
        PersonDataFetcher.class,
        PersonRepository.class}
)
public class PersonFetcherTest {

    @Autowired
    private DgsQueryExecutor executor;

    @Test
    void searchPerson_shouldReturnCorrectResult() throws RuntimeException {

       @Language("GraphQL") final String query = "{\n" +
                "  searchPerson(id: 1) {\n" +
                "    name,\n" +
                "    personAddress {\n" +
                "      street,\n" +
                "      city\n" +
                "    }\n" +
                "  }\n" +
                "}";
        Map<Object, Object> resultMap = executor.execute(query).getData();
        Person actual;
        try {
            JSONObject json = new JSONObject(resultMap).getJSONObject("searchPerson");
            ObjectMapper mapper = new ObjectMapper();
            actual = mapper.readValue(json.toString(), Person.class);
        } catch (JSONException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        PersonAddress address = new PersonAddress();
        address.setStreet("Algarve Street");
        address.setCity("Portimao");

        Person expected = new Person();
        expected.setName("John Doe");
        expected.setPersonAddress(address);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
