package com.app.todoapp.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.NumberPicker;

import com.app.todoapp.R;
import com.app.todoapp.databinding.FragmentFocusBinding;
import com.app.todoapp.databinding.FragmentIndexBinding;

public class FocusFragment extends Fragment {
    FragmentFocusBinding binding;
    private int timeSelected = 0;
    private CountDownTimer timeCountDown;
    private int timeProgress;
    private Long pauseOffset;
    private boolean isStart = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFocusBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.newCount.setOnClickListener(v -> setTimeFunction());
    }

    private void setTimeFunction() {
        Dialog dialog = new Dialog(this.getContext());
        dialog.setContentView(R.layout.count_down_picker_dialog);
        dialogConfig(dialog);
        hoursPicker(dialog);
        minutesPicker(dialog);
        secondsPicker(dialog);
        dialog.show();
    }

    private void dialogConfig(Dialog dialog){
        Window window = dialog.getWindow();
        if(window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
    private void hoursPicker(Dialog dialog) {
        NumberPicker hours = dialog.findViewById(R.id.hours_picker);
        hours.setMaxValue(23);
        hours.setMinValue(0);
    }

    private void minutesPicker(Dialog dialog) {
        NumberPicker minutes = dialog.findViewById(R.id.minutes_picker);
        minutes.setMaxValue(59);
        minutes.setMinValue(0);
    }

    private void secondsPicker(Dialog dialog) {
        NumberPicker seconds = dialog.findViewById(R.id.seconds_picker);
        seconds.setMaxValue(59);
        seconds.setMinValue(0);
    }
}