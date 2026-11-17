package com.assignment.alumni.client;
import com.assignment.alumni.dto.AlumniSearchRequest;
import com.fasterxml.jackson.databind.*;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.util.*;
import com.fasterxml.jackson.databind.JsonNode;
@Component @Profile("mock")
public class MockAlumniSearchClient implements AlumniSearchClient {
 private final ObjectMapper mapper;
 public MockAlumniSearchClient(ObjectMapper mapper){this.mapper=mapper;}

 public List<JsonNode> search(AlumniSearchRequest r) {

  List<Map<String, Object>> rows = List.of(
          Map.of("name", "Aarav Sharma", "currentRole", "Software Engineer",
                  "university", r.university(), "location", "Bengaluru, India",
                  "linkedinHeadline", "Software Engineer | Java | Spring Boot",
                  "passoutYear", 2020, "linkedinUrl", "https://www.linkedin.com/in/demo-aarav"),

          Map.of("name", "Priya Nair", "currentRole", "Senior Software Engineer",
                  "university", r.university(), "location", "Hyderabad, India",
                  "linkedinHeadline", "Backend Developer | Java | Microservices",
                  "passoutYear", 2021, "linkedinUrl", "https://www.linkedin.com/in/demo-priya"),

          Map.of("name", "Rahul Verma", "currentRole", "Data Analyst",
                  "university", r.university(), "location", "Pune, India",
                  "linkedinHeadline", "Data Analyst | SQL | Power BI",
                  "passoutYear", 2019, "linkedinUrl", "https://www.linkedin.com/in/demo-rahul"),

          Map.of("name", "Sneha Rao", "currentRole", "Software Engineer",
                  "university", r.university(), "location", "Chennai, India",
                  "linkedinHeadline", "Software Engineer | APIs",
                  "passoutYear", 2020, "linkedinUrl", "https://www.linkedin.com/in/demo-sneha")
  );

  return rows.stream()
          .filter(x ->
                  x.get("currentRole").toString().toLowerCase()
                          .contains(r.designation().toLowerCase())
                          || r.designation().equalsIgnoreCase("engineer")
          )
          .filter(x ->
                  r.passoutYear() == null ||
                          Objects.equals(x.get("passoutYear"), r.passoutYear())
          )
          .map(x -> (JsonNode) mapper.valueToTree(x))
          .toList();
 }

}