package com.app.todoapp.viewmodels.task;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.app.todoapp.database.task.Task;
import com.app.todoapp.database.task.TaskDAO;
import com.app.todoapp.database.task.TaskWithCategory;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TaskViewModel extends ViewModel {
    public static String TASK_FILTER_KEY = "TASK_FILTER";
    private SavedStateHandle savedStateHandle;
    private TaskDAO taskDAO;
    public LiveData<List<TaskWithCategory>> data;

    public TaskViewModel(TaskDAO taskDAO, SavedStateHandle savedStateHandle) {
        this.taskDAO = taskDAO;
        this.savedStateHandle = savedStateHandle;
    }

    public LiveData<List<TaskWithCategory>> getAllTasksWithCategories() {
        return taskDAO.getAllTasksWithCategories();
    }

    public ListenableFuture<Long> saveTask(Task task) {
        ListenableFuture<Long> save = taskDAO.save(task);
        forceUpdateUI();
        return save;
    }

    private LiveData<List<TaskWithCategory>> fetchData() {
        LiveData<TaskState> taskFilterState = savedStateHandle.getLiveData(TASK_FILTER_KEY);
        return Transformations.switchMap(taskFilterState, state -> {
            TaskStateType filterType = state.getFilter();
            if (filterType.equals(TaskStateType.DATE)) {
                return taskDAO.getTasksByDate((long) state.getData());
            }
            if (filterType.equals(TaskStateType.TODAY)) {
                long todayFastTime = new Date().getTime();
                return taskDAO.getTasksByDate(todayFastTime);
            }
            if (filterType.equals(TaskStateType.TOMORROW)) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                long tomorrow = calendar.getTime().getTime();
                return taskDAO.getTasksByDate(tomorrow);
            }
            if (filterType.equals(TaskStateType.YESTERDAY)) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, -1);
                long yesterday = calendar.getTime().getTime();
                return taskDAO.getTasksByDate(yesterday);
            }
            if (filterType.equals(TaskStateType.SEARCH)) {
                return taskDAO.searchTasksByTitle((String) state.getData());
            }
            return getAllTasksWithCategories();
        });
    }

    public void forceUpdateUI() {
        this.data = fetchData();
    }
}
