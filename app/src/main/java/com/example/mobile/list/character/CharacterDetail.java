package com.example.mobile.list.character;

public class CharacterDetail {
    private String Name;
    private String Gender;
    private String Age;
    private String EyeColor;
    private String HairColor;
    private String Film;

    public CharacterDetail(String name, String gender, String age, String eyeColor, String hairColor, String film) {
        Name = name;
        Gender = gender;
        Age = age;
        EyeColor = eyeColor;
        HairColor = hairColor;
        Film = film;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getEyeColor() {
        return EyeColor;
    }

    public void setEyeColor(String eyeColor) {
        EyeColor = eyeColor;
    }

    public String getHairColor() {
        return HairColor;
    }

    public void setHairColor(String hairColor) {
        HairColor = hairColor;
    }

    public String getFilm() {
        return Film;
    }

    public void setFilm(String film) {
        Film = film;
    }
}
