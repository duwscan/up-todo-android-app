package com.app.todoapp.calendar;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.todoapp.R;
import com.app.todoapp.common.task.OnSaveTask;
import com.app.todoapp.common.task.TaskAdapter;
import com.app.todoapp.common.task.TaskItemEventHandler;
import com.app.todoapp.common.task.TaskViewModel;
import com.app.todoapp.database.task.Task;
import com.app.todoapp.databinding.FragmentCalendarBinding;
import com.app.todoapp.databinding.FragmentIndexBinding;
import com.app.todoapp.state.TaskFilterState;
import com.app.todoapp.state.TaskState;
import com.app.todoapp.state.TaskStateType;
import com.app.todoapp.utils.DateHelper;
import com.sahana.horizontalcalendar.HorizontalCalendar;

import java.time.LocalDate;
import java.util.stream.Collectors;

public class CalendarFragment extends Fragment implements OnSaveTask {
    FragmentCalendarBinding binding;
    private RecyclerView recyclerViewNotCompletedTask;
    private RecyclerView recyclerViewCompletedTask;
    private TaskViewModel taskViewModel;
    private final TaskState state = new TaskState();

    Button getUncompletedTask;
    Button getCompletedTask;

//    HorizontalCalendar horizontalCalendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        getUncompletedTask = binding.getUncompletedTask;
        getCompletedTask = binding.getCompletedTask;
//        horizontalCalendar = binding.horizontalCalendar;
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        recyclerViewNotCompletedTask = binding.recyclerviewTaskNotCompleted;
        recyclerViewCompletedTask = binding.recyclerviewTaskCompleted;
        recyclerViewNotCompletedTask.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewCompletedTask.setLayoutManager(new LinearLayoutManager(requireContext()));
        taskViewModel.saveState(state);
        taskViewModel.getTask().observe(getViewLifecycleOwner(), taskWithCategories -> {
            TaskAdapter adapterForNotCompleted = new TaskAdapter(requireContext(), taskWithCategories.stream()
                    .filter(taskWithCategory -> !taskWithCategory.task.isCompleted()).collect(Collectors.toList()));
            adapterForNotCompleted.setTaskItemEventHandler(this.taskItemEventHandler());

            TaskAdapter adapterForCompleted = new TaskAdapter(requireContext(), taskWithCategories.stream()
                    .filter(taskWithCategory -> taskWithCategory.task.isCompleted()).collect(Collectors.toList()));
            adapterForCompleted.setTaskItemEventHandler(this.taskItemEventHandler());

            recyclerViewCompletedTask.setAdapter(adapterForCompleted);
            recyclerViewNotCompletedTask.setAdapter(adapterForNotCompleted);
        });
        initUnCompletedAndCompletedButtons();
//        initCalendar();
        return view;
    }

//    private void initCalendar() {
//        horizontalCalendar.setOnDateSelectListener(dateModel -> {
//            LocalDate localDate = LocalDate.of(dateModel.year, dateModel.monthNumber, dateModel.day);
//            if (localDate.equals(LocalDate.now())) {
//                getUncompletedTask.setText("Today");
//            } else {
//                getUncompletedTask.setText("Uncompleted");
//            }
//            state.setFilterState(TaskFilterState.DATE, localDate);
//            taskViewModel.saveState(state);
//        });
//    }

    private void initUnCompletedAndCompletedButtons() {
        setActiveButtonStyle(getUncompletedTask);
        recyclerViewNotCompletedTask.setVisibility(View.VISIBLE);
        recyclerViewCompletedTask.setVisibility(View.GONE);
        getUncompletedTask.setOnClickListener(this.unCompletedButton());
        getCompletedTask.setOnClickListener(this.completedButton());
    }

    private void setActiveButtonStyle(Button v) {
        v.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primaryButtonColor));
        v.setClickable(false);
    }

    private void setDeActiveButtonStyle(Button v) {
        v.setBackgroundColor(0);
        v.setClickable(true);
    }

    private View.OnClickListener completedButton() {
        return v -> {
            boolean isNotCompletedTaskVisible = recyclerViewNotCompletedTask.getVisibility() == View.VISIBLE;
            if (isNotCompletedTaskVisible) {
                setActiveButtonStyle((Button) v);
                setDeActiveButtonStyle(getUncompletedTask);
                recyclerViewCompletedTask.setVisibility(View.VISIBLE);
                recyclerViewNotCompletedTask.setVisibility(View.GONE);
            }
        };
    }

    private View.OnClickListener unCompletedButton() {
        return v -> {
            boolean isCompletedTaskVisible = recyclerViewNotCompletedTask.getVisibility() != View.VISIBLE;
            if (isCompletedTaskVisible) {
                setActiveButtonStyle((Button) v);
                setDeActiveButtonStyle(getCompletedTask);
                recyclerViewNotCompletedTask.setVisibility(View.VISIBLE);
                recyclerViewCompletedTask.setVisibility(View.GONE);
            }
        };
    }

    private TaskItemEventHandler taskItemEventHandler() {
        return ((item, selector) -> {
            selector.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                item.task.setCompleted(isChecked);
                onSaveTask(item.task);
            });
        });
    }

    @Override
    public void onSaveTask(Task task) {
        taskViewModel.saveTask(task);
        state.setState(TaskStateType.INSERT);
        taskViewModel.saveState(state);
    }
}