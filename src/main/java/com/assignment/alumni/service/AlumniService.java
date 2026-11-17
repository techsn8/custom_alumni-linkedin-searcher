package com.assignment.alumni.service;
import com.assignment.alumni.client.AlumniSearchClient;
import com.assignment.alumni.dto.*;
import com.assignment.alumni.entity.AlumniProfile;
import com.assignment.alumni.repository.AlumniRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Service
public class AlumniService {
 private final AlumniRepository repo; private final AlumniSearchClient client;
 public AlumniService(AlumniRepository repo,AlumniSearchClient client){this.repo=repo;this.client=client;}
 @Transactional public List<AlumniProfileResponse> search(AlumniSearchRequest req){
  List<AlumniProfileResponse> out=new ArrayList<>();
  for(JsonNode n:client.search(req)){
   String name=text(n,"name","fullName","firstName"); if(name==null) continue;
   String url=text(n,"linkedinUrl","profileUrl","url");
   if(url!=null&&repo.findByLinkedinUrl(url).isPresent()) continue;
   String role=first(text(n,"currentRole","title","occupation","jobTitle"),req.designation());
   String uni=first(text(n,"university","school"),req.university());
   Integer year=integer(n,"passoutYear","graduationYear","year"); if(year==null)year=req.passoutYear();
   out.add(AlumniProfileResponse.from(repo.save(new AlumniProfile(name,role,uni,text(n,"location","geo"),
    text(n,"linkedinHeadline","headline"),year,url))));
  } return out;
 }
 @Transactional(readOnly=true) public List<AlumniProfileResponse> all(){return repo.findAll().stream().map(AlumniProfileResponse::from).toList();}
 @Transactional(readOnly=true) public AlumniProfileResponse one(Long id){return repo.findById(id).map(AlumniProfileResponse::from).orElseThrow(()->new NoSuchElementException("Alumni not found: "+id));}
 @Transactional public void delete(Long id){if(!repo.existsById(id))throw new NoSuchElementException("Alumni not found: "+id);repo.deleteById(id);}
 private String text(JsonNode n,String...keys){for(String k:keys)if(n.hasNonNull(k)&&!n.get(k).asText().isBlank())return n.get(k).asText();return null;}
 private String first(String a,String b){return a==null?b:a;}
 private Integer integer(JsonNode n,String...keys){for(String k:keys)if(n.hasNonNull(k)){try{return n.get(k).isNumber()?n.get(k).intValue():Integer.valueOf(n.get(k).asText());}catch(Exception ignored){return null;}}return null;}
}