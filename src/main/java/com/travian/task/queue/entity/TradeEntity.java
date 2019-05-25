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
@Table(name = "RESOURCE_TRANSFER")
@Data
@ToString
public class TradeEntity implements Serializable{
	
	@Column(name="USERID")
	private String userId;
	@Id
    @Column(name="TRANSACTION_ID")
	private String transactionId;
	@Column(name="SOURCE_VILLAGE_ID")
	private String sourceVillageId;
	@Column(name="DEST_VILLAGE_NAME")
	private String destVillageName;
	@Column(name="WOOD")
	private int wood;
	@Column(name="CLAY")
	private int clay;
	@Column(name="IRON")
	private int iron;
	@Column(name="CROP")
	private int crop;
	@Column(name="TRANSFER_INTERVAL")
	private int interval;
	@Column(name="LAST_UPDATE")
	private String lastUpdate;
	

}
