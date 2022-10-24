package com.example.mobile.list.character;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile.R;

import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterViewHolder>{
    private List<CharacterDetail> characterList;
    OnItemClickListener onItemClickListener;

    public CharacterAdapter(List<CharacterDetail> characterList, OnItemClickListener onItemClickListener) {
        this.characterList = characterList;
        this.onItemClickListener = onItemClickListener;
    }
    public void clear(){
        int size = characterList.size();
        characterList.clear();
        notifyItemRangeRemoved(0, size);
    }
    public void set(List<CharacterDetail> characterList){
        clear();
        this.characterList = characterList;
        notifyItemRangeInserted(0, this.characterList.size());
    }
    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_character, parent, false);
        return new CharacterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, @SuppressLint("RecyclerView") int position) {
        int imageId = 0;
        holder.txtCharacterName.setText(characterList.get(position).getName());
        switch(characterList.get(position).getName()){
            case "Haku":
                imageId = 2131165372;
                break;
            case "Pazu":
                imageId = 2131165376;
                break;
            case "Lusheeta Toel Ul Laputa":
                imageId = 2131165381;
                break;
            case "Captain Dola":
                imageId = 2131165379;
                break;
            case "Romska Palo Ul Laputa":
                imageId = 2131165382;
                break;
            case "Uncle Pom":
                imageId = 2131165378;
                break;
            case "General Mouro":
                imageId = 2131165380;
                break;
            case "Duffi":
                imageId = 2131165371;
                break;
            case "Louis":
                imageId = 2131165373;
                break;
            case "Charles":
                imageId = 2131165369;
                break;
        }
        holder.ivCharacterImage.setImageResource(imageId);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(characterList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }
    public interface OnItemClickListener{
        void onItemClick(CharacterDetail character);
    }
}
