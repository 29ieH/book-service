package _ieh.example.book_service.exception;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    AUTHORNAME_EXISTED(1001, "Author name is available", HttpStatus.BAD_REQUEST),
    ARGUMENTNOTVALID(1002, "Argument not valid", HttpStatus.BAD_REQUEST),
    ERRORCODE(9999, "ERROR CODE", HttpStatus.INTERNAL_SERVER_ERROR),
    VALIDAUTHORNAME(1003, "Name must be least then 3 characters", HttpStatus.BAD_REQUEST),
    VALIDDESCRIPTION(1004, "Description is required", HttpStatus.BAD_REQUEST),
    AUTHOR_ISNOTAVAILABEL(1005, "Author is not available", HttpStatus.NOT_FOUND),
    NOTFOUND(404, "Not found", HttpStatus.NOT_FOUND),
    USERNAME_EXISTED(1006, "User name is available", HttpStatus.BAD_REQUEST),
    USER_ISNOTAVAILABLE(1007, "User is not available", HttpStatus.NOT_FOUND),
    ROLE_ISNOTAVAILABLE(1010, "Role is not availabel", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1008, "UnAuthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIATED(1009, "You do not have permission", HttpStatus.FORBIDDEN),
    ;
    int code;
    String message;
    HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
