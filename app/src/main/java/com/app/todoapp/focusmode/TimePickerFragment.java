package com.app.todoapp.focusmode;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.app.todoapp.databinding.FragmentFocusModeTimePickerBinding;
import com.app.todoapp.utils.TimePickerValue;

public class TimePickerFragment extends Fragment {
    FragmentFocusModeTimePickerBinding binding;
    NumberPicker hours;
    NumberPicker minutes;
    NumberPicker seconds;
    int initialHours = 0;
    int initialMinutes = 0;
    int initialSeconds = 0;
    PickedTime pickedTime;

    TimePickerValue pickerValue = new TimePickerValue();

    public interface PickedTime {
        void pickedTime(TimePickerValue value);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.pickedTime = (PickedTime) getParentFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFocusModeTimePickerBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        hours = binding.hoursPicker;
        minutes = binding.minutesPicker;
        seconds = binding.secondsPicker;
        timePickerConfig();
        pickedTime.pickedTime(new TimePickerValue(0, 0, 0));
        timePickerGroupEvent();
        return view;
    }

    public void timePickerGroupEvent() {
        hours.setOnValueChangedListener((picker, oldVal, newVal) -> {
            pickerValue.setHours(newVal);
            pickedTime.pickedTime(pickerValue);
        });
        minutes.setOnValueChangedListener((picker, oldVal, newVal) -> {
            pickerValue.setMinutes(newVal);
            pickedTime.pickedTime(pickerValue);
        });
        seconds.setOnValueChangedListener((picker, oldVal, newVal) -> {
            pickerValue.setSeconds(newVal);
            pickedTime.pickedTime(pickerValue);
        });
    }

    public void timePickerConfig() {
        hours.setMinValue(0);
        hours.setMaxValue(23);
        hours.setValue(initialHours);
        minutes.setMinValue(0);
        minutes.setMaxValue(59);
        minutes.setValue(initialMinutes);
        seconds.setMinValue(0);
        seconds.setMaxValue(59);
        minutes.setValue(initialSeconds);
    }
}
