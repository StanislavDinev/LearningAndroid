package com.example.stanislavdinev.task_list.fragments;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stanislavdinev.task_list.Presenters.OpenTaskPresenter;
import com.example.stanislavdinev.task_list.TaskApp;
import com.example.stanislavdinev.task_list.MainActivity;
import com.example.stanislavdinev.task_list.R;
import com.example.stanislavdinev.task_list.ViewContracts.OpenTaskView;
import com.example.stanislavdinev.task_list.data.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class OpenTaskFragment extends BaseFragment implements OpenTaskView {
    private static final int DEFAULT_NEGATIVE_VALUE = -1;
    private static final String ARG_ID = "ARG_ID";
    private int id = DEFAULT_NEGATIVE_VALUE;
    private TextView mButton;
    private EditText taskTitle;
    private EditText taskDescription;
    private TextView taskDate;
    private OpenTaskPresenter openTaskPresenter = new OpenTaskPresenter(this, TaskApp.getInstance().getTaskDataManager());


    public static OpenTaskFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);

        OpenTaskFragment openTaskFragment = new OpenTaskFragment();
        openTaskFragment.setArguments(args);
        return openTaskFragment;
    }


    public static OpenTaskFragment newInstance() {
        return newInstance(DEFAULT_NEGATIVE_VALUE);
    }


    @Override
    protected void initUi(View view) {
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_ID);
        }
        taskTitle = view.findViewById(R.id.title);
        taskDescription = view.findViewById(R.id.description);
        taskDate = view.findViewById(R.id.date);
        openTaskPresenter.setupEditText(id);

        taskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker datePicker, int year,
                                                  int month, int day) {
                                taskDate.setText(day + "."
                                        + (month + 1) + "." + year);
                            }
                        }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                datePickerDialog.show();

                openTaskPresenter.parseDate(taskDate.getText().toString());
            }
        });
        mButton = view.findViewById(R.id.save_button);
        mButton.setText(openTaskPresenter.isEditMode(id) ? R.string.save : R.string.add);
        onButtonClick();
    }

    @Override
    public void setupEditText(Task task) {
        taskTitle.setText(task.getTitle());
        if (task.getDescription() != null) {
            taskDescription.setText(task.getDescription());
        }
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy");
        if (task.getDate() != 0) {
            taskDate.setText(f.format(task.getDate()));
        }
    }

    @Override
    public void setupToolbar() {
        setHasOptionsMenu(true);
        android.support.v7.app.ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        if (openTaskPresenter.isEditMode(id)) {
            actionBar.setTitle(R.string.edit_fragment_title);
        } else {
            actionBar.setTitle(R.string.add_fragment_title);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.open_task_fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (openTaskPresenter.isEditMode(id)) {
            inflater.inflate(R.menu.edit_screen_action_bar, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }


    private void onButtonClick() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTaskPresenter.onButtonClick(id, taskTitle.getText().toString(), taskDescription.getText().toString(), taskDate.getText().toString());
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public void onDelete() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setCancelable(true).setMessage(R.string.delete_query)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((MainActivity) getActivity()).setFragment(new TasksListFragment());
                        openTaskPresenter.deletingTask(id);
                    }
                }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).show();
    }

    @Override
    public void showMessage(int message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_button) {
            onDelete();
        }
        return super.onOptionsItemSelected(item);
    }
}