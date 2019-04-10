package com.jumia.audit.api.test.services;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jumia.audit.api.builders.AuditEventBuilder;
import com.jumia.audit.api.entities.AuditEvent;
import com.jumia.audit.api.repositories.AuditEventRepository;
import com.jumia.audit.api.services.AuditEventService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ServicesAuditLogsTest {

	@MockBean
	AuditEventRepository auditRepository;
	
	@Autowired
	AuditEventService service;
	
	@Before
	public void setUp() {
		BDDMockito.given(this.auditRepository.findByUser(Mockito.anyString(), Mockito.any(PageRequest.class))).willReturn(new PageImpl<AuditEvent>(new ArrayList<AuditEvent>()));
		BDDMockito.given(this.auditRepository.findAll(Mockito.any(PageRequest.class))).willReturn(new PageImpl<AuditEvent>(new ArrayList<AuditEvent>()));
		BDDMockito.given(this.auditRepository.save(Mockito.any(AuditEvent.class))).willReturn(new AuditEvent());
		BDDMockito.given(this.auditRepository.findById(Mockito.anyLong())).willReturn(Optional.of(new AuditEvent()));
	}
	
	@Test
	public void testSaveNewAuditEvent() {
		
		AuditEvent aE = AuditEventBuilder.jsonEvent("Test service test", "User1","User").build();
		AuditEvent insertedaE = this.service.create(aE);
		
		assertNotNull(insertedaE);
		
	}
	
	@Test
	public void testListById() {
		AuditEvent aE = AuditEventBuilder.createEvent(1, "Test list one", "User2","Consumer").build();
		AuditEvent listedE = this.service.getById(aE.getId(),false);
		
		assertNotNull(listedE);
	}
	
	@Test
	public void testListAll() {
		
		Page<AuditEvent> listedE = this.service.getAll(new PageRequest(0, 2));
		
		assertNotNull(listedE);
	}
	
	@Test
	public void testListByUser() {
		
		Page<AuditEvent> listedE = this.service.getByUser("User1", new PageRequest(0, 2));
		
		assertNotNull(listedE);
	}
	
}
