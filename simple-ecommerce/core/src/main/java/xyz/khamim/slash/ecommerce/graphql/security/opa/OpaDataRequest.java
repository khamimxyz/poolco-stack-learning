package xyz.khamim.slash.ecommerce.graphql.security.opa;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@AllArgsConstructor
@Data
public class OpaDataRequest {
    Map<String, Object> input;
}
