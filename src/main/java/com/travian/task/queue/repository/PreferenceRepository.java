package com.travian.task.queue.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travian.task.queue.entity.PreferenceEntity;
import com.travian.task.queue.entity.PreferenceKey;

@Repository
@Transactional
public interface PreferenceRepository extends JpaRepository<PreferenceEntity, PreferenceKey>{

}
