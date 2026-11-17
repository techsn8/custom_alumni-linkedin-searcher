package com.assignment.alumni;
import com.assignment.alumni.client.AlumniSearchClient;
import com.assignment.alumni.dto.AlumniSearchRequest;
import com.assignment.alumni.repository.AlumniRepository;
import com.assignment.alumni.service.AlumniService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class AlumniServiceTest {
 @Mock AlumniRepository repo; @Mock AlumniSearchClient client; @InjectMocks AlumniService service;
 @Test void savesValidReturnedProfile(){
  ObjectNode n=new ObjectMapper().createObjectNode();n.put("name","Test User");n.put("currentRole","Developer");
  when(client.search(any())).thenReturn(List.of(n));when(repo.save(any())).thenAnswer(i->i.getArgument(0));
  var result=service.search(new AlumniSearchRequest("Test University","Developer",2022));
  assertEquals(1,result.size());assertEquals("Test User",result.get(0).name());verify(repo).save(any());
 }
 @Test void ignoresRowsWithoutName(){
  ObjectNode n=new ObjectMapper().createObjectNode();n.put("headline","No name");
  when(client.search(any())).thenReturn(List.of(n));
  assertTrue(service.search(new AlumniSearchRequest("Test University","Developer",null)).isEmpty());
  verify(repo,never()).save(any());
 }
}