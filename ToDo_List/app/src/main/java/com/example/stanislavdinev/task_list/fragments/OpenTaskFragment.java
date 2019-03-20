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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class OpenTaskFragment extends BaseFragment implements OpenTaskView {
    long dateInMilliS;
    private static final int CONSTANT_NEGATIVE_VALUE = -1;
    private static final String ARG_ID = "ARG_ID";
    int id = CONSTANT_NEGATIVE_VALUE;
    private TextView mButton;
    EditText title;
    EditText description;
    TextView date;
    OpenTaskPresenter openTaskPresenter = new OpenTaskPresenter(this, TaskApp.getInstance().getDataManager());


    public static OpenTaskFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);

        OpenTaskFragment openTaskFragment = new OpenTaskFragment();
        openTaskFragment.setArguments(args);
        return openTaskFragment;
    }


    public static OpenTaskFragment newInstance() {
        return newInstance(CONSTANT_NEGATIVE_VALUE);
    }


    @Override
    protected void initUi(View view) {
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_ID);
        }
        title = view.findViewById(R.id.title);
        description = view.findViewById(R.id.description);
        date = view.findViewById(R.id.date);
        openTaskPresenter.setupEditText(id);

        date.setOnClickListener(new View.OnClickListener() {
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
                                date.setText(day + "."
                                        + (month + 1) + "." + year);
                            }
                        }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                datePickerDialog.show();
                SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy");
                try {
                    Date d = f.parse(date.getText().toString());
                    dateInMilliS = d.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        mButton = view.findViewById(R.id.save_button);
        mButton.setText(id >= 0 ? R.string.save : R.string.add);
        onButtonClick();
    }

    @Override
    public void setupEditText(Task task) {
        title.setText(task.getTitle());
        if (task.getDescription() != null) {
            description.setText(task.getDescription());
        }
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy");
        if (task.getDate() != 0) {
            date.setText(f.format(task.getDate()));
        }
    }

    @Override
    public void setupToolbar() {
        setHasOptionsMenu(true);
        android.support.v7.app.ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        if (id >= 0) {
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
        if (id >= 0) {
            inflater.inflate(R.menu.edit_screen_action_bar, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }



    private void onButtonClick() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTaskPresenter.setButtons(id);
            }
        });
    }

    @Override
    public void onEdit() {
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date d = f.parse(date.getText().toString());
            dateInMilliS = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (title.getText().toString().equals("")) {
            Toast.makeText(getActivity(), R.string.no_title, Toast.LENGTH_LONG).show();
        } else {
            Task task = TaskApp.getInstance().getDataManager().getTaskById(id);
            task.setId(id);
            task.setTitle(title.getText().toString());
            task.setDescription(description.getText().toString());
            task.setDate(dateInMilliS);
            TaskApp.getInstance().getDataManager().edit(task);
            Toast.makeText(getActivity(), R.string.save_message, Toast.LENGTH_LONG).show();
            getActivity().onBackPressed();
        }
    }

    @Override
    public void onAdd() {
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date d = f.parse(date.getText().toString());
            dateInMilliS = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (title.getText().toString().equals("")) {
            Toast.makeText(getActivity(), R.string.no_title, Toast.LENGTH_LONG).show();
        } else {
            Task task = new Task(title.getText().toString()
                    , dateInMilliS, description.getText().toString());
            TaskApp.getInstance().getDataManager().add(task);
            getActivity().onBackPressed();
        }
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_button) {
            onDelete();
        }
        return super.onOptionsItemSelected(item);
    }
}