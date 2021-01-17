package com.example.smiecioltest;

public class Products {
    public String product_name, weight;

    public Products(){

    }

    public Products(String product_name, String weight) {
        this.product_name = product_name;
        this.weight = weight;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
