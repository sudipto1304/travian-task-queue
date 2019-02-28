package com.travian.task.queue.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travian.task.queue.entity.TrainingEntity;
import com.travian.task.queue.entity.UpgradeEntity;
import com.travian.task.queue.repository.TraningRepository;
import com.travian.task.queue.repository.UpgradeRepository;
import com.travian.task.queue.request.Status;
import com.travian.task.queue.request.TaskRequest;
import com.travian.task.queue.request.TaskStatus;
import com.travian.task.queue.request.TaskType;
import com.travian.task.queue.request.TroopTrain;

@Service
public class TaskService {

	private static final Logger Log = LoggerFactory.getLogger(TaskService.class);

	@Autowired
	UpgradeRepository upgradeRepo;
	
	@Autowired
	TraningRepository traningRepo;

	public Status addTask(TaskRequest request) {
		String taskId = UUID.randomUUID().toString();
		request.setTaskId(taskId);
		UpgradeEntity upgradeEntity = new UpgradeEntity();
		upgradeEntity.setLevel(request.getLevel());
		upgradeEntity.setUserId(request.getUserId());
		upgradeEntity.setStatus(TaskStatus.OPEN.name());
		upgradeEntity.setTaskId(taskId);
		upgradeEntity.setTaskType(request.getTaskType().name());
		upgradeEntity.setUpgradeId(request.getId());
		upgradeEntity.setVillageId(Integer.valueOf(request.getVillageId()));
		upgradeRepo.save(upgradeEntity);
		return new Status(taskId, 200);
	}

	public List<Status> addTasks(List<TaskRequest> request) {
		List<UpgradeEntity> upgradeEntities = new ArrayList<>();
		List<Status> status = new ArrayList<>();
		request.forEach(e -> {
			String taskId = UUID.randomUUID().toString();
			UpgradeEntity upgradeEntity = new UpgradeEntity();
			upgradeEntity.setLevel(e.getLevel());
			upgradeEntity.setUserId(e.getUserId());
			upgradeEntity.setStatus(TaskStatus.OPEN.name());
			upgradeEntity.setTaskId(taskId);
			upgradeEntity.setTaskType(e.getTaskType().name());
			upgradeEntity.setUpgradeId(e.getId());
			upgradeEntity.setVillageId(Integer.valueOf(e.getVillageId()));
			upgradeEntities.add(upgradeEntity);
			status.add(new Status(taskId, 200));
		});
		upgradeRepo.saveAll(upgradeEntities);
		return status;
	}

	public Status removeAllTasks(String villageId) {
		upgradeRepo.deleteByVillageId(Integer.valueOf(villageId));
		return new Status("SUCCESS", 200);
	}

	public TaskRequest getTask(String villageId) {
		return getAllTask(villageId).stream().findFirst().orElse(null);
	}

	public List<TaskRequest> getAllTask(String villageId) {
		List<UpgradeEntity> tasks = upgradeRepo.findByVillageIdAndStatusOrderByTaskSeq(Integer.valueOf(villageId),
				TaskStatus.OPEN.name());
		List<TaskRequest> response = new ArrayList<>();
		tasks.forEach(e -> {
			TaskRequest task = new TaskRequest();
			task.setId(e.getUpgradeId());
			task.setLevel(e.getLevel());
			task.setTaskId(e.getTaskId());
			task.setTaskType(TaskType.valueOf(e.getTaskType()));
			task.setVillageId(String.valueOf(e.getVillageId()));
			response.add(task);
		});

		return response;
	}

	public Status completeTask(String villageId, String taskId) {
		Integer result = upgradeRepo.deleteByVillageIdAndTaskId(Integer.valueOf(villageId), taskId);
		if (result==1) {
			return new Status("SUCCESS", 200);
		}else {
			return new Status("TASK.NOT.FOUND", 400);
		}
		
	}
	
	public Status skipTask(String villageId, String taskId) {
		UpgradeEntity result = upgradeRepo.findByVillageIdAndTaskId(Integer.valueOf(villageId), taskId);
		result.setStatus(TaskStatus.SKIP.name());
		upgradeRepo.save(result);
		return new Status("SUCCESS", 200);
	}
	
	
	public Status trainTroop(TroopTrain request) {
		TrainingEntity training = new TrainingEntity();
		String taskId = UUID.randomUUID().toString();
		training.setTaskId(taskId);
		training.setUserId(request.getUserId());
		training.setStatus(TaskStatus.OPEN.name());
		training.setTargetCount(request.getTargetCount());
		training.setTrainBuilding(request.getBuilding());
		training.setTroopType(request.getTroopType());
		training.setVillageId(request.getVillageId());
		traningRepo.save(training);
		return new Status(taskId, 200);
	}
	
	public List<TroopTrain> getTrainingTasks(){
		List<TroopTrain> response = new ArrayList<>();
		List<TrainingEntity> entities = traningRepo.findAllByStatusOrderByTaskSeq(TaskStatus.OPEN.name());
		entities.forEach(e->{
			TroopTrain train = new TroopTrain();
			train.setBuilding(e.getTrainBuilding());
			train.setCount(e.getCount());
			train.setLink(e.getLink());
			train.setTargetCount(e.getTargetCount());
			train.setTaskId(e.getTaskId());
			train.setTroopType(e.getTroopType());
			train.setVillageId(e.getVillageId());
			train.setStatus(TaskStatus.valueOf(e.getStatus()));
			response.add(train);
		});
		
		return response;
	}
	
	public Status updateTroopTask(List<TroopTrain> request) {
		List<TrainingEntity> entities = traningRepo.findByTaskIdIn(request.stream().map(e->e.getTaskId()).collect(Collectors.toList()));
		for(TrainingEntity entity : entities) {
			for(TroopTrain req : request) {
				if(entity.getTaskId().equals(req.getTaskId())) {
					if(entity.getCount()+req.getCount()>=entity.getTargetCount()) {
						entity.setStatus(TaskStatus.DONE.name());
					}
					entity.setCount(entity.getCount()+req.getCount());
					entity.setLink(req.getLink());
					if(Log.isInfoEnabled())
						Log.info("Previous troopCount::"+entity.getCount()+"::currently training ::"+req.getCount()+":: Total count for village id "+entity.getVillageId()+" is "+entity.getCount()+req.getCount());
				}
			}
		}
		traningRepo.saveAll(entities);
		return new Status("SUCCESS", 200);
	}

}
