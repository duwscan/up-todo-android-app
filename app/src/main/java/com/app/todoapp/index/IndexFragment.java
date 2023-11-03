package com.app.todoapp.index;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.app.todoapp.R;
import com.app.todoapp.common.OnSaveTask;
import com.app.todoapp.common.TaskViewModel;
import com.app.todoapp.database.task.Task;
import com.app.todoapp.databinding.FragmentIndexBinding;
import com.app.todoapp.state.TaskState;
import com.app.todoapp.state.TaskStateType;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.Calendar;
import java.util.concurrent.Executors;

public class IndexFragment extends Fragment implements OnSaveTask {
    FragmentIndexBinding binding;
    private TaskViewModel taskViewModel;
    private final TaskState state = new TaskState();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentIndexBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.saveState(state);
        taskViewModel.getTask().observe(getViewLifecycleOwner(), taskWithCategories -> {
            if (taskWithCategories.isEmpty()) {
                Log.i("FETCH", "onCreateView: ");
            } else {
                String string = taskWithCategories.get(taskWithCategories.size() - 1).task.getTitle();
                binding.taskTitle.setText(string);
            }
        });
        Spinner filterSpinner = binding.filter;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.taskFilter, R.layout.dropdown_item);
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
}