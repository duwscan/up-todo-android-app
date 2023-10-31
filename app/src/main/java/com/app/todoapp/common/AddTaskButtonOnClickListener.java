package com.app.todoapp.common;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.app.todoapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddTaskButtonOnClickListener implements View.OnClickListener {
    Context context;

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
            TextInputLayout textInputLayout = dialog.findViewById(R.id.titleField);
            if (textInputLayout != null) {
                TextInputEditText editText = (TextInputEditText) textInputLayout.getEditText();
                if (editText != null) {
                    editText.requestFocus();
                }
            }
        });
        dialog.show();
    }

}
