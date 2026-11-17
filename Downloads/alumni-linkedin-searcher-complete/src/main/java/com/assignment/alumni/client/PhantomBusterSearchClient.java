package com.assignment.alumni.client;
import com.assignment.alumni.dto.AlumniSearchRequest;
import com.fasterxml.jackson.databind.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.util.*;
@Component @Profile("phantombuster")
public class PhantomBusterSearchClient implements AlumniSearchClient {
 private final RestClient http; private final ObjectMapper mapper; private final String key,agentId;
 public PhantomBusterSearchClient(@Value("${phantombuster.base-url}") String base,
  @Value("${phantombuster.api-key:}") String key,@Value("${phantombuster.agent-id:}") String agentId,ObjectMapper mapper){
  this.http=RestClient.builder().baseUrl(base).build(); this.key=key; this.agentId=agentId; this.mapper=mapper;
 }
 public List<JsonNode> search(AlumniSearchRequest r) {
  if(key.isBlank()||agentId.isBlank()) throw new IllegalStateException("Set PHANTOMBUSTER_API_KEY and PHANTOMBUSTER_AGENT_ID.");
  Map<String,Object> args=new LinkedHashMap<>(); args.put("university",r.university());
  args.put("designation",r.designation()); args.put("passoutYear",r.passoutYear()==null?"":r.passoutYear());
  JsonNode launch=http.post().uri("/agents/launch").header("X-Phantombuster-Key",key)
   .contentType(MediaType.APPLICATION_JSON).body(Map.of("id",agentId,"argument",args)).retrieve().body(JsonNode.class);
  if(launch==null||!launch.hasNonNull("containerId")) throw new IllegalStateException("Launch response has no containerId.");
  String container=launch.get("containerId").asText();
  JsonNode result=http.get().uri(u->u.path("/containers/fetch-result-object").queryParam("id",container).build())
   .header("X-Phantombuster-Key",key).retrieve().body(JsonNode.class);
  return rows(result);
 }
 private List<JsonNode> rows(JsonNode root) {
  if(root==null) return List.of();
  JsonNode n=root.has("resultObject")?root.get("resultObject"):root;
  if(n.isTextual()){try{n=mapper.readTree(n.asText());}catch(Exception e){throw new IllegalStateException("Phantom result is not valid JSON.");}}
  if(n.isArray()){List<JsonNode> a=new ArrayList<>();n.forEach(a::add);return a;}
  for(String k:List.of("data","results","profiles","leads")) if(n.has(k)&&n.get(k).isArray()){
   List<JsonNode>a=new ArrayList<>();n.get(k).forEach(a::add);return a;
  }
  throw new IllegalStateException("Unknown Phantom output format. Adapt rows() to your Phantom's result JSON.");
 }
}