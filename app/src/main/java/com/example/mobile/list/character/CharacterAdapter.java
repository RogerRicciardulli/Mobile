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
        holder.txtCharacterName.setText(characterList.get(position).getName());
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
