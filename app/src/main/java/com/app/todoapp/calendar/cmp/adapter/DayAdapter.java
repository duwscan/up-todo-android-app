package com.app.todoapp.calendar.cmp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.app.todoapp.R;
import com.app.todoapp.calendar.cmp.holder.DayViewHolder;
import com.app.todoapp.calendar.cmp.model.CallDay;
import com.app.todoapp.calendar.cmp.model.OnDayClicked;
import com.app.todoapp.utils.DateHelper;

import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayViewHolder> {
    Context context;
    int year;
    int month;
    List<CallDay> days;
    private OnDayClicked onDayClicked;
    private int clickedPosition = -1;

    public void setClickedPosition(int position) {
        if (position != clickedPosition) {
            int previousClickedPosition = clickedPosition;
            clickedPosition = position;
            notifyItemChanged(previousClickedPosition);
            notifyItemChanged(clickedPosition);
        }
    }

    public void setOnDayClicked(OnDayClicked mListener) {
        this.onDayClicked = mListener;
    }

    private int highlightedPosition;

    public void setHighlightedPosition(int highlightedPosition) {
        this.highlightedPosition = highlightedPosition;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DayViewHolder(LayoutInflater.from(context).inflate(R.layout.day_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        CallDay dayItem = days.get(position);
        holder.day.setText(String.valueOf(dayItem.day));
        holder.dayOfWeek.setText(dayItem.dayOfWeek);
        if (position == clickedPosition) {
            // Apply the clicked style
            holder.layout.setBackgroundColor(ContextCompat.getColor(context, R.color.primaryButtonColor));
        } else {
            // Apply the default style
            holder.layout.setBackgroundColor(ContextCompat.getColor(context, R.color.light_dark));
        }
        holder.layout.setOnClickListener(v -> {
            onDayClicked.onDayClick(days.get(position), holder);
        });

    }

    public int getDayPositionByValue(int day) {
        for (int i = 0; i < days.size(); i++) {
            if (days.get(i).day == day) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public CallDay getItemAtPosition(int position) {
        if (position >= 0 && position < days.size()) {
            return days.get(position);
        } else {
            return null;
        }
    }

    public DayAdapter(Context context, int year, int month) {
        this.context = context;
        this.year = year;
        this.month = month;
        this.days = DateHelper.getAllDaysWithDayOfWeekInMonth(year, month);
        System.out.println(days);
    }
}
