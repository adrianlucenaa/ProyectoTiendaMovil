package org.example.Model.Domain;


public class Phone {

    /**
     * //Declaracion atributoss
     */
    private int idPhone;
    private String PhoneName;
    private String brand;
    private int yearrelease;
    private double price;


    /** constructores
     * @param PhoneName
     * @param brand
     * @param yearrelease
     * @param price
     */
    public Phone(String PhoneName, String brand, int yearrelease, double price) {
        this.PhoneName = PhoneName;
        this.brand = brand;
        this.yearrelease = yearrelease;
        this.price = price;
    }

    public Phone() {
        this.PhoneName = "";
        this.brand = "";
        this.yearrelease = 2023;
        this.price = 0.0;
    }

    /**
     * // Getters y Setters
     * @return
     */
    public int getIdPhone() {
        return idPhone;
    }

    public void setIdPhone(int idPhone) {
        this.idPhone = idPhone;
    }

    public String getPhoneName() {
        return PhoneName;
    }

    public void setPhoneName(String PhoneName) {
        this.PhoneName= PhoneName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getYearrelease() {
        return yearrelease;
    }

    public void setYearrelease(int yearrelease) {
        this.yearrelease = yearrelease;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    //Metodo ToString
    @Override
    public String toString() {
        return "Phone [id=" + idPhone + ", brand=" + brand + ", name=" + PhoneName + ", price=" + price + "]";
    }



}
