package xyz.khamim.reactiveexample.boot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.khamim.reactiveexample.dto.PatientAddressDto;
import xyz.khamim.reactiveexample.dto.PatientDto;
import xyz.khamim.reactiveexample.service.PatientService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
@RequiredArgsConstructor
public class DataInitializer {

  private final PatientService patientService;

  public void initPatientsData() {
    try {
      InputStream in = DataInitializer.class.getClassLoader().getResourceAsStream("data/patients.csv");
      if(in != null) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = reader.readLine()) != null) {
          String[] datas = line.split(",");

          PatientAddressDto address = PatientAddressDto.builder()
                  .address(datas[4])
                  .suburb(datas[5])
                  .state(datas[6])
                  .postcode(datas[7])
                  .build();

          PatientDto patient = PatientDto.builder()
                  .firstName(datas[0])
                  .lastName(datas[1])
                  .dateOfBirth(datas[2])
                  .gender(datas[3])
                  .phoneNo(datas[8])
                  .australianAddress(address)
                  .build();

          patientService.savePatient(patient).subscribe();
        }
      }
    } catch (IOException ignored) {}
  }
}
