package xyz.khamim.dgsserver.graphql.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import xyz.khamim.dgsserver.graphql.domain.Person;
import xyz.khamim.dgsserver.graphql.domain.PersonAddress;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class PersonRepository {

    private List<Person> persons;

    @PostConstruct
    private void init() {
        InputStreamReader inputStreamReader = new InputStreamReader(Objects.requireNonNull(
                PersonRepository.class.getResourceAsStream("/data.csv")));
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String line;
        persons = new ArrayList<>();

        while (true) {
            try {
                if ((line = reader.readLine()) == null) break;
                String[] data = line.split(",");
                PersonAddress address = new PersonAddress();
                address.setStreet(data[2]);
                address.setCity(data[3]);
                address.setProvince(data[4]);

                Person person = new Person();
                person.setId(Integer.parseInt(data[0]));
                person.setName(data[1]);
                person.setPersonAddress(address);
                persons.add(person);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public List<Person> getAll() {
        return persons;
    }

    public Person getById(Integer id) {

        return persons.stream().filter(p -> p.getId().equals(id)).toList().get(0);
    }
}
