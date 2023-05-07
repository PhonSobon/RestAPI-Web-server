package co.istad.moblie_banking.exception;

import co.istad.moblie_banking.base.BaseError;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ApiException {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResponseStatusException.class)
    public BaseError<?> handleServiceException(ResponseStatusException e){
        return BaseError.builder()
                .status(false)
                .code(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .message("Something went wrong ..! ,please check")
                .errors(e.getReason())
                .build()
                ;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseError<?> handleValidationException(MethodArgumentNotValidException e){
        List<Map<String,String>> errors = new ArrayList<>();
        for(FieldError error : e.getFieldErrors()){
            Map<String,String> errorDetails = new HashMap<>();
            errorDetails.put("name",error.getField());
            errorDetails.put("message",error.getDefaultMessage());
            errors.add(errorDetails);
        }
        return BaseError.builder()
                .status(false)
                .code(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .message("Validation is error , please check detail message!")
                .errors(errors)
                .build();
    }
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public BaseError<?> handleValidationException(MaxUploadSizeExceededException exception){
        return BaseError.builder()
                .status(false)
                .code(HttpStatus.PAYLOAD_TOO_LARGE.value())
                .timestamp(LocalDateTime.now())
                .message("Can you check file size .")
                .errors(exception.getMessage()+": 1MB ")
                .build();
    }
    @ExceptionHandler(RuntimeException.class)
    public BaseError<?> handleNoFileForDownload(RuntimeException exception){
        return BaseError.builder()
                .status(false)
                .code(HttpStatus.NOT_FOUND.ordinal())
                .timestamp(LocalDateTime.now())
                .message("File is not exited......")
                .errors(exception.getMessage())
                .build();
    }
    @ExceptionHandler(MultipartException.class)
    public BaseError<?> handleMultipart(MultipartException exception){
        return BaseError.builder()
                .status(false)
                .code(HttpStatus.NOT_FOUND.ordinal())
                .timestamp(LocalDateTime.now())
                .message("File is not exited.")
                .errors(exception.getMessage())
                .build();
    }
}
