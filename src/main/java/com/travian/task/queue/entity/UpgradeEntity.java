package com.travian.task.queue.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "UPGRADE_TASKS")
@Data
public class UpgradeEntity implements Serializable{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="TASK_SEQ")
	private int taskSeq;
	@Column(name="TASK_ID")
	private String taskId;
	@Column(name="LEVEL")
	private int level;
	@Column(name="UPGRADE_ID")
	private int upgradeId;
	@Column(name="VILLAGE_ID")
	private int villageId;
	@Column(name="TASK_TYPE")
	private String taskType;
	@Column(name="STATUS")
	private String status;
	

}
