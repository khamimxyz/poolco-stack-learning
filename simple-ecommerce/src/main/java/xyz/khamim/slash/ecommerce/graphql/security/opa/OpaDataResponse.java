package xyz.khamim.slash.ecommerce.graphql.security.opa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OpaDataResponse {

    private OpaDataResult result;

    @Data
    public static class OpaDataResult {
        private Boolean allow;
    }
}
