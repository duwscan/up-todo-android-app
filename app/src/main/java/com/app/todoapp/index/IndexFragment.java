package com.app.todoapp.index;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.app.todoapp.R;
import com.app.todoapp.databinding.FragmentIndexBinding;

public class IndexFragment extends Fragment {
    FragmentIndexBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentIndexBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Spinner filterSpinner = binding.filter;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.taskFilter, R.layout.dropdown_item);
        adapter.setDropDownViewResource(R.layout.dropdown_item);
        filterSpinner.setAdapter(adapter);
        return view;
    }
}