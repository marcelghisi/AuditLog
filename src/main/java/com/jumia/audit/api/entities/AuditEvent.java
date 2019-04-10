package com.jumia.audit.api.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import com.jumia.audit.api.cryptograph.AESCryptoServiceImpl;
import com.jumia.audit.api.cryptograph.CryptographService;

@Entity
@Table(name="audit_event")
public class AuditEvent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5693228051572200752L;


	private Long id;

	@NotNull
	private Date date;

	@NotNull
	private String user;
	
	@NotNull
	private String event;

	@NotNull
	private String origin;

	private String pci;
	
	private String pii;
	
	public AuditEvent() {

	}

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@DateTimeFormat
	@Column(name="event_date",nullable=false)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name="username",nullable=false)
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Column(name="event",nullable=false)
	public String getEvent() {
		return event;
	}
	
	public void setEvent(String event) {
		this.event = event;
	}

	@Column(name="origin",nullable=false)
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	@Column(name="pci",nullable=true)
	public String getPCI() {
		return pci;
	}

	public void setPCI(String pci) {
		this.pci = pci;
	}
	
	@Column(name="pii",nullable=true)
	public String getPII() {
		return pii;
	}

	public void setPII(String pii) {
		this.pii = pii;
	}
	
	
	@Override
	public String toString() {
		return "AuditEvent [id=" + id + ", date=" + date + ", user=" + user + ", event=" + event + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuditEvent other = (AuditEvent) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
	
	
}
