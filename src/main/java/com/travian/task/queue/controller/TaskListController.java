package com.travian.task.queue.controller;

import java.io.IOException;
import java.util.LinkedList;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.travian.task.queue.request.Status;
import com.travian.task.queue.request.TaskRequest;
import com.travian.task.queue.request.TroopTrain;
import com.travian.task.queue.service.TaskService;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/task")
public class TaskListController {
	
	private static final Logger Log = LoggerFactory.getLogger(TaskListController.class);

	
	@Autowired
	private TaskService service;
	
	@ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = Status.class),
            @ApiResponse(code = 412, message = "Precondition Failed"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@RequestMapping(value="/createTask", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> createTask(@RequestBody TaskRequest request, HttpServletRequest servletRequest, @RequestHeader HttpHeaders headers) throws IOException {
		if(Log.isDebugEnabled())
			Log.debug("AccoiuntInfo Request::"+request);
		Status response = service.addTask(request);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	
	@ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = TaskRequest.class),
            @ApiResponse(code = 412, message = "Precondition Failed"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@RequestMapping(value="/getTask/{villageId}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TaskRequest> getTask(@PathVariable("villageId") String villageId, HttpServletRequest servletRequest, @RequestHeader HttpHeaders headers) throws IOException {
		TaskRequest response = service.getTask(villageId);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	
	@ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = Status.class),
            @ApiResponse(code = 412, message = "Precondition Failed"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@RequestMapping(value="/completeTask/{villageId}/{taskId}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> completeTask(@PathVariable("villageId") String villageId, @PathVariable("taskId") String taskId, HttpServletRequest servletRequest, @RequestHeader HttpHeaders headers) throws IOException {
		Status response = service.completeTask(villageId, taskId);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = TaskRequest.class),
            @ApiResponse(code = 412, message = "Precondition Failed"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@RequestMapping(value="/getAllTask/{villageId}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TaskRequest>> getAllTask(@PathVariable("villageId") String villageId, HttpServletRequest servletRequest, @RequestHeader HttpHeaders headers) throws IOException {
		List<TaskRequest> response = service.getAllTask(villageId);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	
	@ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = List.class),
            @ApiResponse(code = 412, message = "Precondition Failed"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@RequestMapping(value="/createTasks", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Status>> createTasks(@RequestBody List<TaskRequest> request, HttpServletRequest servletRequest, @RequestHeader HttpHeaders headers) throws IOException {
		if(Log.isDebugEnabled())
			Log.debug("AccoiuntInfo Request::"+request);
		List<Status> response = service.addTasks(request);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = Status.class),
            @ApiResponse(code = 412, message = "Precondition Failed"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@RequestMapping(value="/skipTask/{villageId}/{taskId}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> skipTask(@PathVariable("villageId") String villageId, @PathVariable("taskId") String taskId, HttpServletRequest servletRequest, @RequestHeader HttpHeaders headers) throws IOException {
		Status response = service.skipTask(villageId, taskId);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	
	@ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = Status.class),
            @ApiResponse(code = 412, message = "Precondition Failed"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@RequestMapping(value="/removeAllTasks/{villageId}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> removeAllTasks(@PathVariable("villageId") String villageId, HttpServletRequest servletRequest, @RequestHeader HttpHeaders headers) throws IOException {
		if(Log.isDebugEnabled())
			Log.debug("AccoiuntInfo Request::"+villageId);
		Status response = service.removeAllTasks(villageId);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = Status.class),
            @ApiResponse(code = 412, message = "Precondition Failed"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@RequestMapping(value="/trainTroop", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> trainTroop(@RequestBody TroopTrain request, HttpServletRequest servletRequest, @RequestHeader HttpHeaders headers) throws IOException {
		Status response = service.trainTroop(request);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = List.class),
            @ApiResponse(code = 412, message = "Precondition Failed"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@RequestMapping(value="/getTroopTrainTasks", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TroopTrain>> getTrainTaks(HttpServletRequest servletRequest, @RequestHeader HttpHeaders headers) throws IOException {
		List<TroopTrain> response = service.getTrainingTasks();
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	
	@ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = Status.class),
            @ApiResponse(code = 412, message = "Precondition Failed"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@RequestMapping(value="/updateTroopTask", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> updateTroopTask(@RequestBody List<TroopTrain> request, HttpServletRequest servletRequest, @RequestHeader HttpHeaders headers) throws IOException {
		Status response = service.updateTroopTask(request);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	
	
}
