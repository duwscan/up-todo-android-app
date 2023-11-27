package com.app.todoapp.common.task;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.todoapp.R;
import com.app.todoapp.category.CategoryPickerDialog;
import com.app.todoapp.database.categories.Category;
import com.app.todoapp.database.task.Task;
import com.app.todoapp.utils.DateHelper;
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
import java.util.List;
import java.util.Objects;

public class AddTaskButtonOnClickListener implements View.OnClickListener {
    Context context;
    private List<Category> categoryList;

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    Category initialCategory;
    private final String DUE_DATE = "Due Date: ";
    private String title = "";
    private String description = "";
    private long selectedDate = MaterialDatePicker.todayInUtcMilliseconds();
    private Date selectedTimeAndDate;
    private OnSaveTask onSaveTask;
    private Task newTask;

    public void setOnSaveTask(OnSaveTask onSaveTask) {
        this.onSaveTask = onSaveTask;
    }

    public AddTaskButtonOnClickListener(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        openAddTaskDialog();
    }

    private void openAddTaskDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_add_task_dialog);
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
            TextView text = dialog.findViewById(R.id.dueDateText);
            if (selectedTimeAndDate != null) {
                text.setVisibility(View.VISIBLE);
                text.setText(DUE_DATE + DateHelper.formatDate(selectedTimeAndDate));
            } else {
                text.setVisibility(View.GONE);
            }
            TextInputLayout textInputLayout = dialog.findViewById(R.id.titleField);
            if (textInputLayout != null) {
                TextInputEditText editText = (TextInputEditText) textInputLayout.getEditText();
                if (editText != null) {
                    editText.requestFocus();
                }
            }
            TextInputEditText titleEditText = dialog.findViewById(R.id.titleInputText);
            if (!title.equals("")) {
                titleEditText.setText(title);
            }

            titleEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    title = s.toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            TextInputEditText descriptionInputText = dialog.findViewById(R.id.descriptionInputText);
            if (!description.equals("")) {
                descriptionInputText.setText(description);
            }
            descriptionInputText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    description = s.toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        });
        TextView textView = dialog.findViewById(R.id.choosenCate);
        if (initialCategory != null) {
            textView.setVisibility(View.VISIBLE);
            textView.setText("Category: " + initialCategory.getName());
        } else {
            textView.setVisibility(View.GONE);
        }

        datePickerOnDialog(dialog);
        saveButton(dialog);
        categoryDialog(dialog);
        dialog.show();
    }

    public void datePickerOnDialog(Dialog dialog) {
        ImageButton imageButton = dialog.findViewById(R.id.timerPicker);
        imageButton.setOnClickListener(v -> {
            dialog.cancel();
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(Objects.requireNonNull(dialog.getWindow()).getDecorView().getWindowToken(), 0);
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setSelection(selectedDate)
                    .build();
            datePicker.addOnPositiveButtonClickListener(selection -> {
                selectedDate = selection;
                timePickerDialog(dialog, datePicker);
                datePicker.dismiss();
            });
            datePicker.addOnNegativeButtonClickListener(b -> {
                openAddTaskDialog();
            });
            datePicker.addOnCancelListener(dialogPicker -> {
                openAddTaskDialog();
            });
            datePicker.show(((AppCompatActivity) context).getSupportFragmentManager(), datePicker.toString());
        });
    }

    private void timePickerDialog(Dialog dialog, MaterialDatePicker<Long> datePicker) {
        MaterialTimePicker picker = new MaterialTimePicker.Builder()
                .build();
        picker.addOnNegativeButtonClickListener(v -> {
            datePicker.show(((AppCompatActivity) context).getSupportFragmentManager(), datePicker.toString());
        });
        picker.addOnCancelListener(dialog1 -> {
            datePicker.show(((AppCompatActivity) context).getSupportFragmentManager(), datePicker.toString());
        });
        picker.addOnPositiveButtonClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(selectedDate);
            calendar.set(Calendar.HOUR_OF_DAY, picker.getHour());
            calendar.set(Calendar.MINUTE, picker.getMinute());
            selectedTimeAndDate = calendar.getTime();
            picker.dismiss();
            openAddTaskDialog();
        });
        picker.show(((AppCompatActivity) context).getSupportFragmentManager(), picker.toString());
    }

    private void saveButton(Dialog dialog) {
        dialog.findViewById(R.id.saveTask).setOnClickListener(v -> {
            String errorMessage = "";
            if (title == null || title.equals("")) {
                errorMessage = "You must fill your task title";
            } else if (selectedTimeAndDate == null) {
                errorMessage = "Please set up your due date";
            }
            if (!errorMessage.isEmpty()) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                return;
            }
            newTask = new Task();
            newTask.setCompleted(false);
            newTask.setTitle(title);
            newTask.setDescription(description);
            newTask.setPriority("Do first");
            Instant instant = selectedTimeAndDate.toInstant();
            LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            LocalTime localTime = instant.atZone(ZoneId.systemDefault()).toLocalTime();
            newTask.setDueDate(localDate);
            newTask.setDueTime(localTime);
            if (initialCategory != null) {
                newTask.setBelongToCategoryId((long) initialCategory.getUid());
            }
            onSaveTask.onSaveTask(newTask);
            clearPayload();
            dialog.cancel();
        });
    }

    private void clearPayload() {
        title = "";
        description = "";
        selectedTimeAndDate = null;
        initialCategory = null;
    }

    public void categoryDialog(Dialog dialog) {
        ImageButton categoryPicker = dialog.findViewById(R.id.categoryPicker);
        categoryPicker.setOnClickListener(v -> {
            dialog.cancel();
            CategoryPickerDialog categoryPickerDialog = new CategoryPickerDialog(context);
            categoryPickerDialog.setCategoryList(categoryList);
            categoryPickerDialog.setDefaultPickedCategoryId(initialCategory != null ? initialCategory.getUid() : null);
            categoryPickerDialog.setOnPick(category -> {
                initialCategory = category;
                categoryPickerDialog.dismiss();
                categoryPickerDialog.cancel();
            });
            categoryPickerDialog.show(initialCategory != null ? initialCategory.getUid() : null);
            categoryPickerDialog.setOnCancelListener(dialog1 -> {
                openAddTaskDialog();
            });
        });
    }

}
