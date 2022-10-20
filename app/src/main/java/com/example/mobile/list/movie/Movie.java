package com.example.mobile.list.movie;

public class Movie {
    private String Id;
    private String Title;
    private String ImageUrl;

    public Movie(String id, String title, String imageUrl, MovieDetail detail) {
        Id = id;
        Title = title;
        ImageUrl = imageUrl;
        Detail = detail;
    }

    public MovieDetail getDetail() {
        return Detail;
    }

    public void setDetail(MovieDetail detail) {
        Detail = detail;
    }

    private MovieDetail Detail;

    public String getId() {
        return Id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
