package com.jumia.audit.api.test.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumia.audit.api.builders.AuditEventBuilder;
import com.jumia.audit.api.entities.AuditEvent;
import com.jumia.audit.api.services.AuditEventService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure=false)
//@ActiveProfiles("test")
public class CreateAuditLogsTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	AuditEventService auditEventService;

	private static final String URL_BASE = "/api/audits";
	
	@Test
	public void testNewLogEventAuditCreate() throws JsonProcessingException, Exception {
		
		AuditEvent auditEvent = AuditEventBuilder.createEvent(1,"Gogged as u1","User1","Consumer").build();
		
		BDDMockito.given(this.auditEventService.create(Mockito.any(AuditEvent.class))).willReturn(auditEvent);
		
		mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
				.content(createJsonTotest())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andDo(print())
		.andExpect(jsonPath("$.data.id").value(auditEvent.getId()))
		.andExpect(jsonPath("$.data.user").value(auditEvent.getUser()))
		.andExpect(jsonPath("$.data.event").value(auditEvent.getEvent()));
		
	}
	
	@Test
	public void testNewLogEventForPCICreate() throws JsonProcessingException, Exception {
		
		AuditEvent auditEvent = AuditEventBuilder.createEvent(1,"Gogged as u1","User1","Consumer").addPCI("Card Number: 01245 validade 01-02").build();
		
		BDDMockito.given(this.auditEventService.create(Mockito.any(AuditEvent.class))).willReturn(auditEvent);
		
		mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
				.content(createJsonTotest())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andDo(print())
		.andExpect(jsonPath("$.data.id").value(auditEvent.getId()))
		.andExpect(jsonPath("$.data.user").value(auditEvent.getUser()))
		.andExpect(jsonPath("$.data.event").value(auditEvent.getEvent()));
		
	}
	
	@Test
	public void testGetOneEventByResourceId() throws Exception {
		
		AuditEvent auditEvent = AuditEventBuilder.createEvent(1, "Logged as 1", "User1","Consumer").build();
		
		BDDMockito.given(this.auditEventService.getById(Mockito.anyLong(),Mockito.anyBoolean())).willReturn(auditEvent);
		
		mvc.perform(MockMvcRequestBuilders.get(URL_BASE+"/1")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
		
	}
	
	@Test
	public void testGetOneEventDecryptingPCI_PII_Data() throws Exception {
		
		AuditEvent auditEvent = AuditEventBuilder.createEvent(1, "Logged as 1", "User1","Consumer").build();
		
		BDDMockito.given(this.auditEventService.getById(Mockito.anyLong(),Mockito.eq(true))).willReturn(auditEvent);


		//If url with admin and password need to call given method with true as decrypt argument above
		mvc.perform(MockMvcRequestBuilders.get(URL_BASE+"/1/?admin=admin&password=123456")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(status().isOk());
		
	}
	
	@Test
	public void testGetAuditsByUserId() throws Exception {
		
		List<AuditEvent> audits = new ArrayList<AuditEvent>(0);
		
		audits.add(AuditEventBuilder.createEvent(1, "Logged as", "User1","Consumer").build());
		audits.add(AuditEventBuilder.createEvent(2, "Logged as", "User1","Consumer").build());
		
		Page<AuditEvent> page = new PageImpl(audits);
		
		BDDMockito.given(this.auditEventService.getByUser(Mockito.anyString(), Mockito.any(PageRequest.class))).willReturn(page);
		
		mvc.perform(MockMvcRequestBuilders.get(URL_BASE+"/filter/user/User1?pag=1")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(jsonPath("$.data.content.[0]").isNotEmpty())
				.andExpect(jsonPath("$.data.content.[1]").isNotEmpty())
				.andExpect(jsonPath("$.data.content.[0].id").value(1))
				.andExpect(jsonPath("$.data.content.[1].id").value(2))
				.andExpect(jsonPath("$.data.content.[0].user").value("User1"))
				.andExpect(jsonPath("$.data.content.[1].user").value("User1"))
				.andExpect(status().isOk());
		
	}
	
	@Test
	public void testGetAuditsByUserType() throws Exception {
		
		List<AuditEvent> audits = new ArrayList<AuditEvent>(0);
		
		audits.add(AuditEventBuilder.createEvent(1, "Logged as", "User1","Consumer").build());
		audits.add(AuditEventBuilder.createEvent(2, "Logged as", "User1","Consumer").build());
		
		Page<AuditEvent> page = new PageImpl(audits);
		
		BDDMockito.given(this.auditEventService.getByUserType(Mockito.anyString(), Mockito.any(PageRequest.class))).willReturn(page);
		
		mvc.perform(MockMvcRequestBuilders.get(URL_BASE+"/filter/type/Consumer?pag=1")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(jsonPath("$.data.content.[0]").isNotEmpty())
				.andExpect(jsonPath("$.data.content.[1]").isNotEmpty())
				.andExpect(jsonPath("$.data.content.[0].id").value(1))
				.andExpect(jsonPath("$.data.content.[1].id").value(2))
				.andExpect(jsonPath("$.data.content.[0].user").value("User1"))
				.andExpect(jsonPath("$.data.content.[1].user").value("User1"))
				.andExpect(status().isOk());
		
	}
	
	@Test
	public void testGetAllLogs() throws Exception {
		
		List<AuditEvent> audits = new ArrayList<AuditEvent>(0);
		
		audits.add(AuditEventBuilder.createEvent(1, "Logged as", "User1","Consumer").build());
		audits.add(AuditEventBuilder.createEvent(2, "Logged as", "User1","Consumer").build());
		audits.add(AuditEventBuilder.createEvent(3, "Invalid password", "User3","Consumer").build());
		audits.add(AuditEventBuilder.createEvent(4, "Logged as 3", "User3","Consumer").build());
		
		Page<AuditEvent> page = new PageImpl(audits);
		
		BDDMockito.given(this.auditEventService.getAll(Mockito.any(PageRequest.class))).willReturn(page);
		
		mvc.perform(MockMvcRequestBuilders.get(URL_BASE)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(jsonPath("$.data.content.[0]").isNotEmpty())
				.andExpect(jsonPath("$.data.content.[1]").isNotEmpty())
				.andExpect(jsonPath("$.data.content.[0].id").value(1))
				.andExpect(jsonPath("$.data.content.[1].id").value(2))
				.andExpect(jsonPath("$.data.content.[0].user").value("User1"))
				.andExpect(jsonPath("$.data.content.[1].user").value("User1"))
				.andExpect(jsonPath("$.data.content.[2].user").value("User3"))
				.andExpect(jsonPath("$.data.content.[3].user").value("User3"))
				.andExpect(jsonPath("$.data.content.length()",is(4)))
				.andExpect(status().isOk());
		
	}
	
	@Test
	public void testGetPage1() throws Exception {
		
		List<AuditEvent> audits = new ArrayList<AuditEvent>(0);
		
		audits.add(AuditEventBuilder.createEvent(1, "Logged as", "User1","Consumer").build());
		
		Page<AuditEvent> page = new PageImpl(audits);
		
		BDDMockito.given(this.auditEventService.getAll(Mockito.any(PageRequest.class))).willReturn(page);
		
		mvc.perform(MockMvcRequestBuilders.get(URL_BASE+"/?pag=1")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(jsonPath("$.data.content.length()",is(1)))
				.andExpect(status().isOk());
		
	}
	
	@Test
	public void testGetPage0() throws Exception {
		
		List<AuditEvent> audits = new ArrayList<AuditEvent>(0);
		
		audits.add(AuditEventBuilder.createEvent(1, "Logged as", "User1","Consumer").build());
		audits.add(AuditEventBuilder.createEvent(2, "Logged as", "User1","Consumer").build());
		
		Page<AuditEvent> page = new PageImpl(audits);
		
		BDDMockito.given(this.auditEventService.getAll(Mockito.any(PageRequest.class))).willReturn(page);
		
		mvc.perform(MockMvcRequestBuilders.get(URL_BASE+"/?pag=0&dir=ASC")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(jsonPath("$.data.content.length()",is(2)))
				.andExpect(status().isOk());
		
	}
	
	private String createJsonTotest() throws JsonProcessingException {
		AuditEvent auditEvent = AuditEventBuilder.jsonEvent("Logged as 1", "User1","Consumer").addPCI("Card Number: 01245 validade 01-02").build();
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(auditEvent);
		return json;
	}
}
