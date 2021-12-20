package com.cognizant.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.exception.TaskNotFoundException;
import com.cognizant.model.Task;
import com.cognizant.service.TaskService;

@RestController
@RequestMapping("/api")
public class TaskController {

	@Autowired
	private TaskService taskService;

	@GetMapping("/tasks")
	public List<Task> getTasks() {
		return taskService.findAll();
	}

	@GetMapping("/tasks/{id}")
	public Task getTask(@PathVariable("id") int id) {

		Task task = taskService.findById(id);

		if (task == null) {
			throw new TaskNotFoundException("Task id - " + id + " not found");
		}

		return task;
	}

	@PostMapping("/tasks")
	public Task saveTask(@RequestBody Task task) {

		// check for invalid sub task id
		var tasks = taskService.findAll();
		checkInvalidSubTask(tasks, task);

		return taskService.save(task);
	}

	@PutMapping("/tasks")
	public Task updateTask(@RequestBody Task task) {

		Task updateTask = taskService.findById(task.getId());

		if (updateTask == null) {
			throw new TaskNotFoundException("Task id - " + task.getId() + " not found");
		}

		var tasks = taskService.findAll();

		// check for invalid sub task id
		checkInvalidSubTask(tasks, task);

		// check all sub tasks finished
		checkFinishedSubTask(task, updateTask);

		// check of recursive sub tasking
		checkRecursiveSubTasks(tasks, task);

		// updating sub task, update if sub task is finished
		updateIfSubTaskFinished(tasks, task, updateTask);

		return taskService.update(task);
	}

	@DeleteMapping("/tasks/{id}")
	public Task deleteTask(@PathVariable("id") int id) {

		Task deleteTask = taskService.findById(id);

		if (deleteTask == null) {
			throw new TaskNotFoundException("Task id " + id + " not found");
		} else if (deleteTask.isFinished() != true) {
			throw new RuntimeException("Task id " + id + " not finished");
		}

		var tasks = taskService.findAll();
		// check all sub tasks finished
		checkFinishedSubTask(tasks, deleteTask);

		// remove task for list of sub task
		tasks.stream().forEach(t -> {
			if (t.getSubTasksId().contains(id)) {
//				System.out.println("t.getSubTasksId() - " + t.getSubTasksId());
				t.getSubTasksId().remove(Integer.valueOf(id));
			}
		});

		taskService.deleteById(id);

		return deleteTask;
	}

	private void checkInvalidSubTask(List<Task> allTasks, Task task) {
		var allId = allTasks.stream().map(Task::getId).toList();
		var subTaskId = task.getSubTasksId();

//		System.out.println("subTaskId: " + subTaskId);

		subTaskId.sort(Integer::compareTo);

//		System.out.println("subTaskId: " + subTaskId);

		var commonId = allId.stream().filter(subTaskId::contains).toList();

//		System.out.println("commonId: " + commonId);

		if (!subTaskId.equals(commonId)) {
			throw new TaskNotFoundException(
					"Sub task id - " + subTaskId.stream().filter((id) -> !allId.contains(id)).toList() + " not found");
		} else if (subTaskId.contains(task.getId())) {
			throw new RuntimeException("Task id " + task.getId() + " can't be sub task of itself");
		}
	} // end of checkInvalidSubTask

	private void checkFinishedSubTask(Task task, Task updateTask) {
		if (task.isFinished() == true && updateTask.isFinished() == false) {
			// check if sub task are finished
			var subTaskId = updateTask.getSubTasksId();

//			System.out.println("subTaskId - " + subTaskId);

			var notFinishedSubTasks = taskService.findAll().stream().filter(t -> subTaskId.contains(t.getId()))
					.filter(t -> !t.isFinished()).toList();

//			System.out.println("notFinishedSubTasks - " + notFinishedSubTasks);

			if (!notFinishedSubTasks.isEmpty()) {
				throw new RuntimeException(
						"Sub task id " + notFinishedSubTasks.stream().map(Task::getId).toList() + " not finished");
			}
		}
	} // end of checkFinishedSubTask

	private void checkRecursiveSubTasks(List<Task> allTasks, Task task) {
		var id = task.getSubTasksId();
		allTasks.stream().filter(i -> id.contains(i.getId())).forEach(i -> {
			if (i.getSubTasksId().contains(task.getId())) {
				throw new RuntimeException("Task id " + i.getId() + " has sub task id " + task.getId());
			}
		});
	} // end of checkRecursiveSubTasks

	private void updateIfSubTaskFinished(List<Task> allTasks, Task task, Task updateTask) {
		var subTaskId = task.getSubTasksId();

//		System.out.println("subTaskId: " + subTaskId);

		var updateSubTaskid = updateTask.getSubTasksId();

//		System.out.println("updateSubTaskid: " + updateSubTaskid);

		allTasks.stream().filter(t -> updateSubTaskid.contains(t.getId())).filter(t -> !subTaskId.contains(t.getId()))
				.forEach(t -> {
					if (!t.isFinished()) {
						throw new RuntimeException("Sub task id " + t.getId() + " not finished");
					}
				});

//		System.out.println(allTasks.stream().filter(t -> subTaskId.contains(t.getId()))
//				.filter(t -> !updateSubTaskid.contains(t.getId())).toList());

		allTasks.stream().filter(t -> subTaskId.contains(t.getId())).filter(t -> !updateSubTaskid.contains(t.getId()))
				.forEach(t -> {
					if (t.isFinished()) {
						throw new RuntimeException("Sub task id " + t.getId() + " is finished");
					}
				});
	} // end of updateIfSubTaskFinished

	private void checkFinishedSubTask(List<Task> tasks, Task deleteTask) {
		tasks.stream().filter(t -> deleteTask.getSubTasksId().contains(t.getId())).forEach(t -> {
			if (!t.isFinished()) {
				throw new RuntimeException("Task id - " + t.getId() + " not finished");
			}
		});
	} // end of checkFinishedSubTask

}
