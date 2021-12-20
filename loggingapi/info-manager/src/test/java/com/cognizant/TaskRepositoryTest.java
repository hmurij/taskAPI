package com.cognizant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cognizant.model.Task;
import com.cognizant.service.TaskService;

@DataJpaTest
public class TaskRepositoryTest {

	@Autowired
	private TaskService taskService;

	@Test
	public void testGetAllTasks() {
		assertEquals(taskService.findAll().size(), 5);
	}

	@Test
	public void testGetAllTasksEmpty() {
		taskService.deleteById(1);
		taskService.deleteById(2);
		taskService.deleteById(3);
		taskService.deleteById(4);
		taskService.deleteById(5);
		assertEquals(taskService.findAll().size(), 0);
	}

	@Test
	public void testSaveTask() {
		Task savedTask = taskService.save(new Task());
		assertNotNull(savedTask);
	}

	@Test
	public void testGetTaskExist() {
		int taskId = 1;
//		System.out.println(taskService.getAllTasks());
		Task task = taskService.findById(taskId);
//		System.out.println(task);
		assertThat(task.getId() == taskId);
	}

	@Test
	public void testGetTaskNotExist() {
		int taskId = 0;
//		System.out.println(taskService.getAllTasks());
//		Task task = taskService.getTask(taskId);
		assertNull(taskService.findById(taskId));
//		assertThrows(EntityNotFoundException.class, () -> taskService.getTask(taskId),
//				"Task with id " + taskId + " not found");
		// EntityNotFoundException

	}

	@Test
	public void testUpdateTaskExist() {
		Task updateTask = new Task("New Task Name");
		updateTask.setId(1);
		assertEquals(updateTask.getTaskName(), taskService.update(updateTask).getTaskName());
	}

	@Test
	public void testDeleteTask() {
		int id = 1;

		Task taskBeforeDelete = taskService.findById(id);

		taskService.deleteById(id);

		Task taskAfterDelete = taskService.findById(id);
		assertNotNull(taskBeforeDelete);
		assertNull(taskAfterDelete);

	}

}
