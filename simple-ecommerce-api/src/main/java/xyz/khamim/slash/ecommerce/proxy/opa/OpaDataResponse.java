package xyz.khamim.slash.ecommerce.proxy.opa;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OpaDataResponse {

    private OpaDataResult result;

    @Data
    public static class OpaDataResult {
        private Boolean allow;
    }
}
