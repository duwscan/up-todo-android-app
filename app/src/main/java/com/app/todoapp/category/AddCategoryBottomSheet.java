package com.app.todoapp.category;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.app.todoapp.R;
import com.app.todoapp.database.categories.Category;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import yuku.ambilwarna.AmbilWarnaDialog;

public class AddCategoryBottomSheet {
    private Context context;
    private BottomSheetDialog bottomSheetDialog;
    int defaultColor;
    private EditText categoryName;

    public interface OnSaveCategory {
        void saveCategory(Category category);
    }

    private OnSaveCategory onSaveCategory;

    public void setOnSaveCategory(OnSaveCategory onSaveCategory) {
        this.onSaveCategory = onSaveCategory;
    }

    public AddCategoryBottomSheet(Context context) {
        this.context = context;
    }

    public void showCreateForm() {
        bottomSheetDialog = new BottomSheetDialog(context);
        BottomSheetBehavior<View> bottomSheetBehavior;
        View bottomSheetView = bottomSheetDialog.getLayoutInflater().inflate(R.layout.add_category_layout, null);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        CoordinatorLayout layout = bottomSheetDialog.findViewById(R.id.task_details_fragment);
        assert layout != null;
        layout.getLayoutParams().height = Resources.getSystem().getDisplayMetrics().heightPixels;
        bottomSheetDialog.show();
        categoryName = bottomSheetDialog.findViewById(R.id.categoryName);
        Button saveTask = bottomSheetDialog.findViewById(R.id.save);
        saveTask.setOnClickListener(v -> {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            String categoryName = this.categoryName.getText().toString();
            Category category = new Category();
            category.setName(categoryName);
            category.setIcon(String.valueOf(defaultColor));
            onSaveCategory.saveCategory(category);
            bottomSheetDialog.cancel();
            bottomSheetDialog.dismiss();
        });
        Button close = bottomSheetDialog.findViewById(R.id.close);
        assert close != null;
        close.setOnClickListener(v -> {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            bottomSheetDialog.cancel();
            bottomSheetDialog.dismiss();
        });
        colorPicker();
    }

    public void colorPicker() {
        TextView colorPreview = bottomSheetDialog.findViewById(R.id.chooseColor);
        defaultColor = ContextCompat.getColor(context, R.color.primaryButtonColor);
        assert colorPreview != null;
        colorPreview.setBackgroundColor(defaultColor);
        colorPreview.setOnClickListener(v -> {
            AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(context, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                @Override
                public void onCancel(AmbilWarnaDialog dialog) {
                }

                @Override
                public void onOk(AmbilWarnaDialog dialog, int color) {
                    defaultColor = color;
                    colorPreview.setBackgroundColor(color);
                }
            });
            ambilWarnaDialog.show();
        });
    }


}
