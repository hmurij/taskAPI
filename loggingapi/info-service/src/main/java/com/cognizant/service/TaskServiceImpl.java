package com.cognizant.service;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.dao.TaskRepository;
import com.cognizant.model.Task;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@PostConstruct
	public void initTask() {

		List<Task> tasks = IntStream.rangeClosed(1, 5)
				.mapToObj(i -> new Task("Task Nr " + i, Duration.ofSeconds(new Random().nextInt(30 * 60)),
						"Task Group Nr " + i, "Employee Nr " + i, null, false))
				.collect(Collectors.toList());

		tasks.get(0).addSubTaskId(2, 4);
		tasks.get(4).addSubTaskId(3);
		tasks.get(3).setFinished(true);

		taskRepository.saveAll(tasks);
	}

	@Override
	public List<Task> findAll() {
		return taskRepository.findAll();
	}

	@Override
	public Task findById(int id) {

		var task = taskRepository.findById(id);

		return task.isEmpty() ? null : task.get();
	}

	@Override
	public Task save(Task task) {
		return taskRepository.save(task);
	}

	@Override
	public Task update(Task task) {

		return taskRepository.save(task);
	}

	@Override
	public void deleteById(int id) {
		taskRepository.deleteById(id);
	}

}
