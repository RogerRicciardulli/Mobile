package com.example.mobile.list.character;

public class Character {
    private String Id;
    private String Name;
    private String ImageUrl;

    public Character(String id, String name, String imageUrl, CharacterDetail detail) {
        Id = id;
        Name = name;
        ImageUrl = imageUrl;
        Detail = detail;
    }

    public CharacterDetail getDetail() {
        return Detail;
    }

    public void setDetail(CharacterDetail detail) {
        Detail = detail;
    }

    private CharacterDetail Detail;

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
