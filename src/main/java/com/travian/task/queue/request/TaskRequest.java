package com.travian.task.queue.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonInclude(Include.NON_NULL)
public class TaskRequest implements Serializable{
	
	private String villageId;
	private String userId;
	private String path;
	private int level;
	private int id;
	private TaskType taskType;
	private String taskId;

}
