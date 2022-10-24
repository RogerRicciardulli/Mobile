package com.example.mobile.list.character;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile.R;

public class CharacterViewHolder extends RecyclerView.ViewHolder{
    public TextView txtCharacterName;
    public ImageView ivCharacterImage;
    public CharacterViewHolder(@NonNull View itemView) {
        super(itemView);
        txtCharacterName = itemView.findViewById(R.id.txtCharacterName);
        ivCharacterImage = itemView.findViewById(R.id.ivCharacterImage);
    }
}
