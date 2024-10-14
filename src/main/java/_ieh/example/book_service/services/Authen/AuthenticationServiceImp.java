package _ieh.example.book_service.services.Authen;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import _ieh.example.book_service.dto.request.*;
import _ieh.example.book_service.dto.response.AuthenticationResponse;
import _ieh.example.book_service.dto.response.IntrospectResponse;
import _ieh.example.book_service.exception.AppException;
import _ieh.example.book_service.exception.ErrorCode;
import _ieh.example.book_service.model.TokenValidated;
import _ieh.example.book_service.model.User;
import _ieh.example.book_service.repository.TokenValidatedRepository;
import _ieh.example.book_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Data
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class AuthenticationServiceImp implements AuthenticationService {
    UserRepository userRepository;
    TokenValidatedRepository tokenValidatedRepository;

    @NonFinal
    @Value("${jwt.secret-key}")
    protected String keySecret;

    @NonFinal
    @Value("${jwt.duration-time}")
    protected Long durationTime;

    @NonFinal
    @Value("${jwt.refreshable-time}")
    protected Long refreshableTime;

    @Override
    public AuthenticationResponse authen(AuthenticationRequest request) {
        System.out.println("Authentication: " + request);
        User user = userRepository
                .findByUserName(request.getUserName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_ISNOTAVAILABLE));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Boolean valid = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (valid) {
            return AuthenticationResponse.builder()
                    .valid(valid)
                    .token(generateJWT(user))
                    .build();
        }
        return AuthenticationResponse.builder().valid(false).build();
    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        boolean valid = true;
        try {
            SignedJWT signedJWT = verifyToken(request.getToken(), false);
        } catch (AppException e) {
            valid = false;
        }
        return IntrospectResponse.builder().valid(valid).build();
    }

    @Override
    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        System.out.println("Aloaloal");
        try {
            var signerJwt = verifyToken(request.getToken(), true);
            String jwtId = signerJwt.getJWTClaimsSet().getJWTID();
            Date expTime = signerJwt.getJWTClaimsSet().getExpirationTime();
            TokenValidated tokenValidated =
                    TokenValidated.builder().id(jwtId).expTime(expTime).build();
            tokenValidatedRepository.save(tokenValidated);
        } catch (AppException e) {
            System.out.println("Token already validated");
        }
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenRequest request) throws ParseException, JOSEException {
        var signerToken = verifyToken(request.getToken(), true);
        var tid = signerToken.getJWTClaimsSet().getJWTID();
        Date expTime = signerToken.getJWTClaimsSet().getExpirationTime();
        var userName = signerToken.getJWTClaimsSet().getSubject();
        TokenValidated tokenValidated =
                TokenValidated.builder().id(tid).expTime(expTime).build();
        tokenValidatedRepository.save(tokenValidated);
        User user =
                userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
        return AuthenticationResponse.builder()
                .valid(true)
                .token(generateJWT(user))
                .build();
    }

    public SignedJWT verifyToken(String token, Boolean isRefresh) throws ParseException, JOSEException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier jwsVerifier = new MACVerifier(keySecret.getBytes());
        Date expTime = isRefresh
                ? new Date(signedJWT
                        .getJWTClaimsSet()
                        .getIssueTime()
                        .toInstant()
                        .plus(refreshableTime, ChronoUnit.SECONDS)
                        .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(jwsVerifier);
        if (!(verified && expTime.after(new Date()))) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        if (tokenValidatedRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        return signedJWT;
    }
    ;

    private String generateJWT(User user) {
        String scope = tranScope(user);
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUserName())
                .issuer("29ieh")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(durationTime, ChronoUnit.SECONDS).toEpochMilli()))
                .claim("scope", scope)
                .jwtID(UUID.randomUUID().toString())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        try {
            JWSSigner jwsSigner = new MACSigner(keySecret);
            jwsObject.sign(jwsSigner);
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }
    ;

    private String tranScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!user.getRoles().isEmpty()) {
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if (!role.getPermissions().isEmpty()) {
                    role.getPermissions().forEach(permission -> {
                        stringJoiner.add(permission.getName());
                    });
                }
            });
            return stringJoiner.toString();
        }
        return "";
    }
    ;
}
