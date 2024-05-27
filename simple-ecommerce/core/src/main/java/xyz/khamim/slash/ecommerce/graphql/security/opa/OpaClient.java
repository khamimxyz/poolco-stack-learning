package xyz.khamim.slash.ecommerce.graphql.security.opa;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "opaAuthorization", url = "${opa.authorization.data.url}")
public interface OpaClient {

    @PostMapping("/ecommerce/authorization")
    OpaDataResponse authorizedToAccessAPI(@RequestBody OpaDataRequest opaDataRequest);

}