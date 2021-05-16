package com.example.taskapp.ui.onboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.OnItemClickListener;
import com.example.taskapp.R;
import com.example.taskapp.databinding.PagerBoardBinding;

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
    private PagerBoardBinding binding;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = PagerBoardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding.getRoot());
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }

        public void bind(int position) {
            binding.textBoard.setText(titles[position]);
            binding.anime.setAnimation(images[position]);
            binding.textDesc.setText(titlesExplain[position]);
            if (position == titles.length - 1) binding.btnStart.setVisibility(View.VISIBLE);
            else binding.btnStart.setVisibility(View.INVISIBLE);
            binding.btnStart.setOnClickListener(v -> {
                onItemClickListener.onClick(null, getAdapterPosition());
            });
        }
    }
}
