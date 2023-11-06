package com.app.todoapp.taskscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.app.todoapp.MainActivity;
import com.app.todoapp.R;
import com.app.todoapp.databinding.ActivityTaskScreenBinding;

import java.util.zip.Inflater;

public class TaskScreen extends AppCompatActivity implements DialogEditTaskTitle.DialogEditTaskTitleInterface{

    ActivityTaskScreenBinding binding;
    boolean isImage = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskScreenBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();

        binding.layoutDelete.setOnClickListener(v -> {
            OpenDialogDelete();
        });

        binding.imagebtnEdit.setOnClickListener(v -> openDialogEditTaskTitle());


        binding.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(TaskScreen.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(TaskScreen.this, "Update task complete", Toast.LENGTH_SHORT).show();
        });


        binding.imagebtnClose.setOnClickListener(v -> {
            Intent intent = new Intent(TaskScreen.this, MainActivity.class);
            startActivity(intent);
        });


        binding.imageviewEditNotify.setOnClickListener(v -> {

            if (isImage) {
                binding.imageviewEditNotify.setImageResource(R.drawable.baseline_notifications_active_24);
            } else {
                binding.imageviewEditNotify.setImageResource(R.drawable.baseline_notifications_24);
            }
            isImage = !isImage;
        });



        setContentView(view);
    }

    private void openDialogEditTaskTitle() {
        DialogEditTaskTitle dialogEditTaskTitle = new DialogEditTaskTitle();
        dialogEditTaskTitle.show(getSupportFragmentManager(),null);


    }

    private void OpenDialogDelete() {
        DialogDeleteTask dialogDeleteTask = new DialogDeleteTask();
        dialogDeleteTask.isStage_delete();
        dialogDeleteTask.show(getSupportFragmentManager(),null);
    }


    @Override
    public void applyData(String title, String description) {
        Toast.makeText(this,title+description,Toast.LENGTH_SHORT).show();
    }
}