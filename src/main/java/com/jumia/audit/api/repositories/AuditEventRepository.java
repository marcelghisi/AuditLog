package com.jumia.audit.api.repositories;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.jumia.audit.api.entities.AuditEvent;

@Transactional(readOnly=false)
@NamedQueries({ 
                @NamedQuery( name="AuditEventRepository.findByUser",
                			query="SELECT audit AuditEvent audit WHERE audit.user = :user"),
                @NamedQuery( name="AuditEventRepository.findByUserType",
    			query="SELECT audit AuditEvent audit WHERE audit.origin = :origin")})

public interface AuditEventRepository extends JpaRepository<AuditEvent, Long>{
	
	AuditEvent save(AuditEvent auditEvent); 

	Page<AuditEvent> findAll(Pageable pageable);
	
	Page<AuditEvent> findByUser(@Param("user") String user,Pageable pageable);
	
	Page<AuditEvent> findByOrigin(@Param("origin") String origin,Pageable pageable);
}
