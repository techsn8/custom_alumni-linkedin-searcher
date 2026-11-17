package com.assignment.alumni.controller;
import com.assignment.alumni.dto.*;
import com.assignment.alumni.service.AlumniService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/alumni")
public class AlumniController {
 private final AlumniService service; public AlumniController(AlumniService s){service=s;}
 @PostMapping("/search") public ApiResponse<List<AlumniProfileResponse>> search(@Valid @RequestBody AlumniSearchRequest r){return ApiResponse.success(service.search(r));}
 @GetMapping("/all") public ApiResponse<List<AlumniProfileResponse>> all(){return ApiResponse.success(service.all());}
 @GetMapping("/{id}") public ApiResponse<AlumniProfileResponse> one(@PathVariable Long id){return ApiResponse.success(service.one(id));}
 @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){service.delete(id);return ResponseEntity.noContent().build();}
}