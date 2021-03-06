package com.travian.task.queue.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travian.task.queue.entity.TradeEntity;
import com.travian.task.queue.entity.TrainingEntity;
import com.travian.task.queue.entity.UpgradeEntity;
import com.travian.task.queue.repository.TradeRepository;
import com.travian.task.queue.repository.TraningRepository;
import com.travian.task.queue.repository.UpgradeRepository;
import com.travian.task.queue.request.Status;
import com.travian.task.queue.request.TaskRequest;
import com.travian.task.queue.request.TaskStatus;
import com.travian.task.queue.request.TaskType;
import com.travian.task.queue.request.TradeRequest;
import com.travian.task.queue.request.TroopTrain;

@Service
public class TaskService {

	private static final Logger Log = LoggerFactory.getLogger(TaskService.class);

	@Autowired
	UpgradeRepository upgradeRepo;
	
	@Autowired
	TraningRepository traningRepo;
	
	@Autowired
	TradeRepository tradeRepo;

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

	public Status removeAllTasks(String userId, String villageId) {
		upgradeRepo.deleteByUserIdAndVillageId(userId, Integer.valueOf(villageId));
		return new Status("SUCCESS", 200);
	}

	public TaskRequest getTask(String userId, String villageId) {
		return getAllTask(userId, villageId).stream().findFirst().orElse(null);
	}

	public List<TaskRequest> getAllTask(String userId, String villageId) {
		List<UpgradeEntity> tasks = upgradeRepo.findByUserIdAndVillageIdAndStatusOrderByTaskSeq(userId, Integer.valueOf(villageId),
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
	
	public List<TroopTrain> getTrainingTasks(String userId){
		List<TroopTrain> response = new ArrayList<>();
		List<TrainingEntity> entities = traningRepo.findByUserIdAndStatusOrderByTaskSeq(userId, TaskStatus.OPEN.name());
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
		if(Log.isInfoEnabled())
			Log.info("troop update Request--->"+request);
		if(Log.isInfoEnabled())
			Log.info("troop update tasks in db--->"+entities);
		for(TrainingEntity entity : entities) {
			for(TroopTrain req : request) {
				if(entity.getTaskId().equals(req.getTaskId())) {
					if(Log.isInfoEnabled())
						Log.info("Previous troopCount::"+entity.getCount()+"::currently training ::"+req.getCount()+":: Total count for village id "+entity.getVillageId()+" is "+Integer.valueOf(entity.getCount()+req.getCount()));
					if(entity.getCount()+req.getCount()>=entity.getTargetCount()) {
						entity.setStatus(TaskStatus.DONE.name());
					}
					entity.setCount(Integer.valueOf(entity.getCount()+req.getCount()));
					entity.setLink(req.getLink());
					
				}
			}
		}
		if(Log.isInfoEnabled())
			Log.info("final update--->"+entities);
		traningRepo.saveAll(entities);
		return new Status("SUCCESS", 200);
	}
	
	public Map<String, List<TaskRequest>> getAllTasks(String userId){
		Map<String, List<TaskRequest>> result = new HashMap<String, List<TaskRequest>>();
		List<TaskRequest> taskList = null;
		List<UpgradeEntity> allTask = upgradeRepo.findAllByUserId(userId);
		for(UpgradeEntity task : allTask) {
			TaskRequest villageTask = new TaskRequest();
			villageTask.setId(task.getUpgradeId());
			villageTask.setTaskId(task.getTaskId());
			villageTask.setLevel(task.getLevel());
			villageTask.setTaskType(TaskType.valueOf(task.getTaskType()));
			villageTask.setUserId(task.getUserId());
			villageTask.setVillageId(String.valueOf(task.getVillageId()));
			if(result.containsKey(String.valueOf(task.getVillageId()))) {
				result.get(String.valueOf(task.getVillageId())).add(villageTask);
			}else {
				taskList = new ArrayList<TaskRequest>();
				taskList.add(villageTask);
				result.put(String.valueOf(task.getVillageId()), taskList);
			}
		}
		return result;
	}
	
	
	public Status createTrade(TradeRequest trade) {
		TradeEntity entity = new TradeEntity();
		entity.setClay(trade.getClay());
		entity.setCrop(trade.getCrop());
		entity.setDestVillageName(trade.getDestVillageName());
		entity.setInterval(trade.getInterval());
		entity.setIron(trade.getIron());
		entity.setSourceVillageId(trade.getSourceVillage());
		entity.setUserId(trade.getUserId());
		entity.setWood(trade.getWood());
		entity.setTransactionId(UUID.randomUUID().toString());
		tradeRepo.save(entity);
		return new Status(entity.getTransactionId(), 200);
	}
	
	public List<TradeRequest> getTrades(String userId){
		List<TradeEntity> trades = tradeRepo.findAllByUserId(userId);
		List<TradeRequest> response = new ArrayList<>();
		trades.forEach(e->{
			TradeRequest trade = new TradeRequest();
			trade.setClay(e.getClay());
			trade.setWood(e.getWood());
			trade.setCrop(e.getCrop());
			trade.setDestVillageName(e.getDestVillageName());
			trade.setInterval(e.getInterval());
			trade.setIron(e.getIron());
			trade.setSourceVillage(e.getSourceVillageId());
			trade.setUserId(e.getUserId());
			trade.setWood(e.getWood());
			trade.setTransactionId(e.getTransactionId());
			trade.setLastUpdateTime(e.getLastUpdate());
			response.add(trade);
		});
		
		return response;
	}
	
	public Status updateTrades(String transactionId){
		TradeEntity trade = tradeRepo.findByTransactionId(transactionId);
		trade.setLastUpdate(String.valueOf(new Date().getTime()));
		tradeRepo.save(trade);
		return new Status("SUCCESS", 200);
	}

}
