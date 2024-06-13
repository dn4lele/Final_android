package com.example.final_android.ViewPager;

import java.util.List;

public class SliderItems {
    private int id;
    private String image;
    private String name;
    private List<String> genre;
    private String year;
    private String rating;

    public SliderItems(int id, String image, String name, List<String> genre, String year, String rating) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.genre = genre;
        this.year = year;
        this.rating = rating;
    }

    public SliderItems() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
