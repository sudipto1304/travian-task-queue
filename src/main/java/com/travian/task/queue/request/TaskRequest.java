package com.travian.task.queue.request;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TaskRequest implements Serializable{
	
	private String villageId;
	private String path;
	private int level;
	private int id;
	private int buildingId;
	private TaskType taskType;
	private String taskId;

}
