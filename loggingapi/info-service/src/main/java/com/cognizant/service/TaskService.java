package com.cognizant.service;

import java.util.List;

import com.cognizant.model.Task;

public interface TaskService {

	public List<Task> findAll();

	public Task findById(int id);

	public Task save(Task task);

	public Task update(Task task);

	public void deleteById(int id);

}
