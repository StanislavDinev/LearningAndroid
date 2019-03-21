package com.example.stanislavdinev.task_list.fragments;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.stanislavdinev.task_list.TaskAdapter;
import com.example.stanislavdinev.task_list.MainActivity;
import com.example.stanislavdinev.task_list.Presenters.TasksListPresenter;
import com.example.stanislavdinev.task_list.R;
import com.example.stanislavdinev.task_list.TaskApp;
import com.example.stanislavdinev.task_list.ViewContracts.TaskListView;
import com.example.stanislavdinev.task_list.data.Task;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.VISIBLE;


public class TasksListFragment extends BaseFragment implements TaskAdapter.Listener, TaskListView {
    private TaskAdapter taskAdapter;
    private View emptyLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.tasks_list_fragment;
    }

    @Override
    protected void initUi(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        taskAdapter = new TaskAdapter(new ArrayList<Task>(), this);
        recyclerView.setAdapter(taskAdapter);
        emptyLayout = view.findViewById(R.id.empty_layout);
        FloatingActionButton fab = view.findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)
                        getActivity()).setFragmentAndAddToBackStack(OpenTaskFragment.newInstance());
            }
        });
        TasksListPresenter tasksListPresenter = new TasksListPresenter(this, TaskApp.getInstance().getTaskDataManager());
        tasksListPresenter.loadTasks();
    }

    @Override
    protected void setupToolbar() {
        android.support.v7.app.ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(R.string.task_fragment_title);
    }

    @Override
    public void onTask(Task task) {
        ((MainActivity) getActivity()).setFragmentAndAddToBackStack(OpenTaskFragment.newInstance(task.getId()));
    }

    @Override
    public void setTasks(List<Task> taskList) {
        taskAdapter.setTasks(taskList);
    }

    @Override
    public void showEmptyLayout() {
        emptyLayout.setVisibility(VISIBLE);
    }
}