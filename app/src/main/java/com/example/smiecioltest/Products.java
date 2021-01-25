package com.example.smiecioltest;

public class Products {
    public String barcode, id, name, weight;

    public Products(){

    }

    public Products(String barcode, String id, String name, String weight) {
        this.barcode = barcode;
        this.id = id;
        this.name = name;
        this.weight = weight;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
