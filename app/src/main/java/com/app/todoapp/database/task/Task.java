package com.app.todoapp.database.task;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Task {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private Date dueDateTime;
    @ColumnInfo(name = "category_id")
    private Long belongToCategoryId;
    private String priority;
    private boolean isCompleted;

    public Task(String title, String description, Date dueDateTime, Long belongToCategoryId, String priority, boolean isCompleted) {
        this.title = title;
        this.description = description;
        this.dueDateTime = dueDateTime;
        this.belongToCategoryId = belongToCategoryId;
        this.priority = priority;
        this.isCompleted = isCompleted;
    }

    public Task() {
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDueDateTime() {
        return dueDateTime;
    }

    public long getBelongToCategoryId() {
        return belongToCategoryId;
    }

    public String getPriority() {
        return priority;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDateTime(Date dueDateTime) {
        this.dueDateTime = dueDateTime;
    }

    public void setBelongToCategoryId(Long belongToCategoryId) {
        this.belongToCategoryId = belongToCategoryId;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
