package com.example.mobile.list.movie;

import static com.example.mobile.ExtensionesKt.getImage;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile.MovieDetailActivity;
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
        int imageId = 0;
        holder.txtMovieName.setText(movieList.get(position).getOriginalTitle());
        switch(movieList.get(position).getOriginalTitle()){
            case "Castle in the Sky":
                imageId = 2131165347;
                break;
            case "Grave of the Fireflies":
                imageId = 2131165349;
                break;
            case "My Neighbor Totoro":
                imageId = 2131165352;
                break;
            case "Kiki's Delivery Service":
                imageId = 2131165351;
                break;
            case "Only Yesterday":
                imageId = 2131165354;
                break;
            case "Porco Rosso":
                imageId = 2131165356;
                break;
            case "Pom Poko":
                imageId = 2131165368;
                break;
            case "Whisper of the Heart":
                imageId = 2131165366;
                break;
            case "Princess Mononoke":
                imageId = 2131165357;
                break;
            case "My Neighbors the Yamadas":
                imageId = 2131165353;
                break;
            case "Spirited Away":
                imageId = 2131165358;
                break;
            case "The Cat Returns":
                imageId = 2131165360;
                break;
            case "Howl's Moving Castle":
                imageId = 2131165350;
                break;
            case "Tales from Earthsea":
                imageId = 2131165359;
                break;
            case "Ponyo":
                imageId = 2131165355;
                break;
            case "Arrietty":
                imageId = 0;
                break;
            case "From Up on Poppy Hill":
                imageId = 2131165348;
                break;
            case "The Wind Rises":
                imageId = 2131165364;
                break;
            case "The Tale of the Princess Kaguya":
                imageId = 2131165363;
                break;
            case "When Marnie Was There":
                imageId = 2131165365;
                break;
            case "The Red Turtle":
                imageId = 2131165361;
                break;
        }
        holder.ivImage.setImageResource(imageId);
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
