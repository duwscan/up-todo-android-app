package com.app.todoapp.category;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.app.todoapp.R;
import com.app.todoapp.database.categories.Category;

import java.util.List;

public class CategoryPickerAdapter extends RecyclerView.Adapter<CategoryPickerViewHolder> {
    protected final Context context;
    private final List<Category> categories;

    public interface CategoryItemHandler {
        void handler(CategoryPickerViewHolder holder, Category category);
    }

    public void setCategoryItemHandler(CategoryItemHandler categoryItemHandler) {
        this.categoryItemHandler = categoryItemHandler;
    }

    private CategoryItemHandler categoryItemHandler;

    public CategoryPickerAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryPickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryPickerViewHolder(LayoutInflater.from(this.context).inflate(R.layout.category_picker_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryPickerViewHolder holder, int position) {
        Category category = this.categories.get(position);
        setColorForListIcon(holder.icon, Integer.parseInt(category.getIcon()));
        holder.textView.setText(category.getName());
        categoryItemHandler.handler(holder, category);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setColorForListIcon(ImageView icon, int color) {
        icon.setImageResource(R.drawable.baseline_format_list_bulleted_24);
        icon.setColorFilter(ContextCompat.getColor(context, android.R.color.white), PorterDuff.Mode.SRC_IN);
        GradientDrawable shapeDrawable = new GradientDrawable();
        shapeDrawable.setShape(GradientDrawable.OVAL);
        shapeDrawable.setStroke(2, color);
        shapeDrawable.setColor(color);
        icon.setBackground(shapeDrawable);
    }

}
