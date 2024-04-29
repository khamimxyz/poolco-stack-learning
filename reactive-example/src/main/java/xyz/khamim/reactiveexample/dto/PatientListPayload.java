package xyz.khamim.reactiveexample.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientListPayload {
  private int totalPages = 0;
  private long totalData = 0;
  private List<PatientDto> data = new ArrayList<>();
}
