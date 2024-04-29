package xyz.khamim.reactiveexample.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import xyz.khamim.reactiveexample.dto.PatientAddressDto;
import xyz.khamim.reactiveexample.dto.PatientDto;
import xyz.khamim.reactiveexample.dto.PatientListPayload;
import xyz.khamim.reactiveexample.model.Patient;
import xyz.khamim.reactiveexample.model.PatientAddress;
import xyz.khamim.reactiveexample.repository.PatientAddressRepository;
import xyz.khamim.reactiveexample.repository.PatientRepository;

@Service
@RequiredArgsConstructor
public class PatientService {
  private final PatientRepository patientRepository;
  private final PatientAddressRepository patientAddressRepository;
  private final ModelMapper modelMapper;
  public Mono<PatientListPayload> getAllPatients() {

    return patientRepository
            .findAll()
            .flatMap(data -> {
                PatientDto patientDto = modelMapper.map(data, PatientDto.class);
                return patientAddressRepository.findByPatientId(data.getId())
                        .map(address -> modelMapper.map(address, PatientAddressDto.class))
                        .map(addresses -> {
                            patientDto.setAustralianAddress(addresses);
                            return patientDto;
                        });
            })
            .collectList()
            .map(data ->
              PatientListPayload
                .builder()
                .totalData(data.size())
                .totalPages(0)
                .data(data)
                .build()
            );
  }

  public Mono<PatientDto> getPatientById(Long id) {
    final Mono<PatientDto> patientMono = patientRepository.findById(id)
        .map(e -> modelMapper.map(e, PatientDto.class));

    final Mono<PatientAddressDto> patientAddressMono = patientAddressRepository
            .findByPatientId(id)
            .map(e -> modelMapper.map(e, PatientAddressDto.class));

    return patientMono.zipWith(patientAddressMono)
                    .map(tuple -> {
                        PatientDto patientDto = tuple.getT1();
                        patientDto.setAustralianAddress(tuple.getT2());
                        return patientDto;
                    });
  }

  @Transactional
  public Mono<PatientDto> savePatient(PatientDto patientDto) {
    Patient patient = modelMapper.map(patientDto, Patient.class);
    PatientAddress patientAddress = modelMapper.map(patientDto.getAustralianAddress(), PatientAddress.class);
    return patientRepository
            .save(patient)
            .map(data -> {
              patientAddress.setPatientId(data.getId());
              patientAddressRepository.save(patientAddress).subscribe();
              patientDto.setId(data.getId());
              return patientDto;
            });
  }

  public void deletePatient(Long id) {
    patientRepository.deleteById(id);
  }

}
