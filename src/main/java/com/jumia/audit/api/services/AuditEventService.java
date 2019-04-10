package com.jumia.audit.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.jumia.audit.api.entities.AuditEvent;

public interface AuditEventService {

	public AuditEvent create(AuditEvent auditEvent);

	public AuditEvent getById(long id,boolean decrypt);

	public Page<AuditEvent> getByUser(String user, PageRequest pageRequest);
	
	public Page<AuditEvent> getByUserType(String type, PageRequest pageRequest);

	public Page<AuditEvent> getAll(PageRequest pageRequest);
	
	
}
