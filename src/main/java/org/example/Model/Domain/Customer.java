package org.example.Model.Domain;

public class Customer {

    /**
     * //Declaracion Atributos
     */
    private int idCustomer;

    private String name;

    private String number;

    private String mail;

    private String address;

    /**
     * /Constructores
     */
    public Customer() {
    }

    /**
     * //getters y setters
     * @return
     */

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    /**
     * //Metodo ToString
     * @return
     */
    @Override
    public String toString() {
        return "ID: " + idCustomer + ", Name: " + name + ", Number: " + number;
    }

}
