package SMS_Shared.Exception;

import jakarta.persistence.NonUniqueResultException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({HttpServerErrorException.InternalServerError.class})
    public ResponseEntity internalServerError(HttpServerErrorException exception, WebRequest webRequest) {
        final String ERROR_MESSAGE = "internal server error";
        logger.error(ERROR_MESSAGE, exception);
        Error errorResponse = getErrorResponse(HttpStatus.NOT_FOUND, ERROR_MESSAGE, webRequest.getDescription(true));
        return handleExceptionInternal(exception, errorResponse, new HttpHeaders(), HttpStatus.NOT_FOUND,
                webRequest);
    }

    @ExceptionHandler({HttpClientErrorException.NotFound.class})
    public ResponseEntity notFoundException(Exception exception, WebRequest webRequest) {
        final String ERROR_MESSAGE = "NotFound Exception";
        logger.error(ERROR_MESSAGE, exception);
        Error errorResponse = getErrorResponse(HttpStatus.NOT_FOUND, ERROR_MESSAGE, webRequest.getDescription(true));
        return handleExceptionInternal(exception, errorResponse, new HttpHeaders(), HttpStatus.NOT_FOUND,
                webRequest);
    }

    @ExceptionHandler({IllegalStateException.class})
    public ResponseEntity arrayIndexOutOfBoundsException1(Exception exception, WebRequest webRequest) {
        final String ERROR_MESSAGE = exception.getMessage();
        logger.error(ERROR_MESSAGE, exception);
        Error errorResponse = getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_MESSAGE, webRequest.getDescription(true));
        return handleExceptionInternal(exception, errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,
                webRequest);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity arrayIndexOutOfBoundsException(Exception exception, WebRequest webRequest) {
        final String ERROR_MESSAGE = exception.getMessage();
        logger.error(ERROR_MESSAGE, exception);
        Error errorResponse = getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_MESSAGE, webRequest.getDescription(true));
        return handleExceptionInternal(exception, errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,
                webRequest);
    }
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Object> handleCustomException(ServiceException exception, WebRequest webRequest) {
        Error errorResponse = getCustomErrorResponse(
                String.valueOf(exception.getStatus().getStatusCode()),
                exception.getDetail(),
                webRequest.getDescription(true),
                exception.getStatus().getReasonPhrase()
        );
        return handleExceptionInternal(exception, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST,
                webRequest);
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity handleNullPointerException(Exception exception, WebRequest webRequest) {
        final String ERROR_MESSAGE = "null pointer exception";
        logger.error(ERROR_MESSAGE, exception);
        Error errorResponse = getErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ERROR_MESSAGE,
                webRequest.getDescription(true)
        );
        return handleExceptionInternal(exception, errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,
                webRequest);
    }

    @ExceptionHandler({NonUniqueResultException.class})
    public ResponseEntity<Object> exception(NonUniqueResultException exception, WebRequest webRequest) {
        logger.error(exception.getMessage(), exception);
        Error errorResponse = getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Two Records Found Instead of One", webRequest.getDescription(true));
        return handleExceptionInternal(exception, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST,
                webRequest);
    }


    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    public ResponseEntity<Object> SQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException exception, WebRequest webRequest) {
        logger.error(exception.getMessage(), exception);
        Error errorResponse = getErrorResponse(HttpStatus.BAD_REQUEST, "Try To Add Duplicate Data In Unique Column", webRequest.getDescription(true));
        return handleExceptionInternal(exception, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST,
                webRequest);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> Exception(Exception exception, WebRequest webRequest) {
        logger.error(exception.getMessage(), exception);
        Error errorResponse = getErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), webRequest.getDescription(true));
        return handleExceptionInternal(exception, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST,
                webRequest);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> Exception(DataIntegrityViolationException exception, WebRequest webRequest) {
        logger.error(exception.getMessage(), exception);
        Error errorResponse = getErrorResponse(HttpStatus.BAD_REQUEST, "USERNAME ALREADY TAKEN", webRequest.getDescription(true));
        return handleExceptionInternal(exception, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST,
                webRequest);
    }

    @ExceptionHandler({InvalidDataAccessResourceUsageException.class})
    public ResponseEntity<Object> Exception(InvalidDataAccessResourceUsageException exception, WebRequest webRequest) {
        logger.error(exception.getMessage(), exception);
        Error errorResponse = getErrorResponse(HttpStatus.BAD_REQUEST, "Table Dosen't Exist", webRequest.getDescription(true));
        return handleExceptionInternal(exception, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST,
                webRequest);
    }

    protected Error getCustomErrorResponse(String status, String errorMessage, String path, String customError) {
        if (errorMessage.isEmpty()) {
            errorMessage = "An unexpected error occurred";
        }
        return new Error(customError, status, errorMessage, path);
    }

    protected Error getErrorResponse(HttpStatus status, String errorMessage, String path) {
        if (errorMessage.isEmpty()) {
            errorMessage = "An unexpected error occurred";
        }
        return new Error(status.name(), String.valueOf(status.value()), errorMessage, path);
    }

}