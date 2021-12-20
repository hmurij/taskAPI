package com.cognizant.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.model.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {

}
