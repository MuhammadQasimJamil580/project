package SMS_Shared.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    public ResponseEntity<Object> handleNotFoundException(NotFoundException notFoundException)
    {
        ExceptionDetail exceptionDetail=new ExceptionDetail(notFoundException.getMessage(),
                notFoundException.getCause(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(exceptionDetail,HttpStatus.NOT_FOUND);

    }
}
