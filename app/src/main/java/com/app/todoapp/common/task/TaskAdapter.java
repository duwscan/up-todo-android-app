package com.app.todoapp.common.task;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.todoapp.R;
import com.app.todoapp.common.task.TaskItemEventHandler;
import com.app.todoapp.common.task.TaskViewHolder;
import com.app.todoapp.database.task.TaskWithCategory;
import com.app.todoapp.utils.DateHelper;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    Context context;
    List<TaskWithCategory> tasks;
    TaskItemEventHandler taskItemEventHandler;

    public void setTaskItemEventHandler(TaskItemEventHandler taskItemEventHandler) {
        this.taskItemEventHandler = taskItemEventHandler;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(context).inflate(R.layout.task_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        if (tasks.get(position).category != null) {
            holder.category.setText(tasks.get(position).category.getName());
            holder.category.setBackgroundColor(Integer.parseInt(tasks.get(position).category.getIcon()));
        } else {
            holder.category.setVisibility(View.INVISIBLE);
        }
        Date date = Date.from(tasks.get(position).task.getDueDate().atTime(tasks.get(position).task.getDueTime()).atZone(ZoneId.systemDefault()).toInstant());
        holder.dueDate.setText(DateHelper.formatDate(date));
        holder.title.setText(tasks.get(position).task.getTitle());
        holder.checkBox.setChecked(tasks.get(position).task.isCompleted());
        if (taskItemEventHandler != null) {
            taskItemEventHandler.eventHandler(tasks.get(position), holder);
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public TaskAdapter(Context context, List<TaskWithCategory> tasks) {
        this.context = context;
        this.tasks = tasks;
    }
}
