package xyz.khamim.reactiveexample.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import xyz.khamim.reactiveexample.model.Patient;


public interface PatientRepository extends ReactiveCrudRepository<Patient, Long> {
  @Query("SELECT p FROM Patient p WHERE " +
      "(LOWER(p.firstName) LIKE LOWER(CONCAT('%', :name, '%')) " +
      "OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :name, '%')))")
  Flux<Patient> findByName(@Param("name") String name);
}
