package com.travian.task.queue.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class PreferenceKey implements Serializable{
	@Column(name="USERID", nullable = false)
	private String userId;
	@Column(name="VILLAGEID", nullable = false)
	private int villageId;
	@Column(name="PREFERENCE", nullable = false)
	private String preference;

}
