package com.auction.client.model;


public class Item {
    private String id;
    private String name;
    private String imagePath;
    private String status;
    private double price;

    public Item(String id, String name, String imagePath, String status, double price) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.status = status;
        this.price = price;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getImagePath() { return imagePath; }
    public String getStatus() { return status; }
    public double getPrice() { return price; }
}