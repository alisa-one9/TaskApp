package com.example.taskapp.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.OnItemClickListener;
import com.example.taskapp.R;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private ArrayList<String> list;

    public static final String key1 = "key1";
    public static final String key2 = "key2";

    public TextView textTitle;
    private String text;
    private onClick onClick;

    public TaskAdapter(onClick onClick) {
        this.list = new ArrayList<>();
        this.onClick = onClick;
        list.add("Пшеница");
        list.add("Картофель");
        list.add("Молочные продукты");
        list.add("Масло растительное");
        list.add("Рис");
        list.add("Кукуруза");
        list.add("Гречневое зерно");
        list.add("Овес");
        list.add("Яблоки");
        list.add("Персики");
        list.add("Бахчевые");
        list.add("Мясная продукция");
        list.add("Сахар");
        list.add("Соль");
        list.add("Премиксы");
        list.add("Пшеничные продукты");
        list.add("Груши");
        list.add("Пектины");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_task, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.CYAN);
        } else {
            holder.itemView.setBackgroundColor(Color.LTGRAY);
        }
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(String title) {
        list.add(0, title);
        notifyItemInserted(list.size() - 1);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            text = textTitle.getText().toString();

            itemView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                onClick.send(list.get(getAdapterPosition()));
                                            }
                                        });

                    itemView.setOnLongClickListener(v1 -> {
                        OnItemClickListener.onLongClick(getAdapterPosition());
                        return true;
                    });

            itemView.setOnLongClickListener(v -> {
            AlertDialog alertDialog = new AlertDialog.Builder(itemView.getContext()).setMessage("Вы хотите удалить?")
                    .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    list.remove(getAdapterPosition());
                    notifyDataSetChanged();
                }
            }).setNegativeButton("Нет",null).create();
            alertDialog.show();
            return true;
        });
        }

        public void bind(String s) {
            textTitle.setText(s);
        }
    }
    public  interface onClick{
        void send(String string);
    }


}



