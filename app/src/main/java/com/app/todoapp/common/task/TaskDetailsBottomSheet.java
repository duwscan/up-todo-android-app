package com.app.todoapp.common.task;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.app.todoapp.R;
import com.app.todoapp.database.task.Task;
import com.app.todoapp.database.task.TaskWithCategory;
import com.app.todoapp.utils.DateHelper;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class TaskDetailsBottomSheet {
    public Context context;
    private TextView title;
    private TextView description;
    private TaskWithCategory task;

    private TextView editTaskTime;
    private Long selectionDate;
    private Long selectionDateAndTime;

    private TaskDetailsBottomSheet(Context context) {
        this.context = context;
    }

    public interface OnUpdateTask {
        void updateTask(TaskWithCategory task);
    }

    public interface OnDeleteTask {
        void deleteTask(TaskWithCategory task);
    }

    private OnUpdateTask onUpdateTaskListener;
    private OnDeleteTask onDeleteTaskListener;

    public TaskDetailsBottomSheet setOnUpdateTaskListener(OnUpdateTask onUpdateTaskListener) {
        this.onUpdateTaskListener = onUpdateTaskListener;
        return this;
    }

    public TaskDetailsBottomSheet setOnDeleteTaskListener(OnDeleteTask onDeleteTaskListener) {
        this.onDeleteTaskListener = onDeleteTaskListener;
        return this;
    }

    public static TaskDetailsBottomSheet context(Context context) {
        return new TaskDetailsBottomSheet(context);
    }

    public void showTaskDetails(TaskWithCategory taskWithCategory) {
        task = taskWithCategory;
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        BottomSheetBehavior<View> bottomSheetBehavior;
        View bottomSheetView = bottomSheetDialog.getLayoutInflater().inflate(R.layout.fragment_task_details, null);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        CoordinatorLayout layout = bottomSheetDialog.findViewById(R.id.task_details_fragment);
        assert layout != null;
        layout.getLayoutParams().height = Resources.getSystem().getDisplayMetrics().heightPixels;
        bottomSheetDialog.show();

        // config action bar
        assert onUpdateTaskListener != null;
        Button saveTask = bottomSheetDialog.findViewById(R.id.save);
        assert saveTask != null;
        saveTask.setOnClickListener(v -> {
            onUpdateTaskListener.updateTask(taskWithCategory);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            bottomSheetDialog.cancel();
            bottomSheetDialog.dismiss();
        });
        Button close = bottomSheetDialog.findViewById(R.id.close);
        assert close != null;
        close.setOnClickListener(v -> {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            bottomSheetDialog.cancel();
            bottomSheetDialog.dismiss();
        });
        editTaskTime = bottomSheetDialog.findViewById(R.id.chooseTime);
        assert editTaskTime != null;
        bindTaskInformationToDialog(taskWithCategory, bottomSheetDialog);
        editButtonEventHandler(bottomSheetDialog, taskWithCategory);
        editTaskTime.setOnClickListener(v -> {
            editTaskTime(bottomSheetDialog);
        });
        Button del = bottomSheetDialog.findViewById(R.id.deleteTask);
        assert del != null;
        del.setOnClickListener(v -> {
            this.onDeleteTaskListener.deleteTask(task);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            bottomSheetDialog.cancel();
            bottomSheetDialog.dismiss();
        });

    }


    private void bindTaskInformationToDialog(TaskWithCategory task, BottomSheetDialog dialog) {
        title = dialog.findViewById(R.id.task_details_title);
        String titleText = task.task.getTitle() != null ? task.task.getTitle() : "";
        title.setText(titleText);
        description = dialog.findViewById(R.id.task_details_des);
        String desText = task.task.getDescription() != null ? task.task.getDescription() : "";
        description.setText(desText);
        selectionDate = Date.from(task.task.getDueDate().atTime(task.task.getDueTime()).atZone(ZoneId.systemDefault()).toInstant()).getTime();
        Date date = Date.from(task.task.getDueDate().atTime(task.task.getDueTime()).atZone(ZoneId.systemDefault()).toInstant());
        editTaskTime.setText(DateHelper.formatDate(date));
    }

    private void editButtonEventHandler(BottomSheetDialog dialog, TaskWithCategory task) {
        Button editTitleDes = dialog.findViewById(R.id.editTitleDes);
        editTitleDes.setOnClickListener(v -> {
            editTitleDialog(task, dialog);
        });
    }

    private void editTitleDialog(TaskWithCategory task, BottomSheetDialog bottomSheetDialog) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_title_des_dialog);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.BOTTOM;
        windowAttributes.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(windowAttributes);
        dialog.setCancelable(true);
        dialog.setOnShowListener(dialogInterface -> {
            TextInputLayout textInputLayout = dialog.findViewById(R.id.titleField);
            if (textInputLayout != null) {
                TextInputEditText editText = (TextInputEditText) textInputLayout.getEditText();
                if (editText != null) {
                    editText.requestFocus();
                }
            }
            TextInputEditText titleEditText = dialog.findViewById(R.id.titleInputText);
            titleEditText.setText(task.task.getTitle());
            titleEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    task.task.setTitle(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            TextInputEditText descriptionInputText = dialog.findViewById(R.id.descriptionInputText);
            descriptionInputText.setText(task.task.getDescription());
            descriptionInputText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    task.task.setDescription(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        });
        ImageButton saveEdit = dialog.findViewById(R.id.saveEdit);
        saveEdit.setOnClickListener(v -> {
            title.setText(task.task.getTitle());
            description.setText(task.task.getDescription());
            dialog.cancel();
            dialog.dismiss();
        });
        dialog.show();
    }

    private void editTaskTime(BottomSheetDialog bottomSheetDialog) {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(selectionDate)
                .build();
        datePicker.addOnPositiveButtonClickListener(selection -> {
            selectionDate = selection;
            timePickerDialog(datePicker);
        });
        datePicker.addOnNegativeButtonClickListener(b -> {
            datePicker.dismiss();
        });
        datePicker.addOnCancelListener(dialogPicker -> {
            datePicker.dismiss();
        });
        datePicker.show(((AppCompatActivity) context).getSupportFragmentManager(), datePicker.toString());
    }

    private void timePickerDialog(MaterialDatePicker<Long> datePicker) {
        MaterialTimePicker picker = new MaterialTimePicker.Builder()
                .setHour(task.task.dueTime.getHour())
                .setMinute(task.task.dueTime.getMinute())
                .build();
        picker.addOnNegativeButtonClickListener(v -> {
            picker.dismiss();
        });
        picker.addOnCancelListener(dialog1 -> {
            picker.dismiss();
        });
        picker.addOnPositiveButtonClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(selectionDate);
            calendar.set(Calendar.HOUR_OF_DAY, picker.getHour());
            calendar.set(Calendar.MINUTE, picker.getMinute());
            selectionDateAndTime = calendar.getTime().getTime();
            Instant instant = new Date(selectionDateAndTime).toInstant();
            LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            LocalTime localTime = instant.atZone(ZoneId.systemDefault()).toLocalTime();
            task.task.setDueTime(localTime);
            task.task.setDueDate(localDate);
            editTaskTime.setText(DateHelper.formatDate(new Date(selectionDateAndTime)));
            picker.dismiss();
            datePicker.dismiss();
        });
        picker.show(((AppCompatActivity) context).getSupportFragmentManager(), picker.toString());
    }
}
