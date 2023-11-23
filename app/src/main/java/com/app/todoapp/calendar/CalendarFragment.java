package com.app.todoapp.calendar;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.todoapp.R;
import com.app.todoapp.calendar.cmp.model.OnPickedDate;
import com.app.todoapp.common.task.OnSaveTask;
import com.app.todoapp.common.task.TaskAdapter;
import com.app.todoapp.common.task.TaskDetailsBottomSheet;
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
import java.time.LocalTime;
import java.util.stream.Collectors;

public class CalendarFragment extends Fragment implements OnSaveTask, OnPickedDate {
    FragmentCalendarBinding binding;
    private RecyclerView recyclerViewNotCompletedTask;
    private RecyclerView recyclerViewCompletedTask;
    private TaskViewModel taskViewModel;
    private final TaskState state = new TaskState();

    Button getUncompletedTask;
    Button getCompletedTask;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(new CalendarCompFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        getUncompletedTask = binding.getUncompletedTask;
        getCompletedTask = binding.getCompletedTask;

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        recyclerViewNotCompletedTask = binding.recyclerviewTaskNotCompleted;
        recyclerViewCompletedTask = binding.recyclerviewTaskCompleted;
        recyclerViewNotCompletedTask.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewCompletedTask.setLayoutManager(new LinearLayoutManager(requireContext()));
        state.setFilterState(TaskFilterState.DATE, LocalDate.now());
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
        return view;
    }

    @Override
    public void onPicked(int month, int day) {
        LocalDate localDate = LocalDate.of(LocalDate.now().getYear(), month, day);
        if (localDate.equals(LocalDate.now())) {
            getUncompletedTask.setText("Today");
        } else {
            getUncompletedTask.setText("Uncompleted");
        }
        state.setFilterState(TaskFilterState.DATE, localDate);
        taskViewModel.saveState(state);
    }

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

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.calendar_fragment, fragment);
        fragmentTransaction.commit();
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
            selector.card.setOnClickListener(v -> {
                // open details dialog
                TaskDetailsBottomSheet.context(requireActivity())
                        .setOnUpdateTaskListener(task -> {
                            this.onSaveTask(task.task);
                        }).setOnDeleteTaskListener(task -> {
                            this.deleteTask(task.task);
                        })
                        .showTaskDetails(item);
            });
        });
    }

    @Override
    public void onSaveTask(Task task) {
        taskViewModel.saveTask(task);
        state.setState(TaskStateType.INSERT);
        taskViewModel.saveState(state);
    }

    public void deleteTask(Task task) {
        taskViewModel.deleteTask(task);
        state.setState(TaskStateType.INSERT);
        taskViewModel.saveState(state);
    }

}