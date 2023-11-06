package com.app.todoapp.taskscreen;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.app.todoapp.R;

public class DialogDeleteTask extends AppCompatDialogFragment {

    Button btn_cancel_delete,btn_delete_task;
    boolean stage_delete = Boolean.parseBoolean(null);
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_delete_task,null);

        builder.setView(view);

        btn_delete_task = view.findViewById(R.id.btn_edit_delete);

        btn_delete_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stage_delete=true;
                dismiss();
            }
        });

        btn_cancel_delete = view.findViewById(R.id.btn_cancel_delete);

        btn_cancel_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stage_delete=false;
                dismiss();
            }
        });

        return builder.create();
    }

    public boolean isStage_delete(){
        return stage_delete;
    }

}
