package com.example.stanislavdinev.todo_list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.stanislavdinev.todo_list.data.ToDoElement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.DataHolder> {
    private List<ToDoElement> myList = new ArrayList<>();
    private Listener listener;


    public ItemAdapter(List<ToDoElement> myList, Listener listener) {
        this.myList.addAll(myList);
        this.listener = listener;
    }

    public interface Listener {
        void onToDoClick(ToDoElement item);
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
            ToDoElement item = myList.get(getAdapterPosition());
            listener.onToDoClick(item);
        }
    }

    @NonNull
    @Override
    public ItemAdapter.DataHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.to_do_row, viewGroup, false);
        return new DataHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull DataHolder dataHolder, int position) {
        dataHolder.title.setText(myList.get(position).getTitle());
        if (myList.get(position).getDate() != 0) {
            SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy");
            Long date = myList.get(position).getDate();
            dataHolder.date.setText(f.format(date));
        }
        dataHolder.description.setText(myList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }
}