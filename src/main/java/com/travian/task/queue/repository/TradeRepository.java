package com.travian.task.queue.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travian.task.queue.entity.TradeEntity;

@Repository
@Transactional
public interface TradeRepository extends JpaRepository<TradeEntity, String>{
	
	List<TradeEntity> findAllByUserId(String userId);
	TradeEntity findByTransactionId(String transactionId);

}
