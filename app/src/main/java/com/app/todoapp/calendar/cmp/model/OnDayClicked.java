package com.app.todoapp.calendar.cmp.model;

import com.app.todoapp.calendar.cmp.holder.DayViewHolder;

public interface OnDayClicked {
    void onDayClick(CallDay day, DayViewHolder viewHolder);
}
