package com.jumia.audit.api.builders;

import java.util.Date;

import com.jumia.audit.api.entities.AuditEvent;


public class AuditEventBuilder {

	private AuditEvent event;

	public AuditEventBuilder() {
		
	}
	
	public static AuditEventBuilder jsonEvent(String event,String user,String origin) {
		AuditEventBuilder aeb = new AuditEventBuilder();
		
		AuditEvent aev = new AuditEvent();
		aev.setDate(new Date());
		aev.setEvent(event);
		aev.setUser(user);
		aev.setOrigin(origin);
		
		aeb.event = aev;
		
		return aeb;
	}
	
	public static AuditEventBuilder createEvent(long id,String event,String user,String origin) {
		AuditEventBuilder aeb = new AuditEventBuilder();
		
		AuditEvent aev = new AuditEvent();
		aev.setDate(new Date());
		aev.setId(id);
		aev.setEvent(event);
		aev.setUser(user);
		aev.setOrigin(origin);
		
		aeb.event = aev;
		
		return aeb;
	}
	
	public AuditEventBuilder addPII(String pii) {
		this.event.setPII(pii);
		return this;
	}
	
	public AuditEventBuilder addPCI(String pci) {
		this.event.setPCI(pci);
		return this;
	}

	
	public AuditEvent build() {
		return event;
	}


	
}
