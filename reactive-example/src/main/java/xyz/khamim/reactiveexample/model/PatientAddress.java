package xyz.khamim.reactiveexample.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "patient_address")
public class PatientAddress {

  @Id
  private Long id;

  private Long patientId;

  private String address;

  private String suburb;

  private String state;

  private String postcode;
}
