package com.lethsmarketenterprise.lethsmarket.models;

public class Product {
    private String idProduct, name, category, quantity, priceSell, priceBought, validity, brand, comment;

    public Product(String idProduct, String name, String category, String quantity, String priceSell, String priceBought, String validity, String brand, String comment) {
        this.idProduct = idProduct;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.priceSell = priceSell;
        this.priceBought = priceBought;
        this.validity = validity;
        this.brand = brand;
        this.comment = comment;
    }
    public Product(){

    }
    public String getIdProduct() {
        return idProduct;
    }
    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPriceSell() {
        return priceSell;
    }

    public void setPriceSell(String priceSell) {
        this.priceSell = priceSell;
    }

    public String getPriceBought() {
        return priceBought;
    }

    public void setPriceBought(String priceBought) {
        this.priceBought = priceBought;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", quantity='" + quantity + '\'' +
                ", priceSell='" + priceSell + '\'' +
                ", priceBought='" + priceBought + '\'' +
                ", validity='" + validity + '\'' +
                ", brand='" + brand + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
