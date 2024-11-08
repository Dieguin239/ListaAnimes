package com.repo.listanimes.servicios;
import com.repo.listanimes.models.AnimeResponse;
import com.repo.listanimes.models.EpisodeResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("anime")
    Call<AnimeResponse> getAnimes(@Query("page") int page);

    @GET("anime/{id}/episodes")
    Call<EpisodeResponse> getEpisodes(@Path("id") int animeId, @Query("page") int page);
}
