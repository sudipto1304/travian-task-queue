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
	

	@Column(name="VALUE")
	private String value;
	@Id
	@Column(name="USERID")
	private String userId;
	@Column(name="PREFERENCE")
	private String preference;

}
