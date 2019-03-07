package com.travian.task.queue.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travian.task.queue.controller.PreferenceController;
import com.travian.task.queue.entity.AccountPreferenceEntity;
import com.travian.task.queue.repository.AccountPreferenceRepository;
import com.travian.task.queue.response.Preference;

@Service
public class PreferenceService {
	
	private static final Logger Log = LoggerFactory.getLogger(PreferenceService.class);
	
	@Autowired
	private AccountPreferenceRepository repo;
	
	
	
	public List<Preference> getAccountPreference(String userId){
		List<Preference> response = new ArrayList<>();
		List<AccountPreferenceEntity> entities = repo.findAllByUserId(userId);
		if(Log.isInfoEnabled())
			Log.info("AccountPreferenceEntity:::"+entities);
		entities.forEach(e->{
			Preference pref = new Preference();
			pref.setPreference(e.getPreference());
			pref.setValue(e.getValue());
			response.add(pref);
		});
		return response;
	}

}
