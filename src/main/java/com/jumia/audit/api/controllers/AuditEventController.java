package com.jumia.audit.api.controllers;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jumia.audit.api.entities.AuditEvent;
import com.jumia.audit.api.response.Response;
import com.jumia.audit.api.services.AuditEventService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuditEventController {

	@Autowired
	AuditEventService auditEventService;
	
	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;
	
	@PostMapping("/audits")
	public ResponseEntity<Response<AuditEvent>>  createAudit(@Valid @RequestBody AuditEvent audit) {
		Response<AuditEvent> response = new Response<AuditEvent>();
		AuditEvent createdAudit = this.auditEventService.create(audit);
		response.setData(createdAudit);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/audits/{id}")
	public ResponseEntity<Response<AuditEvent>>  createAudit(
			@PathVariable("id") long id,
			@RequestParam(value = "admin", defaultValue = "") String admin,
			@RequestParam(value = "password", defaultValue = "") String password) {
		Response<AuditEvent> response = new Response<AuditEvent>();
		
		AuditEvent foundAuditEvent = null;
		//TODO Decide with team or po the best way to autenticate auditor
		if(StringUtils.isNotEmpty(admin) && StringUtils.isNotEmpty(password)) {
			foundAuditEvent = this.auditEventService.getById(id,true);
		} else {
			foundAuditEvent = this.auditEventService.getById(id,false);
		}
		response.setData(foundAuditEvent);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/audits/filter/user/{user}")
	public ResponseEntity<Response<Page<AuditEvent>>> listAuditsByUser(
			@PathVariable("user") String user,
			@RequestParam(value = "pag", defaultValue = "0") int page,
			@RequestParam(value = "ord" ,defaultValue = "id") String order,
			@RequestParam(value = "dir" ,defaultValue = "DESC") String direction){
		
			Response<Page<AuditEvent>> response = new Response<Page<AuditEvent>>();
			
			PageRequest pageRequest = new PageRequest(page, this.qtdPorPagina,Direction.valueOf(direction),order);
			Page<AuditEvent> lancs = this.auditEventService.getByUser(user, pageRequest);

			response.setData(lancs);
			
			return ResponseEntity.ok(response);
		
	}
	
	@GetMapping(value = "/audits/filter/type/{type}")
	public ResponseEntity<Response<Page<AuditEvent>>> listAuditsByTypeUser(
			@PathVariable("type") String type,
			@RequestParam(value = "pag", defaultValue = "0") int page,
			@RequestParam(value = "ord" ,defaultValue = "id") String order,
			@RequestParam(value = "dir" ,defaultValue = "DESC") String direction){
		
			Response<Page<AuditEvent>> response = new Response<Page<AuditEvent>>();
			
			PageRequest pageRequest = new PageRequest(page, this.qtdPorPagina,Direction.valueOf(direction),order);
			Page<AuditEvent> lancs = this.auditEventService.getByUserType(type, pageRequest);

			response.setData(lancs);
			
			return ResponseEntity.ok(response);
		
	}
	
	@GetMapping(value = "/audits")
	public ResponseEntity<Response<Page<AuditEvent>>> listAllAudits(
			@RequestParam(value = "pag", defaultValue = "0") int page,
			@RequestParam(value = "ord" ,defaultValue = "id") String order,
			@RequestParam(value = "dir" ,defaultValue = "DESC") String direction){
		
			Response<Page<AuditEvent>> response = new Response<Page<AuditEvent>>();
			
			PageRequest pageRequest = new PageRequest(page, this.qtdPorPagina,Direction.valueOf(direction),order);
			Page<AuditEvent> lancs = this.auditEventService.getAll(pageRequest);

			response.setData(lancs);
			
			return ResponseEntity.ok(response);
		
	}
	
}
