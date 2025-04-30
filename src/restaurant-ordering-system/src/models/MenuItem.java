package models;

import java.awt.Image;

public class MenuItem {
    private String name;
    private String description;
    private double cost;
    private Image image;

    public MenuItem(String name, String description, double cost, Image image) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getCost() {
        return cost;
    }

    public Image getImage() {
        return image;
    }
}