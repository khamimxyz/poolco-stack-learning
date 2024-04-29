package xyz.khamim.dgsserver.graphql.fetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.khamim.dgsserver.graphql.domain.Person;
import xyz.khamim.dgsserver.graphql.repository.PersonRepository;

import java.util.List;

@DgsComponent
public class PersonDataFetcher {

    @Autowired
    private PersonRepository repository;

    @DgsQuery
    public List<Person> allPersons() {
        return repository.getAll();
    }

    @DgsQuery Person searchPerson(@InputArgument Integer id) {

        return repository.getById(id);
    }
}
