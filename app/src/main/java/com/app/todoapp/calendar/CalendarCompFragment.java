package com.app.todoapp.calendar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.app.todoapp.calendar.cmp.adapter.DayAdapter;
import com.app.todoapp.calendar.cmp.adapter.MonthAdapter;
import com.app.todoapp.calendar.cmp.holder.DayViewHolder;
import com.app.todoapp.calendar.cmp.model.CallDay;
import com.app.todoapp.calendar.cmp.model.CallMonth;
import com.app.todoapp.calendar.cmp.model.DayItemDecorator;
import com.app.todoapp.calendar.cmp.model.OnDayClicked;
import com.app.todoapp.calendar.cmp.model.OnPickedDate;
import com.app.todoapp.databinding.FragmentCalendarCompBinding;
import com.app.todoapp.focusmode.TimePickerFragment;

import java.time.LocalDate;
import java.time.Month;

public class CalendarCompFragment extends Fragment implements OnDayClicked {

    int currentYear = LocalDate.now().getYear();
    int currentMonth = LocalDate.now().getMonth().getValue();
    int currentDay = LocalDate.now().getDayOfMonth();
    int dayValue;
    FragmentCalendarCompBinding binding;
    int monthValue;
    RecyclerView month;
    RecyclerView days;
    DayAdapter currentDayAdapter;
    LinearLayoutManager monthLayoutManager;
    LinearLayoutManager dayLayoutManager;

    OnPickedDate onPickedDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCalendarCompBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        monthCmpOnCreate();
        binding.years.setText(String.valueOf(currentYear));
        dayCmpOnCreate();
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Fragment x = getParentFragment();
        this.onPickedDate = (OnPickedDate) getParentFragment();
    }

    public void dayCmpOnCreate() {
        days = binding.dayChooser;
        currentDayAdapter = new DayAdapter(requireContext(), currentYear, currentMonth);
        days.setAdapter(currentDayAdapter);
        currentDayAdapter.setClickedPosition(currentDayAdapter.getDayPositionByValue(currentDay));
        currentDayAdapter.setOnDayClicked(this);
        dayLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        days.setLayoutManager(dayLayoutManager);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(days);
    }

    public void dayCmpOnCreated() {
        dayValue = currentDay;
        int pos = currentDayAdapter.getDayPositionByValue(dayValue);
        dayLayoutManager.scrollToPosition(pos);
    }

    public void monthCmpOnCreate() {
        month = binding.monthChooser;
        MonthAdapter monthAdapter = new MonthAdapter(requireContext());
        month.setAdapter(monthAdapter);
        monthLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        month.setLayoutManager(monthLayoutManager);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(month);
        month.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int firstVisibleItemPosition = monthLayoutManager.findFirstVisibleItemPosition();
                    CallMonth selectedItem = monthAdapter.getItemAtPosition(firstVisibleItemPosition);
                    monthValue = selectedItem.getMonthValue();
                    if (dayValue == currentDay && currentMonth == selectedItem.getMonthValue()) {
                        onPickedDate.onPicked(selectedItem.getMonthValue(), dayValue);
                    }
                    currentDayAdapter = new DayAdapter(requireContext(), currentYear, selectedItem.getMonthValue());
                    currentDayAdapter.setOnDayClicked(getThis());
                    days.setAdapter(currentDayAdapter);
                }
            }
        });
    }

    private OnDayClicked getThis() {
        return this;
    }

    public void monthCmpOnCreated() {
        monthValue = currentMonth;
        int currentMonthPosition = CallMonth.getPositionByMonthValue(monthValue);
        monthLayoutManager.scrollToPosition(currentMonthPosition);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        monthCmpOnCreated();
        dayCmpOnCreated();
    }

    @Override
    public void onDayClick(CallDay day, DayViewHolder holder) {
        currentDayAdapter.setClickedPosition(currentDayAdapter.getDayPositionByValue(day.day));
        dayValue = day.day;
        onPickedDate.onPicked(monthValue, dayValue);
    }
}