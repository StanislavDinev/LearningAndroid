package com.example.stanislavdinev.todo_list.fragments;

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

import com.example.stanislavdinev.todo_list.TodoApp;
import com.example.stanislavdinev.todo_list.MainActivity;
import com.example.stanislavdinev.todo_list.R;
import com.example.stanislavdinev.todo_list.data.ToDoElement;
import com.example.stanislavdinev.todo_list.utils.IdGenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class OpenToDoFragment extends BaseFragment {
    long dateInMiliS;
    private static final int CONSTANT_NEGATIVE_VALUE = -1;
    private static final String ARG_ID = "ARG_ID";
    int id = CONSTANT_NEGATIVE_VALUE;
    private TextView mButton;
    EditText title;
    EditText description;
    TextView date;


    public static OpenToDoFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);

        OpenToDoFragment openToDoFragment = new OpenToDoFragment();
        openToDoFragment.setArguments(args);
        return openToDoFragment;
    }


    public static OpenToDoFragment newInstance() {
        return newInstance(CONSTANT_NEGATIVE_VALUE);
    }


    @Override
    protected void initUi(View view) {

        if (getArguments() != null) {
            id = getArguments().getInt(ARG_ID);
        }
        ToDoElement toDoElement = TodoApp.getInstance().getDataManager().getItemById(id);

        title = view.findViewById(R.id.title);
        description = view.findViewById(R.id.description);
        date = view.findViewById(R.id.date);
        if (id >= 0) {
            setupEditText(toDoElement);
        }

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
                    dateInMiliS = d.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        mButton = view.findViewById(R.id.save_button);
        mButton.setText(id >= 0 ? R.string.save : R.string.add);
        onButtonClick();
    }

    private void setupEditText(ToDoElement toDoElement) {
        title.setText(toDoElement.getTitle());
        if (toDoElement.getDescription() != null) {
            description.setText(toDoElement.getDescription());
        }
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy");
        if (toDoElement.getDate() != 0) {
            date.setText(f.format(toDoElement.getDate()));
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
        return R.layout.to_do_edit;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (id >= 0) {
            inflater.inflate(R.menu.edit_fragment_action_bar, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }


    public void deletingToDo() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setCancelable(true).setMessage(R.string.delete_query)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((MainActivity) getActivity()).setFragment(new ToDoFragment());
                        TodoApp.getInstance().getDataManager().delete(id);
                    }
                }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).show();
    }


    private void onButtonClick() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id >= 0) {
                    onEditButtonClick();
                } else {
                    onAddButtonClick();
                }

            }
        });
    }

    private void onEditButtonClick() {
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date d = f.parse(date.getText().toString());
            dateInMiliS = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (title.getText().toString().equals("")) {
            Toast.makeText(getActivity(), R.string.no_title, Toast.LENGTH_LONG).show();
        } else {
            ToDoElement toDoElement = TodoApp.getInstance().getDataManager().getItemById(id);
            toDoElement.setTitle(title.getText().toString());
            toDoElement.setDescription(description.getText().toString());
            toDoElement.setDate(dateInMiliS);
            TodoApp.getInstance().getDataManager().edit(toDoElement);
            Toast.makeText(getActivity(), R.string.save_message, Toast.LENGTH_LONG).show();
            ((MainActivity) getActivity()).setFragment(new ToDoFragment());
        }
    }

    private void onAddButtonClick() {
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date d = f.parse(date.getText().toString());
            dateInMiliS = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (title.getText().toString().equals("")) {
            Toast.makeText(getActivity(), R.string.no_title, Toast.LENGTH_LONG).show();
        } else {
            ToDoElement toDoElement = new ToDoElement(IdGenerator.getId(), title.getText().toString()
                    , dateInMiliS, description.getText().toString());
            TodoApp.getInstance().getDataManager().add(toDoElement);
            title.setText("");
            description.setText("");
            date.setText("");
            IdGenerator.incrementID();
            dateInMiliS = 0;
            ((MainActivity) getActivity()).setFragment(new ToDoFragment());
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_button) {
            deletingToDo();
        }
        return super.onOptionsItemSelected(item);
    }
}