package com.travian.task.queue.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travian.task.queue.entity.AccountPreferenceEntity;
import com.travian.task.queue.entity.AccountPreferenceKey;

@Repository
@Transactional
public interface AccountPreferenceRepository extends JpaRepository<AccountPreferenceEntity, String>{
	
	List<AccountPreferenceEntity> findAllByUserId(String userId);

}
