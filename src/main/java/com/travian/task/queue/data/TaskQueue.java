package com.travian.task.queue.data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import com.travian.task.queue.request.TaskRequest;

public class TaskQueue {
	
	
	private TaskQueue() {
		
	}
	
	private static TaskQueue instance;
	private static final Map<String, Queue<TaskRequest>> tasks = new HashMap<String, Queue<TaskRequest>>();
	
	public static TaskQueue getInstance() {
		if(instance==null) {
			instance = new TaskQueue();
		}
		return instance;
	}
	
	
	public void addTask(String villageId, TaskRequest task) {
		Queue<TaskRequest> taskQueue = null;
		if(tasks.containsKey(villageId)) {
			taskQueue = tasks.get(villageId);
		}else {
			taskQueue = new LinkedList<TaskRequest>();
		}
		taskQueue.add(task);
		tasks.put(villageId, taskQueue);
	}
	
	public TaskRequest getTask(String villageId) {
		if(tasks.containsKey(villageId)) {
			return tasks.get(villageId).peek();
		}else {
			return null;
		}
	}
	
	public TaskRequest pollTask(String villageId) {
		if(tasks.containsKey(villageId)) {
			return tasks.get(villageId).poll();
		}else {
			return null;
		}
	}
	
	public Queue<TaskRequest> getAllTask(String villageId){
		return tasks.get(villageId);
	}
	
	

}
