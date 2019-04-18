package com.travian.task.queue.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "TRAINING_TASK")
@Data
@ToString
public class TrainingEntity implements Serializable{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="TASK_SEQ")
	private int taskSeq;
	@Column(name="TASK_ID")
	private String taskId;
	@Column(name="USERID")
	private String userId;
	@Column(name="VILLAGE_ID")
	private int villageId;
	@Column(name="TROOP_TYPE")
	private String troopType;
	@Column(name="STATUS")
	private String status;
	@Column(name="TRAIN_BUILDING")
	private String trainBuilding;
	@Column(name="COUNT")
	private int count;
	@Column(name="TARGET_COUNT")
	private int targetCount;
	@Column(name="BUILDING_LINK")
	private String link;

}
