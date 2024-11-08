package com.repo.listanimes;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.repo.listanimes.models.Anime;
import com.repo.listanimes.models.AnimeResponse;
import com.repo.listanimes.ApiClient;
import com.repo.listanimes.servicios.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private AnimeAdapter adapter;
    private List<Anime> animeList = new ArrayList<>();
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AnimeAdapter(this, animeList);  // 'this' es el contexto de la actividad
        recyclerView.setAdapter(adapter);

        loadAnimes(currentPage);

        // Implementación de paginación
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(1)) {
                    currentPage++;
                    loadAnimes(currentPage);
                }
            }
        });
    }

    private void loadAnimes(int page) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<AnimeResponse> call = apiService.getAnimes(page);

        call.enqueue(new Callback<AnimeResponse>() {
            @Override
            public void onResponse(Call<AnimeResponse> call, Response<AnimeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    animeList.addAll(response.body().getData());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<AnimeResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}