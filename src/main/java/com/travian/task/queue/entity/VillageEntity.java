package com.travian.task.queue.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "VILLAGES")
@Data
public class VillageEntity implements Serializable{

	@EmbeddedId
    private VillageKey primKey;
	
	@Column(name="VILLAGE_X_CORD")
	private int xCord;
	@Column(name="VILLAGE_Y_CORD")
	private int yCord;
	@Column(name="VILLAGE_NAME")
	private String villageName;
}
