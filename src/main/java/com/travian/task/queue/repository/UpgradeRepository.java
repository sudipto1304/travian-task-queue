package com.travian.task.queue.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travian.task.queue.entity.UpgradeEntity;

@Repository
@Transactional
public interface UpgradeRepository extends JpaRepository<UpgradeEntity, Integer>{
	List<UpgradeEntity> findByVillageIdAndStatusOrderByTaskSeq(int villageId, String status);
	
	List<UpgradeEntity> deleteByVillageId(int villageId);
	Integer deleteByVillageIdAndTaskId(int villageId, String taskId);
	
	UpgradeEntity findByVillageIdAndTaskId(int villageId, String taskId);
} 
