package xyz.khamim.reactiveexample.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import xyz.khamim.reactiveexample.model.PatientAddress;

public interface PatientAddressRepository extends ReactiveCrudRepository<PatientAddress, Long> {

    Mono<PatientAddress> findByPatientId(Long patientId);
}
