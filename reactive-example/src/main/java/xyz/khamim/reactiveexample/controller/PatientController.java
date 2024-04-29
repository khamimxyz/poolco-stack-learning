package xyz.khamim.reactiveexample.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import xyz.khamim.reactiveexample.dto.PatientDto;
import xyz.khamim.reactiveexample.dto.PatientListPayload;
import xyz.khamim.reactiveexample.service.PatientService;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

  private final PatientService patientService;

  @GetMapping
  public Mono<ResponseEntity<PatientListPayload>> getAllPatients(
      @RequestParam(value = "keyword", required = false) String keyword,
      @RequestParam("page") int page, @RequestParam("size") int size) {

    return patientService.getAllPatients().map(ResponseEntity::ok);
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<PatientDto>> getPatientById(@PathVariable Long id) {
    return patientService.getPatientById(id)
            .map(ResponseEntity::ok)
            .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
  }

  @PostMapping("/save")
  public Mono<ResponseEntity<PatientDto>> savePatient(@RequestBody PatientDto patientDto) {
    return patientService.savePatient(patientDto).map(ResponseEntity::ok);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
    patientService.deletePatient(id);
    return ResponseEntity.noContent().build();
  }
}
