package com.example.stanislavdinev.task_list.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), null);

        initUi(view);
        setupToolbar();

        return view;
    }

    protected abstract int getLayoutId();

    protected abstract void initUi(View view);

    protected abstract void setupToolbar();

}
