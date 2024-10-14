package _ieh.example.book_service.controller;

import java.text.ParseException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;

import _ieh.example.book_service.api.ApiResponse;
import _ieh.example.book_service.dto.request.AuthenticationRequest;
import _ieh.example.book_service.dto.request.IntrospectRequest;
import _ieh.example.book_service.dto.request.LogoutRequest;
import _ieh.example.book_service.dto.request.RefreshTokenRequest;
import _ieh.example.book_service.dto.response.AuthenticationResponse;
import _ieh.example.book_service.dto.response.IntrospectResponse;
import _ieh.example.book_service.services.Authen.AuthenticationService;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequestMapping("/authen")
@RestController
@Data
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> login(@RequestBody AuthenticationRequest request) {
        System.out.println("AAA");
        ApiResponse<AuthenticationResponse> response = new ApiResponse<>();
        response.setCode(200);
        response.setResult(authenticationService.authen(request));
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/introspect")
    public ResponseEntity<ApiResponse<IntrospectResponse>> introspect(@RequestBody IntrospectRequest request) {
        try {
            return ResponseEntity.ok()
                    .body(ApiResponse.<IntrospectResponse>builder()
                            .code(200)
                            .result(authenticationService.introspect(request))
                            .build());
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        System.out.println("aloala");
        authenticationService.logout(request);
        return ApiResponse.<Void>builder().code(200).build();
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthenticationResponse> refresh(@RequestBody RefreshTokenRequest request)
            throws ParseException, JOSEException {
        return ApiResponse.<AuthenticationResponse>builder()
                .code(200)
                .result(authenticationService.refreshToken(request))
                .build();
    }
}
