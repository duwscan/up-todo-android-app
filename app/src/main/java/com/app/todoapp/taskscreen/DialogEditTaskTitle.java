package com.app.todoapp.taskscreen;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.app.todoapp.R;

public class DialogEditTaskTitle extends AppCompatDialogFragment {

    DialogEditTaskTitleInterface dialogEditTaskTitleInterface;
    Button btn_cancel_edit_task,btn_save_edit_task;
    EditText editTextTitle,editTextDiscription;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_task_title,null);

        builder.setView(view);


        editTextTitle = view.findViewById(R.id.edt_edit_title);
        editTextDiscription = view.findViewById(R.id.edt_edit_discription);

        btn_save_edit_task = view.findViewById(R.id.btn_save_edit_task);
        btn_save_edit_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String title = editTextTitle.getText().toString();
               String description = editTextDiscription.getText().toString();

               dialogEditTaskTitleInterface.applyData(title,description);
               dismiss();
            }
        });
        btn_cancel_edit_task = view.findViewById(R.id.btn_cancel_edit_task);
        btn_cancel_edit_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dialogEditTaskTitleInterface =(DialogEditTaskTitleInterface) context;
    }

    public interface DialogEditTaskTitleInterface{
        void applyData(String title,String description);
    }

}
