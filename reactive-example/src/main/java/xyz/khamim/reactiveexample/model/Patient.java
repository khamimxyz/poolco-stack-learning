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
@Table(name = "patient")
public class Patient {

  @Id
  private Long id;
  private String firstName;
  private String lastName;
  private String dateOfBirth;
  private String gender;
  private String phoneNo;
}
