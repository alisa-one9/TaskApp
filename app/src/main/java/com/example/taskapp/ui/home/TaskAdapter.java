package com.example.taskapp.ui.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.App;
import com.example.taskapp.OnItemClickListener;
import com.example.taskapp.R;
import com.example.taskapp.models.Model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<Model> list = new ArrayList<>();
    private int position;
    private OnItemClickListener onItemClickListener;
    private Button btn_menu_sort;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_task, parent, false);
        return new ViewHolder(view);
    }

    public TaskAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.CYAN);
        } else {
            holder.itemView.setBackgroundColor(Color.LTGRAY);
        }
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void addItem(Model model) {
        list.add(0, model);
        notifyItemChanged(list.indexOf(0));
    }

    public void setList(ArrayList<Model> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void sortList(ArrayList<Model> sortAll) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void addApdateList(List<Model> models) {
        list.clear();
        list = models;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textTv, dateTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTv = itemView.findViewById(R.id.textTitle);
            dateTv = itemView.findViewById(R.id.date_tv);
            itemView.setOnClickListener(v -> onItemClickListener.onClick(list.get(getAdapterPosition()), getAdapterPosition()));

            itemView.setOnLongClickListener(v1 -> {
                AlertDialog alertDialog = new AlertDialog.Builder(itemView.getContext()).setMessage("???? ???????????? ???????????????")
                        .setPositiveButton("????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                App.appDatabase.taskDao().delete(list.get(getAdapterPosition()));
                                remove(getAdapterPosition());
                            }
                        }).setNegativeButton("??????", null).create();
                alertDialog.show();
                return true;
            });
        }

        public void onBind(Model model) {
            textTv.setText(model.getName());
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm, dd MM yyyy");
            String date = dateFormat.format(model.getCreatedAt());
            dateTv.setText(date);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}