package com.cognizant.model;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "taske_name")
	private String taskName;

	@Column(name = "time_spent_on_task")
//	private int timeSpentOnTask;
	private Duration timeSpentOnTask;

	@Column(name = "taskGroup")
	private String taskGroup;

	@Column(name = "task_assignee")
	private String taskAssignee;

	@Column(name = "sub_tasks")
	@ElementCollection(targetClass = Integer.class)
	private List<Integer> subTasksId;

	@Column(name = "is_finished")
	private boolean finished;

	public Task() {

	}

	public Task(String taskName) {
		this.taskName = taskName;
	}

	public Task(String taskName, Duration timeSpentOnTask, String taskGroup, String taskAssignee,
			List<Integer> subTasksId, boolean finished) {
		this.taskName = taskName;
		this.timeSpentOnTask = timeSpentOnTask;
		this.taskGroup = taskGroup;
		this.taskAssignee = taskAssignee;
		this.subTasksId = subTasksId;
		this.finished = finished;
	}

	public Task(int id, String taskName, Duration timeSpentOnTask, String taskGroup, String taskAssignee,
			List<Integer> subTasksId, boolean finished) {
		this.id = id;
		this.taskName = taskName;
		this.timeSpentOnTask = timeSpentOnTask;
		this.taskGroup = taskGroup;
		this.taskAssignee = taskAssignee;
		this.subTasksId = subTasksId;
		this.finished = finished;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Duration getTimeSpentOnTask() {
		return timeSpentOnTask;
	}

	public void setTimeSpentOnTask(Duration timeSpentOnTask) {
		this.timeSpentOnTask = timeSpentOnTask;
	}

	public String getTaskGroup() {
		return taskGroup;
	}

	public void setTaskGroup(String taskGroup) {
		this.taskGroup = taskGroup;
	}

	public String getTaskAssignee() {
		return taskAssignee;
	}

	public void setTaskAssignee(String taskAssignee) {
		this.taskAssignee = taskAssignee;
	}

	public List<Integer> getSubTasksId() {
		return subTasksId;
	}

	public void setSubTasksId(List<Integer> subTasksId) {
		this.subTasksId = subTasksId;
	}

	public void addSubTaskId(Integer... id) {
		if (this.subTasksId == null) {
			this.subTasksId = new ArrayList<>(Arrays.asList(id));
		} else {
			this.subTasksId.addAll(Arrays.asList(id));
		}
	}

	public boolean isFinished() {
		return finished;
	}

	public boolean setFinished(boolean finished) {
		return this.finished = finished;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", taskName=" + taskName + ", timeSpentOnTask=" + timeSpentOnTask + ", taskGroup="
				+ taskGroup + ", taskAssignee=" + taskAssignee + ", subTasksId=" + subTasksId + ", finished=" + finished
				+ "]";
	}

}
