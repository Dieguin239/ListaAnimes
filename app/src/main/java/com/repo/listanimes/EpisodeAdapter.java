package com.repo.listanimes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.repo.listanimes.models.Episode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder> {

    private Context context;
    private List<Episode> episodeList;

    public EpisodeAdapter(Context context, List<Episode> episodeList) {
        this.context = context;
        this.episodeList = episodeList;
    }

    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_episode, parent, false);
        return new EpisodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder holder, int position) {
        Episode episode = episodeList.get(position);
        holder.titleTextView.setText(episode.getTitle());

        // Obtener la fecha de emisión
        String aired = episode.getAired();

        if (aired != null && !aired.isEmpty()) {
            try {
                // Parsear la fecha en formato 'yyyy-MM-dd' o el formato que devuelve la API
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date date = inputFormat.parse(aired);  // Parsear la fecha
                if (date != null) {
                    // Convertir la fecha a la cadena con el formato deseado
                    String formattedDate = outputFormat.format(date);
                    holder.releaseDateTextView.setText(formattedDate);
                } else {
                    holder.releaseDateTextView.setText("Fecha no válida");
                }
            } catch (ParseException e) {
                e.printStackTrace();
                holder.releaseDateTextView.setText("Fecha no disponible");
            }
        } else {
            holder.releaseDateTextView.setText("Fecha no disponible");
        }
    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }

    public static class EpisodeViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView releaseDateTextView;

        public EpisodeViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.episodeTitle);
            releaseDateTextView = itemView.findViewById(R.id.episodeReleaseDate);
        }
    }
}