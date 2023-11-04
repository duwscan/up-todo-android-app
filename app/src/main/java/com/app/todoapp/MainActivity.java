package com.app.todoapp;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.app.todoapp.common.task.AddTaskButtonOnClickListener;
import com.app.todoapp.common.task.OnSaveTask;
import com.app.todoapp.common.task.TaskViewModel;
import com.app.todoapp.databinding.ActivityMainBinding;
import com.app.todoapp.focusmode.FocusFragment;
import com.app.todoapp.index.IndexFragment;
import com.app.todoapp.state.TaskState;

public class MainActivity extends AppCompatActivity {
    final static int INDEX_FRAGMENT = R.id.index;
    final static int CALENDAR_FRAGMENT = R.id.calendar;
    final static int FOCUS_FRAGMENT = R.id.focuse;
    final static int SETTINGS_FRAGMENT = R.id.settings;
    private ActivityMainBinding binding;
    private TextView titleHeader;
    private static final int POST_NOTIFICATIONS_PERMISSIONS_REQUEST_CODE = 123;
    private TaskViewModel taskViewModel;
    private TaskState state = new TaskState();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.saveState(state);
        // initial fragment
        titleHeader = binding.headerTitle;
        MenuItem initialFragment = binding.bottomNavigationView.getMenu().getItem(0);
        titleHeader.setText(initialFragment.getTitle());
        replaceFragment(new IndexFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            titleHeader.setText(item.getTitle());
            if (itemId == INDEX_FRAGMENT) {
                replaceFragment(new IndexFragment());
            }
            if (itemId == FOCUS_FRAGMENT) {
                replaceFragment(new FocusFragment());
            }
            return true;
        });
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, POST_NOTIFICATIONS_PERMISSIONS_REQUEST_CODE);
        }
        // send timer count down notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("default_channel_id", "Uptodo", importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        // set add task FAB- floating action button event
        AddTaskButtonOnClickListener addTaskButtonOnClickListener = new AddTaskButtonOnClickListener(this);
        addTaskButtonOnClickListener.setOnSaveTask(task -> {
            try {
                OnSaveTask handler = (OnSaveTask) this.getSupportFragmentManager().findFragmentById(R.id.fragment_content);
                if (handler != null) {
                    handler.onSaveTask(task);
                }
            } catch (Exception e) {
            }
        });
        binding.addTaskFAB.setOnClickListener(addTaskButtonOnClickListener);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_content, fragment);
        fragmentTransaction.commit();
    }
}