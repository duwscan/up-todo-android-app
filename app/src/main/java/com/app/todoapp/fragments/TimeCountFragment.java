package com.app.todoapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.todoapp.databinding.FragmentFocusModeTimeCountBinding;
import com.app.todoapp.utils.TimePickerValue;

import java.util.Locale;

public class TimeCountFragment extends Fragment {
    FragmentFocusModeTimeCountBinding binding;
    private TimePickerValue initialTimePickerValue;
    private TimePickerValue countTimePickerValue;
    private CountDownTimer countDownTimer;
    private TimeCountDownListener timeCountDownListener;

    public interface TimeCountDownListener {
        void onCountDownFinish();

        boolean forceReset();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentFocusModeTimeCountBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        countTimePickerValue = initialTimePickerValue;
        startTimer();
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.timeCountDownListener = (TimeCountDownListener) getParentFragment();
    }

    public void startTimer() {
        if (timeCountDownListener.forceReset()) {
            countDownTimer.cancel();
            countTimePickerValue = initialTimePickerValue;
        }
        countDownTimer = new CountDownTimer(countTimePickerValue.toMillis(), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countTimePickerValue = TimePickerValue.fromMillis(millisUntilFinished);
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeCountDownListener.onCountDownFinish();
            }
        }.start();
    }

    public void pauseTimer() {
        countDownTimer.cancel();
    }

    private void updateCountDownText() {
        binding.clockTimer.setText(countTimePickerValue.formatTimeWithHours());
    }

    public void receiveTime(TimePickerValue timePickerValue) {
        this.initialTimePickerValue = timePickerValue;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        countDownTimer.cancel();
    }
}