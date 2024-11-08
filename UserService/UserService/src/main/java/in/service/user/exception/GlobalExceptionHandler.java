package in.service.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundExceptoin(ResourceNotFoundException ex){
        Map<String, Object> error= new HashMap<>();
        error.put("status", HttpStatus.NOT_FOUND);
        error.put("message",ex.getMessage());
        error.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}