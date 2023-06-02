package org.example.Model.Domain;

public class Phone {
    private int idPhone;
    private String name;
    private String brand;
    private String yearrelease;

    private double price;

    public Phone(int idPhone, String name, String brand, String yearrelease, double precio) {
        this.idPhone = idPhone;
        this.name = name;
        this.brand = brand;
        this.yearrelease = yearrelease;
        this.price = precio;
    }

    public Phone() {
    this.idPhone=0;
    this.name="";
    this.brand="";
    this.yearrelease="2023";
    this.price=0.0;
    }

    public int getIdPhone() {
        return idPhone;
    }

    public void setIdPhone(int idPhone) {
        this.idPhone = idPhone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getYearrelease() {
        return yearrelease;
    }

    public void setYearrelease(String yearrelease) {
        this.yearrelease = yearrelease;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double precio) {
        this.price = precio;
    }

    @Override
    public String toString() {
        return "Phone [id=" + idPhone + ", brand=" + brand + ", name=" + name + ", price=" + price + "]";
    }

}
