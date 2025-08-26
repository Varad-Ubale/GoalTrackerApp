package com.vktech.goaltrackerapp;

public class SubTask {
    private String title;
    private boolean isCompleted;

    public SubTask(String title) {
        this.title = title;
        this.isCompleted = false;
    }

    public String getTitle() { return title; }
    public boolean isCompleted() { return isCompleted; }

    public void setTitle(String title) { this.title = title; }
    public void setCompleted(boolean completed) { this.isCompleted = completed; }
}