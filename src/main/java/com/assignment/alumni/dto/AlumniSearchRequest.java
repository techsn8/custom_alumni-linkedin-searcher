package com.assignment.alumni.dto;
import jakarta.validation.constraints.*;
public record AlumniSearchRequest(
 @NotBlank @Size(max=250) String university,
 @NotBlank @Size(max=250) String designation,
 @Min(1950) @Max(2100) Integer passoutYear) {}