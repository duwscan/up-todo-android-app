package com.app.todoapp.category;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.todoapp.common.task.TaskDetailsBottomSheet;
import com.app.todoapp.common.task.TaskItemEventHandler;
import com.app.todoapp.common.task.TaskViewModel;
import com.app.todoapp.database.categories.Category;
import com.app.todoapp.database.task.Task;
import com.app.todoapp.database.task.TaskWithCategory;
import com.app.todoapp.databinding.FragmentCategoryBinding;
import com.app.todoapp.state.TaskStateType;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryFragment extends Fragment {
    private FragmentCategoryBinding binding;
    private Button addNewCategory;
    private CategoryViewModel categoryViewModel;
    private TaskViewModel taskViewModel;

    private RecyclerView categoriesListRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        categoriesListRecyclerView = binding.categoriesList;
        categoriesListRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        addNewCategory = binding.newCategory;
        categoryViewModel.getAll().observe(getViewLifecycleOwner(), categories -> {
            CategoryAdapter categoryAdapter = new CategoryAdapter(requireContext(), categories);
            categoriesListRecyclerView.setAdapter(categoryAdapter);
            categoryAdapter.setCategoryItemHandler((holder, c) -> {
                holder.cardCategory.setOnClickListener(v -> {
                    categoryDetails(c.getUid());
                });
            });
        });
        addNewCategory.setOnClickListener(v -> {
            AddCategoryBottomSheet addCategoryBottomSheet = new AddCategoryBottomSheet(requireContext());
            addCategoryBottomSheet.setOnSaveCategory(category -> {
                categoryViewModel.save(category);
            });
            addCategoryBottomSheet.showCreateForm();
        });
        return view;
    }

    public void categoryDetails(int categoryId) {
        Intent intent = new Intent(getActivity(), CategoryDetailsActivity.class);
        intent.putExtra("categoryID", categoryId);
        startActivity(intent);
    }
}