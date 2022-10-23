package com.example.mobile.list.character;

public class CharacterDetail {
    private String Name;
    private String Gender;
    private String Age;
    private String EyeColor;
    private String HairColor;

    public CharacterDetail(String name, String gender, String age, String eyeColor, String hairColor) {
        Name = name;
        Gender = gender;
        Age = age;
        EyeColor = eyeColor;
        HairColor = hairColor;
    }

    public CharacterDetail() {
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
}
