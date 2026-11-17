package com.assignment.alumni.entity;
import jakarta.persistence.*;
import jakarta.persistence.Column;
@Entity @Table(name="alumni_profiles")
public class AlumniProfile {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false)
 private String name;
 @Column(name = "job_title")
 private String currentRole;
 @Column(nullable=false) private String university;
 private String location;
 @Column(length=1000) private String linkedinHeadline;
 private Integer passoutYear;
 @Column(length=1000, unique=true) private String linkedinUrl;
 protected AlumniProfile() {}
 public AlumniProfile(String name,String role,String university,String location,String headline,Integer year,String url) {
  this.name=name; this.currentRole=role; this.university=university; this.location=location;
  this.linkedinHeadline=headline; this.passoutYear=year; this.linkedinUrl=url;
 }
 public Long getId(){return id;} public String getName(){return name;}
 public String getCurrentRole(){return currentRole;} public String getUniversity(){return university;}
 public String getLocation(){return location;} public String getLinkedinHeadline(){return linkedinHeadline;}
 public Integer getPassoutYear(){return passoutYear;} public String getLinkedinUrl(){return linkedinUrl;}
}