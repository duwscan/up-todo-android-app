package com.app.todoapp.category;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.todoapp.R;
import com.app.todoapp.database.categories.Category;

import java.util.List;

public class CategoryPickerDialog extends Dialog {
    private List<Category> categoryList;
    private RecyclerView recyclerViewCategoryList;

    public interface OnPick {
        void on(Category category);
    }

    private Integer defaultPickedCategoryId;
    private OnPick onPick;

    public void setDefaultPickedCategoryId(Integer defaultPickedCategoryId) {
        this.defaultPickedCategoryId = defaultPickedCategoryId;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public void setOnPick(OnPick onPick) {
        this.onPick = onPick;
    }

    public CategoryPickerDialog(@NonNull Context context) {
        super(context);
        this.setContentView(R.layout.category_picker);
        this.setOnShowListener(dialog -> {
            recyclerViewCategoryList = this.findViewById(R.id.categoriesList);
            recyclerViewCategoryList.setLayoutManager(new LinearLayoutManager(context));
            CategoryPickerAdapter categoryAdapter = new CategoryPickerAdapter(context, categoryList);
            categoryAdapter.setCategoryItemHandler((holder, category) -> {
                if (defaultPickedCategoryId != null && category.getUid() == defaultPickedCategoryId) {
                    holder.isCheck.setVisibility(View.VISIBLE);
                } else {
                    holder.isCheck.setVisibility(View.INVISIBLE);
                }
                holder.cardCategory.setOnClickListener(v -> {
                    if (holder.isCheck.getVisibility() == View.VISIBLE) {
                        // when user click the check cate -> set to uncheck
                        onPick.on(null);
                        return;
                    }
                    onPick.on(category);
                });
            });
            recyclerViewCategoryList.setAdapter(categoryAdapter);
        });
    }

    public void show(Integer defaultPickedCategoryId) {
        this.defaultPickedCategoryId = defaultPickedCategoryId;
        super.show();
    }
}
