package xyz.khamim.slash.ecommerce.graphql.security;

import com.netflix.graphql.dgs.context.DgsContext;
import com.netflix.graphql.dgs.reactive.internal.DgsReactiveRequestData;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
@Slf4j
public class SecureMethodInterceptor {

  private final OpaAuthorizationManager opaAuthorizationManager;

  @Value("${secureMethod.allow}")
  private String allow;

  @Before("@annotation(secureMethod)")
  @SuppressWarnings("deprecation")
  public void beforeSecureMethod(JoinPoint joinPoint, SecureMethod secureMethod) {

    if (!"Y".equals(allow)) {
      final DataFetchingEnvironment env = (DataFetchingEnvironment) joinPoint.getArgs()[0];
      DgsContext context = env.getContext();
      String jwtToken = null;
      DgsReactiveRequestData requestData = (DgsReactiveRequestData) context.getRequestData();
      if (requestData != null) {
        if (requestData.getHeaders() != null) {
          jwtToken = requestData.getHeaders().getFirst("Authorization");
          if (jwtToken != null) {
            jwtToken = jwtToken.replaceAll("Bearer ", "");
          }
        }
      }
      String module = secureMethod.module();

      if (jwtToken == null || jwtToken.isEmpty() || !opaAuthorizationManager.check(jwtToken, module)) {
        throw new RuntimeException("Invalid JWT");
      }
    }
  }

}
