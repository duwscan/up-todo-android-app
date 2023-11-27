package com.app.todoapp.calendar.cmp.holder;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.todoapp.R;

public class DayViewHolder extends RecyclerView.ViewHolder {
    public TextView dayOfWeek;
    public TextView day;
    public FrameLayout layout;

    public DayViewHolder(@NonNull View itemView) {
        super(itemView);
        dayOfWeek = itemView.findViewById(R.id.dayOfWeekText);
        day = itemView.findViewById(R.id.dayText);
        layout = itemView.findViewById(R.id.day_wrapper);
    }
}
