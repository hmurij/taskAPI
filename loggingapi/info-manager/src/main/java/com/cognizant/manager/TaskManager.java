package com.cognizant.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cognizant.model.Task;
import com.cognizant.service.TaskService;

//@Component
public class TaskManager {

	private final int MAX_TASKS = 10;

	@Autowired
	private TaskService taskService;

	private Logger logger = LoggerFactory.getLogger(TaskManager.class);

	public void updateTasks() {
		logger.info("TaskManager updating tasks: {}", taskService.findAll());
//		logger.info("TaskManager total tasks: {}", taskService.getAllTasks().size());

		if (taskService.findAll().size() < MAX_TASKS) {
			logger.info("TaskManager task size less than {}: {}", MAX_TASKS, "INSERT NEW TASK");
			taskService.save(new Task("Task"));

		}
	}

}
