package com.example.taskapp.ui.onboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.taskapp.OnItemClickListener;
import com.example.taskapp.R;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {
    private String[] titles = new String[]{"Консультируем по поставкам сельскохозяйственных товаров", "Быстрая помощь", "24/7"};
    private String[] titlesExplain = new String[]{"Всегда найдем любые данные и сделаем необходимый анализ",
            "Оставьте один месседж ", "Звоните в любое время!"};

    private final int[] images = new int[]{
            R.raw.cat, R.raw.powerful, R.raw.soft
    };

    public BoardAdapter() {
    }

    private OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pager_board, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textBoard, textdesc;
        private Button btnStart;
        private LottieAnimationView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textBoard = itemView.findViewById(R.id.textBoard);
            btnStart = itemView.findViewById(R.id.btnStart);
            textdesc = itemView.findViewById(R.id.textDesc);
            imageView = itemView.findViewById(R.id.anime);
                btnStart.setOnClickListener(v -> onItemClickListener.onItemClick(getAdapterPosition()));
        }

        public void bind(int position) {
            textBoard.setText(titles[position]);
            imageView.setAnimation(images[position]);
            textdesc.setText(titlesExplain[position]);
            if (position == titles.length - 1) btnStart.setVisibility(View.VISIBLE);
            else btnStart.setVisibility(View.INVISIBLE);
        }
    }
}
