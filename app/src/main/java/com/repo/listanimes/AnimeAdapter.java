package com.repo.listanimes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.repo.listanimes.models.Anime;
import java.util.List;

public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder> {

    private final List<Anime> animeList;
    private final Context context;

    // Constructor modificado para recibir el contexto
    public AnimeAdapter(Context context, List<Anime> animeList) {
        this.context = context;  // Asigna el contexto pasado como parámetro
        this.animeList = animeList;
    }

    @NonNull
    @Override
    public AnimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_anime, parent, false);
        return new AnimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeViewHolder holder, int position) {
        Anime anime = animeList.get(position);
        holder.titleTextView.setText(anime.getTitle());
        holder.episodesTextView.setText("Episodes: " + anime.getEpisodes());

        String imageUrl = anime.getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder)  // Imagen por defecto mientras se carga
                    .error(R.drawable.error_image)  // Imagen de error si la carga falla
                    .into(holder.animeImageView);
        } else {
            // Si la URL es nula o vacía, mostrar una imagen por defecto
            Glide.with(context)
                    .load(R.drawable.error_image)
                    .into(holder.animeImageView);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("anime_id", anime.getMalId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }

    public static class AnimeViewHolder extends RecyclerView.ViewHolder {
        ImageView animeImageView;
        TextView titleTextView, episodesTextView;

        public AnimeViewHolder(@NonNull View itemView) {
            super(itemView);
            animeImageView = itemView.findViewById(R.id.animeImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            episodesTextView = itemView.findViewById(R.id.episodesTextView);
        }
    }
}
