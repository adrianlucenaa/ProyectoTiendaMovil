package org.example.Model.Domain;

public class Buys {
    private int idBuys;
    private int IdCustomer;
    private int IdPhone;
    private String customerName;

    private double price;

    public Buys() {

    }

    public int getIdBuys() {
        return idBuys;
    }

    public void setIdBuys(int idBuys) {
        this.idBuys = idBuys;
    }

    public int getIdCustomer() {
        return IdCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        IdCustomer = idCustomer;
    }

    public int getIdPhone() {
        return IdPhone;
    }

    public void setIdPhone(int idPhone) {
        IdPhone = idPhone;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Buys(int idBuys, int idCustomer, int idPhone, String customerName, double price) {
        this.idBuys = idBuys;
        IdCustomer = idCustomer;
        IdPhone = idPhone;
        this.customerName = customerName;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Buys{" +
                "IdBuys=" + idBuys +
                ", IdCustomer=" + IdCustomer +
                ", IdPhone=" + IdPhone +
                ", customerName='" + customerName + '\'' +
                ", price=" + price +
                '}';
    }
}
