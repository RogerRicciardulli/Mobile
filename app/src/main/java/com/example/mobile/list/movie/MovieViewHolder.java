package com.example.mobile.list.movie;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile.R;

public class MovieViewHolder extends RecyclerView.ViewHolder{
    public TextView txtMovieName;
    public ImageView ivImage;
    public MovieViewHolder(@NonNull View itemView) {
        super(itemView);
        txtMovieName = itemView.findViewById(R.id.txtMovieName);
        ivImage = itemView.findViewById(R.id.ivImage);
    }
}
