package org.example.Model.Domain;

public class Buys {


    //Declarando Atributos
    private int IdBuys;

    private String CustomerName;

    private String PhoneName;


    private double price;

    //Constructores
    public Buys() {

    }

    public Buys(String customerName, String phoneName, double price) {
        CustomerName = customerName;
        PhoneName = phoneName;
        this.price = price;
    }
 //Getters y setters
    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getPhoneName() {
        return PhoneName;
    }

    public void setPhoneName(String phoneName) {
        PhoneName = phoneName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public int getIdBuys() {
        return IdBuys;
    }

    public void setIdBuys(int idBuys) {
        IdBuys = idBuys;
    }
}
