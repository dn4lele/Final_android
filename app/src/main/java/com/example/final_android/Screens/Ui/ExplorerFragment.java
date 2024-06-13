package com.example.final_android.Screens.Ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.final_android.R;
import com.example.final_android.databinding.FragmentExplorerBinding;


public class ExplorerFragment extends Fragment {

    FragmentExplorerBinding binding;
    ExplorerViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentExplorerBinding.inflate(inflater , container , false);
        viewModel = new ViewModelProvider(this).get(ExplorerViewModel.class);

        return binding.getRoot();
    }

}