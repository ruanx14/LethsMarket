package com.lethsmarketenterprise.lethsmarket.models;

import java.util.List;

public class Sale {
    private String idSale, total, data, paymentMethod, comment;
    private List<Product> products;
    private List<Integer> quantity;


    public void setIdSale(String idSale){
        this.idSale = idSale;
    }
    public String getIdSale(){
        return idSale;
    }
    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Integer> getQuantity() {
        return quantity;
    }

    public void setQuantity(List<Integer> quantity) {
        this.quantity = quantity;
    }


}
