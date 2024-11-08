package com.repo.listanimes.models;

public class Anime {
    private int mal_id;
    private String title;
    private String  image_url;
    private int episodes;

    public int getMalId() { return mal_id; }
    public String getTitle() { return title; }
    public String getImageUrl() { return image_url; }
    public int getEpisodes() { return episodes; }
}
