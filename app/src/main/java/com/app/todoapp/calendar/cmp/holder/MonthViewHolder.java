package com.app.todoapp.calendar.cmp.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.todoapp.R;

public class MonthViewHolder extends RecyclerView.ViewHolder {
    public TextView month;

    public MonthViewHolder(@NonNull View itemView) {
        super(itemView);
        month = itemView.findViewById(R.id.month_view);
    }
}
