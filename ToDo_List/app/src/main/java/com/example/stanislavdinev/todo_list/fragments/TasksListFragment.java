package com.example.stanislavdinev.todo_list.fragments;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.stanislavdinev.todo_list.ItemAdapter;
import com.example.stanislavdinev.todo_list.MainActivity;
import com.example.stanislavdinev.todo_list.Presenters.ToDoPresenter;
import com.example.stanislavdinev.todo_list.R;
import com.example.stanislavdinev.todo_list.TodoApp;
import com.example.stanislavdinev.todo_list.ViewContracts.TaskListView;
import com.example.stanislavdinev.todo_list.data.TaskElement;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.VISIBLE;


public class ToDoFragment extends BaseFragment implements ItemAdapter.Listener, TaskListView {
    private ItemAdapter itemAdapter;
    private View emptyLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.task_list_fragment;
    }

    @Override
    protected void initUi(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        itemAdapter = new ItemAdapter(new ArrayList<TaskElement>(), this);
        recyclerView.setAdapter(itemAdapter);
        emptyLayout = view.findViewById(R.id.empty_layout);

        FloatingActionButton fab = view.findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)
                        getActivity()).setFragmentAndAddToBackStack(OpenToDoFragment.newInstance());
            }
        });
        ToDoPresenter toDoPresenter = new ToDoPresenter(this, TodoApp.getInstance().getDataManager());
        toDoPresenter.loadTasks();
    }

    @Override
    protected void setupToolbar() {
        android.support.v7.app.ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(R.string.to_do_fragment_title);
    }

    @Override
    public void onToDoClick(TaskElement item) {
        ((MainActivity) getActivity()).setFragmentAndAddToBackStack(OpenToDoFragment.newInstance(item.getId()));
    }

    @Override
    public void setToDos(List<TaskElement> toDoList) {
        itemAdapter.setTasks(toDoList);
    }

    @Override
    public void showEmptyLayout() {
        emptyLayout.setVisibility(VISIBLE);
    }
}