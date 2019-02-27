package com.travian.task.queue.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travian.task.queue.entity.TrainingEntity;


@Repository
@Transactional
public interface TraningRepository extends JpaRepository<TrainingEntity, Integer>{

		List<TrainingEntity> findAllByStatusOrderByTaskSeq(String status);
		List<TrainingEntity> findByTaskIdIn(List<String> taskId);

}
