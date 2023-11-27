package com.app.todoapp.category;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.todoapp.R;

public class CategoryPickerViewHolder extends CategoryViewHolder {

    Button isCheck;

    public CategoryPickerViewHolder(@NonNull View itemView) {
        super(itemView);
        isCheck = itemView.findViewById(R.id.check);
    }
}

