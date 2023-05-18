package org.example.Model.Domain;

public class Buys {
    private int idBuys;
    private int IdCustomer;
    private int IdProduct;
    private String customerName;

    public Buys(int idBuys, int idCustomer, int idProduct) {
        this.idBuys = idBuys;
        IdCustomer = idCustomer;
        IdProduct = idProduct;
    }

    public Buys() {

    }

    public int getIdBuys() {
        return idBuys;
    }

    public void setIdBuys(int id) {
        this.idBuys = idBuys;
    }

    public int getIdCustomer() {
        return IdCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        IdCustomer = idCustomer;
    }

    public int getIdProduct() {
        return IdProduct;
    }

    public void setIdProduct(int idProduct) {
        IdProduct = idProduct;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
