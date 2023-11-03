package com.app.todoapp.index;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.app.todoapp.R;
import com.app.todoapp.common.OnSaveTask;
import com.app.todoapp.common.TaskAdapter;
import com.app.todoapp.common.TaskItemEventHandler;
import com.app.todoapp.common.TaskViewModel;
import com.app.todoapp.database.task.Task;
import com.app.todoapp.database.task.TaskWithCategory;
import com.app.todoapp.databinding.FragmentIndexBinding;
import com.app.todoapp.state.TaskState;
import com.app.todoapp.state.TaskStateType;

import java.util.stream.Collectors;

public class IndexFragment extends Fragment implements OnSaveTask {
    private FragmentIndexBinding binding;
    private TaskViewModel taskViewModel;
    private final TaskState state = new TaskState();
    private RecyclerView recyclerViewNotCompletedTask;
    private RecyclerView recyclerViewCompletedTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIndexBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        recyclerViewNotCompletedTask = binding.recyclerviewTaskNotCompleted;
        recyclerViewCompletedTask = binding.recyclerviewTaskCompleted;
        recyclerViewNotCompletedTask.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewCompletedTask.setLayoutManager(new LinearLayoutManager(requireContext()));

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
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

        Spinner filterSpinner = binding.filter;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(), R.array.taskFilter, R.layout.dropdown_item);
        adapter.setDropDownViewResource(R.layout.dropdown_item);
        filterSpinner.setAdapter(adapter);

        return view;
    }

    @Override
    public void onSaveTask(Task task) {
        taskViewModel.saveTask(task);
        state.setState(TaskStateType.INSERT);
        taskViewModel.saveState(state);
    }

    private TaskItemEventHandler taskItemEventHandler() {
        return ((item, selector) -> {
            selector.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                item.task.setCompleted(isChecked);
                onSaveTask(item.task);
            });
        });
    }
}
