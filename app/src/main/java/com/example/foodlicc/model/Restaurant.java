package com.example.foodlicc.model;

import java.util.List;

public class Restaurant {
    private String coverpic;
    private String deliveryStatut;
    private String name;
    private String propic;
    private String rating;
    private String statut;
    private String id;
    private List<Food> Menu;
    private String cuisine;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Restaurant() {
    }

    public List<Food> getMenu() {
        return Menu;
    }

    public void setMenu(List<Food> menu) {
        Menu = menu;
    }

    public Restaurant(String coverpic, String deliveryStatut, String name, String propic, String rating, String statut, String id, List<Food> menu) {
        this.coverpic = coverpic;
        this.deliveryStatut = deliveryStatut;
        this.name = name;
        this.propic = propic;
        this.rating = rating;
        this.statut = statut;
        this.id = id;
        this.Menu = menu;

    }

    public String getCoverpic() {
        return coverpic;
    }

    public void setCoverpic(String coverpic) {
        this.coverpic = coverpic;
    }

    public String getDeliveryStatut() {
        return deliveryStatut;
    }

    public void setDeliveryStatut(String deliveryStatut) {
        this.deliveryStatut = deliveryStatut;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPropic() {
        return propic;
    }

    public void setPropic(String propic) {
        this.propic = propic;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }
}
