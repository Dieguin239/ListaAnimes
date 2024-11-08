package com.repo.listanimes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.repo.listanimes.models.Episode;
import com.repo.listanimes.ApiClient;
import com.repo.listanimes.models.EpisodeResponse;
import com.repo.listanimes.servicios.ApiService;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EpisodeAdapter episodeAdapter;
    private List<Episode> episodeList = new ArrayList<>();
    private int animeId;
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        recyclerView = findViewById(R.id.recyclerViewEpisodes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Recibimos el ID del anime seleccionado
        animeId = getIntent().getIntExtra("anime_id", -1);

        episodeAdapter = new EpisodeAdapter(this, episodeList);
        recyclerView.setAdapter(episodeAdapter);

        // Carga inicial de episodios
        loadEpisodes(animeId, currentPage);

        // Paginación para episodios
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(1)) {
                    currentPage++;
                    loadEpisodes(animeId, currentPage);
                }
            }
        });
    }

    private void loadEpisodes(int animeId, int page) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<EpisodeResponse> call = apiService.getEpisodes(animeId, page);

        call.enqueue(new Callback<EpisodeResponse>() {
            @Override
            public void onResponse(Call<EpisodeResponse> call, Response<EpisodeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    episodeList.addAll(response.body().getData());
                    episodeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<EpisodeResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
