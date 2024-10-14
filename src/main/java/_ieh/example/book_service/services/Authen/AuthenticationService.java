package _ieh.example.book_service.services.Authen;

import java.text.ParseException;

import com.nimbusds.jose.JOSEException;

import _ieh.example.book_service.dto.request.AuthenticationRequest;
import _ieh.example.book_service.dto.request.IntrospectRequest;
import _ieh.example.book_service.dto.request.LogoutRequest;
import _ieh.example.book_service.dto.request.RefreshTokenRequest;
import _ieh.example.book_service.dto.response.AuthenticationResponse;
import _ieh.example.book_service.dto.response.IntrospectResponse;

public interface AuthenticationService {
    public AuthenticationResponse authen(AuthenticationRequest request);

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;

    public void logout(LogoutRequest request) throws ParseException, JOSEException;

    public AuthenticationResponse refreshToken(RefreshTokenRequest request) throws ParseException, JOSEException;
}
