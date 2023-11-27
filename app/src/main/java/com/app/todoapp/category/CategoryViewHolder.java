package com.app.todoapp.category;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.todoapp.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout cardCategory;
    public ImageView icon;
    public TextView textView;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        cardCategory = itemView.findViewById(R.id.cardCategory);
        icon = itemView.findViewById(R.id.listIcon);
        textView = itemView.findViewById(R.id.categoryName);
    }
}
