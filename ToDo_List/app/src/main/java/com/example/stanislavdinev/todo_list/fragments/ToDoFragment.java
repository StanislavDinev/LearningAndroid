package com.example.stanislavdinev.todo_list.fragments;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.stanislavdinev.todo_list.ItemAdapter;
import com.example.stanislavdinev.todo_list.data.DataManager;
import com.example.stanislavdinev.todo_list.MainActivity;
import com.example.stanislavdinev.todo_list.R;
import com.example.stanislavdinev.todo_list.data.ToDoElement;


public class ToDoFragment extends BaseFragment implements ItemAdapter.Listener {


    @Override
    protected int getLayoutId() {
        return R.layout.to_do_home_screen;
    }

    @Override
    protected void initUi(View view) {
        ItemAdapter itemAdapter;
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        itemAdapter = new ItemAdapter(DataManager.getInstance().myList, this);
        recyclerView.setAdapter(itemAdapter);
        FloatingActionButton fab = view.findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)
                        getActivity()).setFragmentAndAddToBackStack(OpenToDoFragment.newInstance());
            }
        });

    }

    @Override
    protected void setupToolbar() {
        android.support.v7.app.ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(R.string.to_do_fragment_title);
    }



    @Override
    public void onToDoClick(ToDoElement item) {
        ((MainActivity) getActivity()).setFragmentAndAddToBackStack(OpenToDoFragment.newInstance(item.getId()));
    }
}