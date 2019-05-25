package com.travian.task.queue.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "ACCOUNT_PREFERENCE")
@Data
public class AccountPreferenceEntity implements Serializable{
	

	@Id
	@Column(name="PREF_SEQ")
	private int seq;
	
	
	@Column(name="USERID")
	private String userId;
	
	@Column(name="VALUE")
	private String value;
	
	@Column(name="PREFERENCE")
	private String preference;

}
