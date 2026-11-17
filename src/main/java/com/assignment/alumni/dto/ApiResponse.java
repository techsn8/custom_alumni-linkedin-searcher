package com.assignment.alumni.dto;
public record ApiResponse<T>(String status,T data,String message) {
 public static <T> ApiResponse<T> success(T d){return new ApiResponse<>("success",d,null);}
}