package xyz.khamim.slash.ecommerce.graphql.api;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import xyz.khamim.slash.ecommerce.graphql.dto.AuthResDto;
import xyz.khamim.slash.ecommerce.graphql.dto.VerifyResDto;
import xyz.khamim.slash.ecommerce.graphql.input.AuthReq;
import xyz.khamim.slash.ecommerce.graphql.input.VerifyReq;
import xyz.khamim.slash.ecommerce.graphql.service.AuthService;

@DgsComponent
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  private static final String VERIFY_MSG = "Your verification code has been sent";

  @DgsMutation
  public Mono<AuthResDto> auth(AuthReq authReq) {

    return Mono.fromCallable(() -> authService.auth(authReq))
      .map(session -> AuthResDto.builder().session(session).message(VERIFY_MSG).build());
  }

  @DgsMutation
  public Mono<VerifyResDto> verify(VerifyReq verifyReq) {

    return Mono.fromCallable(() -> authService.verify(verifyReq))
      .map(token -> VerifyResDto.builder().token(token).build());
  }
}
