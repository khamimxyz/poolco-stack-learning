package xyz.khamim.reactiveexample.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientAddressDto {
  private String address;
  private String suburb;
  private String state;
  private String postcode;
}
