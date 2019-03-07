package com.travian.task.queue.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.travian.task.queue.response.Preference;
import com.travian.task.queue.service.PreferenceService;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/preference")
public class PreferenceController {
	
	private static final Logger Log = LoggerFactory.getLogger(PreferenceController.class);
	
	@Autowired
	private PreferenceService service;
	
	@ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = List.class),
            @ApiResponse(code = 412, message = "Precondition Failed"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@RequestMapping(value="/getAccountPreference/{userId}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Preference>> getAccountPreference(@PathVariable("userId") String userId, HttpServletRequest servletRequest, @RequestHeader HttpHeaders headers) throws IOException {
		List<Preference> response = service.getAccountPreference(userId);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	

}
