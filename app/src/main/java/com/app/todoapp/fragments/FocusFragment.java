package com.app.todoapp.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.todoapp.R;
import com.app.todoapp.database.state.FocusStateType;
import com.app.todoapp.databinding.FragmentFocusBinding;
import com.app.todoapp.receiver.AlarmReceiver;
import com.app.todoapp.utils.TimePickerValue;

public class FocusFragment extends Fragment implements TimePickerFragment.PickedTime, TimeCountFragment.TimeCountDownListener {
    FragmentFocusBinding binding;
    Button startButton;
    Button cancelButton;
    private FocusStateType stateType = FocusStateType.CANCEL;
    private TextView clock;
    private TimeCountFragment nextTimeCountFragment;
    private TimePickerFragment timePickerFragment;
    private TimePickerValue timePickerValue;
    String time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFocusBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        startButton = binding.startCount;
        cancelButton = binding.cancelTimer;
        cancelButton.setEnabled(false);
        startButton.setOnClickListener(this::startButtonEvent);
        cancelButton.setOnClickListener(v -> {
            stateType = FocusStateType.CANCEL;
            replaceFragment(new TimePickerFragment());
            startButton.setText(R.string.start);
            cancelButton.setEnabled(false);
        });
        return view;
    }

    void startButtonEvent(View view) {
        if (stateType.equals(FocusStateType.START)) {
            // do pause
            startButton.setText(R.string.continueCount);
            stateType = FocusStateType.PAUSE;
            nextTimeCountFragment.pauseTimer();
        } else if (stateType.equals(FocusStateType.PAUSE)) {
            nextTimeCountFragment.startTimer();
            startButton.setText(R.string.pause);
            stateType = FocusStateType.START;
        } else if (stateType.equals(FocusStateType.RESET)) {
            stateType = FocusStateType.RESET;
            nextTimeCountFragment.startTimer();
        } else {
            if (!timePickerValue.isValidate()) {
                Toast.makeText(getContext(), "Please set your time", Toast.LENGTH_SHORT).show();
                return;
            }
            nextTimeCountFragment.receiveTime(timePickerValue);
            replaceFragment(nextTimeCountFragment);
            startButton.setText(R.string.pause);
            stateType = FocusStateType.START;
        }
        cancelButton.setEnabled(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timePickerFragment = new TimePickerFragment();
        nextTimeCountFragment = new TimeCountFragment();
        replaceFragment(timePickerFragment);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.focuse_mode_fragment_content, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void pickedTime(TimePickerValue value) {
        timePickerValue = value;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onCountDownFinish() {
        stateType = FocusStateType.RESET;
        startButton.setText(R.string.reset);
        Context context = getContext();
        if (context == null) {
            // Handle the case where the context is not available
            return;
        }

        // Get the AlarmManager
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Create an Intent with the appropriate context
        Intent intent = new Intent(context, AlarmReceiver.class);

        // Adjust the request code and flags based on your specific requirements
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Ensure that the alarm is properly triggered based on your logic
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
        }
    }

    @Override
    public boolean forceReset() {
        return stateType.equals(FocusStateType.RESET);
    }
}