package com.app.todoapp.calendar.cmp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.todoapp.R;
import com.app.todoapp.calendar.cmp.holder.MonthViewHolder;
import com.app.todoapp.calendar.cmp.model.CallMonth;

import java.util.List;

public class MonthAdapter extends RecyclerView.Adapter<MonthViewHolder> {
    Context context;
    List<CallMonth> months = CallMonth.monthsInYear();

    @NonNull
    @Override
    public MonthViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MonthViewHolder(LayoutInflater.from(context).inflate(R.layout.month_layout, parent, false));
    }

    public CallMonth getItemAtPosition(int position) {
        if (position >= 0 && position < months.size()) {
            return months.get(position);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MonthViewHolder holder, int position) {
        CallMonth month = months.get(position);
        holder.month.setText(month.getMonthName());
    }

    @Override
    public int getItemCount() {
        return months.size();
    }

    public MonthAdapter(Context context) {
        this.context = context;
    }
}
