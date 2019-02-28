package com.travian.task.queue.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class VillageKey implements Serializable {
	
	@Column(name="USERID", nullable = false)
	private String userId;
	@Column(name="VILAGEID", nullable = false)
	private int villageId;

}
