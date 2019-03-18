package com.example.stanislavdinev.todo_list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.stanislavdinev.todo_list.data.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.DataHolder> {
    private List<Task> tasks = new ArrayList<>();
    private Listener listener;


    public TaskAdapter(List<Task> tasks, Listener listener) {
        this.tasks.addAll(tasks);
        this.listener = listener;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.clear();
        this.tasks.addAll(tasks);
        notifyDataSetChanged();
    }

    public interface Listener {
        void onTask(Task item);
    }

    public class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, date, description;


        private DataHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            date = view.findViewById(R.id.date);
            description = view.findViewById(R.id.description);
            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            Task item = tasks.get(getAdapterPosition());
            listener.onTask(item);
        }
    }

    @NonNull
    @Override
    public TaskAdapter.DataHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_element, viewGroup, false);
        return new DataHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull DataHolder dataHolder, int position) {
        dataHolder.title.setText(tasks.get(position).getTitle());
        if (tasks.get(position).getDate() != 0) {
            SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy");
            Long date = tasks.get(position).getDate();
            dataHolder.date.setText(f.format(date));
        }
        dataHolder.description.setText(tasks.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}