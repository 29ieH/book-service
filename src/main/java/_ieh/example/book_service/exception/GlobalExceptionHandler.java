package _ieh.example.book_service.exception;

import java.net.BindException;
import java.util.HashMap;
import java.util.Map;

import jakarta.validation.ConstraintViolation;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import _ieh.example.book_service.api.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    static final String MIN_ATTRIBUTES = "min";

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<ApiResponse> HandleException(Exception e) {
        ApiResponse response = new ApiResponse();
        response.setCode(ErrorCode.ERRORCODE.getCode());
        response.setMessage(ErrorCode.ERRORCODE.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> HandleValidOrValied(MethodArgumentNotValidException e) {
        ApiResponse<Map<String, String>> response = new ApiResponse<>();
        Map<String, String> payload = new HashMap<>();
        response.setCode(ErrorCode.ARGUMENTNOTVALID.getCode());
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String field = ((FieldError) error).getField();
            String errorMess =
                    replaceAttributes(error.getDefaultMessage() != null ? error.getDefaultMessage() : "", error);
            payload.put(field, errorMess);
        });
        response.setMessage(ErrorCode.ARGUMENTNOTVALID.getMessage());
        response.setPayload(payload);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<ApiResponse<?>> handlingAccessDeniedException() {
        ErrorCode errorCode = ErrorCode.UNAUTHORIATED;
        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.status(errorCode.getHttpStatus()).body(apiResponse);
    }
    ;

    @ExceptionHandler(AppException.class)
    ResponseEntity<ApiResponse> HandleAuthorNameExisted(AppException e) {
        ErrorCode errorCode = e.getErrorCode();
        ApiResponse response = new ApiResponse();
        response.setCode(errorCode.getCode());
        response.setMessage(e.getMessage());
        return ResponseEntity.status(errorCode.getHttpStatus()).body(response);
    }
    ;
    @ExceptionHandler(BindException.class)
    ResponseEntity<String> HandleBindException(BindException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    ResponseEntity<ApiResponse> HandleRequestMethodNotSupport(HttpRequestMethodNotSupportedException e) {
        ApiResponse response = new ApiResponse();
        response.setCode(ErrorCode.NOTFOUND.getCode());
        response.setMessage(ErrorCode.NOTFOUND.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    private String replaceAttributes(String message, ObjectError error) {
        Map<String, Object> atrributes = error.unwrap(ConstraintViolation.class)
                .getConstraintDescriptor()
                .getAttributes();
        String minValue = String.valueOf(atrributes.get(MIN_ATTRIBUTES));
        return message.replace("{" + MIN_ATTRIBUTES + "}", minValue);
    }
}
