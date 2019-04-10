package com.jumia.audit.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.jumia.audit.api.cryptograph.CryptographService;
import com.jumia.audit.api.entities.AuditEvent;
import com.jumia.audit.api.repositories.AuditEventRepository;

@Service
public class AuditEventServiceImpl implements AuditEventService {

	@Autowired
	private AuditEventRepository auditEventRepossitory;
	
	@Autowired
	private CryptographService cryptoService;
	
	@Override
	public AuditEvent create(AuditEvent auditEvent) {
		
		
		try {
			cryptoService.encrypt(auditEvent, "pci");
			cryptoService.encrypt(auditEvent, "pii");

		} catch (Exception e) {
		}
		
		AuditEvent inserted = auditEventRepossitory.save(auditEvent);
		return inserted;
	}

	@Override
	public AuditEvent getById(long id,boolean decrypt) {
		AuditEvent ae = auditEventRepossitory.findById(id).get();
		if (decrypt) {
			//TODO Validate user to decrypt and study another validation strategy..for while just testing decrypt
			try {
				cryptoService.decrypt(ae, "pci");
				cryptoService.decrypt(ae, "pii");
			} catch (Exception e) {
			}
		}
		return ae;
	}

	@Override
	public Page<AuditEvent> getByUser(String user, PageRequest pageRequest) {
		return auditEventRepossitory.findByUser(user, pageRequest);
	}
	
	@Override
	public Page<AuditEvent> getByUserType(String type, PageRequest pageRequest) {
		return auditEventRepossitory.findByOrigin(type, pageRequest);
	}

	@Override
	public Page<AuditEvent> getAll(PageRequest pageRequest) {
		return auditEventRepossitory.findAll(pageRequest);
	}

}
