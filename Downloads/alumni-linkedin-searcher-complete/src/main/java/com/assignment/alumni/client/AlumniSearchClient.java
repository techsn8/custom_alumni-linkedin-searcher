package com.assignment.alumni.client;
import com.assignment.alumni.dto.AlumniSearchRequest;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
public interface AlumniSearchClient { List<JsonNode> search(AlumniSearchRequest request); }