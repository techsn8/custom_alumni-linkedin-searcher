package com.assignment.alumni.exception;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import java.util.*;
import java.util.stream.Collectors;
@RestControllerAdvice
public class GlobalExceptionHandler {
 @ExceptionHandler(MethodArgumentNotValidException.class) public ResponseEntity<?> validation(MethodArgumentNotValidException e){
  String m=e.getBindingResult().getFieldErrors().stream().map(x->x.getField()+": "+x.getDefaultMessage()).collect(Collectors.joining("; "));
  return ResponseEntity.badRequest().body(Map.of("status","error","message",m));
 }
 @ExceptionHandler(NoSuchElementException.class) public ResponseEntity<?> missing(RuntimeException e){return ResponseEntity.status(404).body(Map.of("status","error","message",e.getMessage()));}
 @ExceptionHandler({IllegalStateException.class,RestClientException.class}) public ResponseEntity<?> integration(RuntimeException e){return ResponseEntity.status(502).body(Map.of("status","error","message",e.getMessage()));}
 @ExceptionHandler(Exception.class) public ResponseEntity<?> other(Exception e){return ResponseEntity.status(500).body(Map.of("status","error","message","Unexpected server error."));}
}