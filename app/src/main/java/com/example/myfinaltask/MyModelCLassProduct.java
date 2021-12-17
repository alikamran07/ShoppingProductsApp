package com.example.myfinaltask;

import org.json.JSONArray;
import org.json.JSONException;

public class MyModelCLassProduct {

    private int id;
    private String title;
    private double price;
    private String description;
    private String category;
    private String image;
    private String rating;
    private double rate;
    private int count;

    public MyModelCLassProduct(){}
    public MyModelCLassProduct(int id, String title, double price, String description, String category, String image, double rate, int count)

    {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.category = category;
        this.image = image;
        this.rate = rate;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getImage() {
        return image;
    }

    public double getRate() {
        return rate;
    }

    public int getCount() {
        return count;
    }
}