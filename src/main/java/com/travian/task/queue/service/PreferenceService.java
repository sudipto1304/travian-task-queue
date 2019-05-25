package com.travian.task.queue.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travian.task.queue.entity.AccountPreferenceEntity;
import com.travian.task.queue.repository.AccountPreferenceRepository;
import com.travian.task.queue.response.Preference;

@Service
public class PreferenceService {
	
	private static final Logger Log = LoggerFactory.getLogger(PreferenceService.class);
	
	@Autowired
	private AccountPreferenceRepository repo;
	
	
	
	public Map<String, String> getAccountPreference(String userId){
		Map<String, String> response = new HashMap<>();
		List<AccountPreferenceEntity> entities = repo.findAllByUserId(userId);
		if(Log.isInfoEnabled())
			Log.info("AccountPreferenceEntity:::"+entities);
		entities.forEach(e->{
			response.put(e.getPreference(), e.getValue());
		});
		return response;
	}

}
