package com.app.todoapp.category;

import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.todoapp.R;
import com.app.todoapp.common.task.TaskAdapter;
import com.app.todoapp.common.task.TaskDetailsBottomSheet;
import com.app.todoapp.common.task.TaskItemEventHandler;
import com.app.todoapp.common.task.TaskViewModel;
import com.app.todoapp.database.categories.Category;
import com.app.todoapp.database.task.Task;
import com.app.todoapp.databinding.FragmentCategoryDetailBinding;

import java.util.stream.Collectors;

public class CategoryDetailsActivity extends AppCompatActivity {
    private FragmentCategoryDetailBinding binding;
    private TaskViewModel taskViewModel;
    private RecyclerView taskBelongTo;
    private int categoryId;
    private Category category;
    private CategoryViewModel categoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentCategoryDetailBinding.inflate(getLayoutInflater());
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskBelongTo = binding.taskOfCategory;
        taskBelongTo.setLayoutManager(new LinearLayoutManager(this));
        setContentView(binding.getRoot());
        categoryId = getIntent().getIntExtra("categoryID", 0);
        bindingData();
        initButtons();
    }

    public void bindingData() {
        categoryViewModel.getById(categoryId).observe(this, category -> {
            this.category = category;
            taskViewModel.getAll().observe(this, taskWithCategories -> {
                TaskAdapter taskAdapter = new TaskAdapter(this, taskWithCategories.stream()
                        .filter(taskWithCategory -> taskWithCategory.category != null && taskWithCategory.category.getUid() == category.getUid())
                        .collect(Collectors.toList()));
                taskBelongTo.setAdapter(taskAdapter);
                taskAdapter.setTaskItemEventHandler(this.taskItemEventHandler());
            });
            if (category != null && category.getName() != null) {
                binding.categoryName.setText(category.getName());
                initEditButton();
                setColorForListIcon(binding.listIcon, Integer.parseInt(category.getIcon()));
            }
        });
    }


    private void initButtons() {
        Button close = binding.close;
        close.setOnClickListener(v -> {
            onBackPressed();
        });
        Button del = binding.deleteCategory;
        del.setOnClickListener(v -> {
            categoryViewModel.delete(category);
            onBackPressed();
        });
    }

    public void initEditButton() {
        Button edit = binding.editCategory;
        edit.setOnClickListener(v -> {
            EditCategoryBottomSheet editCategoryBottomSheet = new EditCategoryBottomSheet(this);
            editCategoryBottomSheet.setOnSaveCategory(category1 -> {
                categoryViewModel.save(category1);
            });
            editCategoryBottomSheet.show(category);
        });
    }

    private TaskItemEventHandler taskItemEventHandler() {
        return ((item, selector) -> {
            selector.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                item.task.setCompleted(isChecked);
                onSaveTask(item.task);
            });
            selector.card.setOnClickListener(v -> {
                // open details dialog
                categoryViewModel.getAll().observe(this, categoryList -> {
                    TaskDetailsBottomSheet.context(this)
                            .setCategoryList(categoryList)
                            .setOnUpdateTaskListener(task -> {
                                this.onSaveTask(task.task);
                            }).setOnDeleteTaskListener(task -> {
                                this.deleteTask(task.task);
                            })
                            .showTaskDetails(item);
                });
            });
        });
    }

    public void onSaveTask(Task task) {
        taskViewModel.saveTask(task);
    }

    public void deleteTask(Task task) {
        taskViewModel.deleteTask(task);
    }

    public void setColorForListIcon(ImageView icon, int color) {
        icon.setImageResource(R.drawable.baseline_format_list_bulleted_24);
        icon.setColorFilter(ContextCompat.getColor(this, android.R.color.white), PorterDuff.Mode.SRC_IN);
        GradientDrawable shapeDrawable = new GradientDrawable();
        shapeDrawable.setShape(GradientDrawable.OVAL);
        shapeDrawable.setStroke(2, color);
        shapeDrawable.setColor(color);
        icon.setBackground(shapeDrawable);
    }
}
