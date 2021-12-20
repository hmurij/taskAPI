package com.cognizant.routes;

import java.time.LocalTime;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import com.cognizant.manager.TaskManager;
import com.cognizant.service.TaskService;

//@Component
public class TaskManagementRoute extends RouteBuilder {

	@Autowired
	private TaskService taskService;

	@Autowired
	private TaskManager taskManager;

	@Override
	public void configure() throws Exception {
//		from("timer:tasks")
//		.bean(taskService, "getAllTasks")
//				.bean(taskManager, "updateTasks").to("log:tasks");

		from("timer:time").bean(taskService, "getAllTasks").transform()
				.body((str1, str2) -> "Local time: " + LocalTime.now()).bean(taskManager, "updateTasks").to("log:time");

	}

}
