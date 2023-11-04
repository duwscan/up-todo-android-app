package com.app.todoapp.index;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.todoapp.R;
import com.app.todoapp.common.task.OnSaveTask;
import com.app.todoapp.common.task.TaskAdapter;
import com.app.todoapp.common.task.TaskItemEventHandler;
import com.app.todoapp.common.task.TaskViewModel;
import com.app.todoapp.database.task.Task;
import com.app.todoapp.databinding.FragmentIndexBinding;
import com.app.todoapp.state.TaskFilterState;
import com.app.todoapp.state.TaskState;
import com.app.todoapp.state.TaskStateType;
import com.app.todoapp.utils.DateHelper;
import com.google.android.material.search.SearchBar;
import com.google.android.material.search.SearchView;

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
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        // show data
        recyclerViewNotCompletedTask = binding.recyclerviewTaskNotCompleted;
        recyclerViewCompletedTask = binding.recyclerviewTaskCompleted;
        recyclerViewNotCompletedTask.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewCompletedTask.setLayoutManager(new LinearLayoutManager(requireContext()));
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
        // filter action
        Spinner filterSpinner = binding.filter;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(), R.array.taskFilter, R.layout.dropdown_item);
        adapter.setDropDownViewResource(R.layout.dropdown_item);
        filterSpinner.setAdapter(adapter);
        filterSpinner.setOnItemSelectedListener(this.filter(adapter));
        searchBarHandler();
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

    private AdapterView.OnItemSelectedListener filter(ArrayAdapter<CharSequence> adapter) {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView item = (TextView) view;
                String text = String.valueOf(item.getText());
                if (text.equals(getString(R.string.today))) {
                    state.setFilterState(TaskFilterState.DATE, DateHelper.todayLocalDate());
                }
                if (text.equals(getString(R.string.yesterday))) {
                    state.setFilterState(TaskFilterState.DATE, DateHelper.yesterdayLocalDate());
                }
                if (text.equals(getString(R.string.all))) {
                    state.setFilterState(TaskFilterState.ALL, null);
                }
                if (text.equals(getString(R.string.tomorrow))) {
                    state.setFilterState(TaskFilterState.DATE, DateHelper.tomorrowLocalDate());
                }
                taskViewModel.saveState(state);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    public void searchBarHandler() {
        SearchView searchView = binding.searchView;
        SearchBar searchBar = binding.searchBar;
        RecyclerView searchResult = binding.searchRecycleView;
        searchView.getEditText().setOnEditorActionListener((v, actionId, event) -> {
            String query = v.getText().toString();
            searchResult.setLayoutManager(new LinearLayoutManager(requireContext()));
            taskViewModel.searchTask(query).observe(getViewLifecycleOwner(), taskWithCategories -> {
                TaskAdapter adapterForSearchResult = new TaskAdapter(requireContext(), taskWithCategories);
                adapterForSearchResult.setTaskItemEventHandler(this.taskItemEventHandler());
                searchResult.setAdapter(adapterForSearchResult);
            });
            return true;
        });
        searchView.addTransitionListener(
                (v, previousState, newState) -> {
                    if (newState == SearchView.TransitionState.HIDING) {
                        searchResult.setAdapter(null);
                    }
                });
    }
}
