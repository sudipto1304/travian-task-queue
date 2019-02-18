package com.travian.task.queue.service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.travian.task.queue.data.TaskQueue;
import com.travian.task.queue.request.Status;
import com.travian.task.queue.request.TaskRequest;

@Service
public class TaskService {
	
	private static final Logger Log = LoggerFactory.getLogger(TaskService.class);

	
	public Status addTask(TaskRequest request) {
		String taskId = UUID.randomUUID().toString();
		request.setTaskId(taskId);
		TaskQueue.getInstance().addTask(request.getVillageId(), request);
		if(Log.isDebugEnabled())
			Log.debug(TaskQueue.getInstance().getAllTask(request.getVillageId())!=null?TaskQueue.getInstance().getAllTask(request.getVillageId()).toString():"");
		return new Status(taskId, 200);
	}
	
	public List<Status> addTasks(List<TaskRequest> request) {
		List<Status> status  = request.stream().map(p->addTask(p)).collect(Collectors.toList());
		return status;
	}
	
	public Status removeAllTasks(String villageId) {
		TaskQueue.getInstance().removeAllTaks(villageId);
		return new Status("SUCCESS", 200);
	}
	
	public TaskRequest getTask(String villageId) {
		if(Log.isDebugEnabled())
			Log.debug(TaskQueue.getInstance().getAllTask(villageId)!=null?TaskQueue.getInstance().getAllTask(villageId).toString():"");
		return TaskQueue.getInstance().getTask(villageId);
	}
	
	public LinkedList<TaskRequest> getAllTask(String villageId) {
		if(Log.isDebugEnabled())
			Log.debug(TaskQueue.getInstance().getAllTask(villageId)!=null?TaskQueue.getInstance().getAllTask(villageId).toString():"");
		return (LinkedList<TaskRequest>) TaskQueue.getInstance().getAllTask(villageId);
	}
	
	public Status completeTask(String villageId, String taskId) {
		TaskRequest removedTask = TaskQueue.getInstance().getTask(villageId);
		if(removedTask.getTaskId().equals(taskId)){
			if(Log.isDebugEnabled())
				Log.debug(TaskQueue.getInstance().getAllTask(villageId)!=null?TaskQueue.getInstance().getAllTask(villageId).toString():"");
			if(Log.isInfoEnabled())
				Log.info(":::Task Complete::"+removedTask);
			TaskQueue.getInstance().pollTask(villageId);
			return new Status("SUCCESS", 200);
		}else {
			if(Log.isDebugEnabled())
				Log.debug(TaskQueue.getInstance().getAllTask(villageId).toString());
			return new Status("FAILED::TASKID.NOT.MATCHED", 400);
		}
	}
	
	

}
