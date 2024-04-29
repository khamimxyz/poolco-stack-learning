package xyz.khamim.reactiveexample.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientDto {
  private Long id;
  private String firstName;
  private String lastName;
  private String dateOfBirth;
  private String gender;
  private PatientAddressDto australianAddress;
  private String phoneNo;
}
