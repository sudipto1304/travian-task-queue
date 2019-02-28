package com.travian.task.queue.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travian.task.queue.entity.VillageEntity;
import com.travian.task.queue.entity.VillageKey;



@Repository
@Transactional
public interface VillageRepository extends JpaRepository<VillageEntity, VillageKey>{

}
