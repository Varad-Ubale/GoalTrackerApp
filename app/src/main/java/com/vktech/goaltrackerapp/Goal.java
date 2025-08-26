package com.vktech.goaltrackerapp;

import java.util.ArrayList;
import java.util.List;

public class Goal {
    private String title;
    private String description;
    private List<SubTask> subTaskList;

    public Goal(String title, String description) {
        this.title = title;
        this.description = description;
        this.subTaskList = new ArrayList<>();
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public List<SubTask> getSubTaskList() { return subTaskList; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }

    public void addSubTask(SubTask subTask) {
        this.subTaskList.add(subTask);
    }

    public int getProgress() {
        if (subTaskList.isEmpty()) {
            return 0;
        }
        long completedCount = subTaskList.stream().filter(SubTask::isCompleted).count();
        return (int) ((double) completedCount / subTaskList.size() * 100);
    }
}