package com.example.mobile.list.movie;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile.R;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder>{
    private List<MovieDetail> movieList;
    OnItemClickListener onItemClickListener;

    public MovieAdapter(List<MovieDetail> movieList, OnItemClickListener onItemClickListener) {
        this.movieList = movieList;
        this.onItemClickListener = onItemClickListener;
    }
    public  void clear(){
        int size = movieList.size();
        movieList.clear();
        notifyItemRangeRemoved(0, size);
    }
    public  void set(List<MovieDetail> movieList){
        clear();
        this.movieList = movieList;
        notifyItemRangeInserted(0, this.movieList.size());
    }
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtMovieName.setText(movieList.get(position).getOriginalTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(movieList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
    public interface OnItemClickListener{
        void onItemClick(MovieDetail movie);
    }
}
