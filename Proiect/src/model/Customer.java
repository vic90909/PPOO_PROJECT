package model;

import model.audit.Auditable;
import org.joda.time.DateTime;

import java.io.Serializable;

public class Customer extends Auditable implements Serializable {

    private String name;
    private String surname;
    private String address;
    private String phoneNumber;
    private DateTime birthdate;

    public Customer() {

    }

    public Customer(String uuid,String createdBy, DateTime createdDate, boolean logicallyDeleted, DateTime deletedDate, DateTime editedDate) {
        super(uuid,createdBy, createdDate, logicallyDeleted, deletedDate, editedDate);
    }

    public Customer(String uuid,String createdBy, DateTime createdDate, boolean logicallyDeleted, DateTime deletedDate, DateTime editedDate, Long id, String name, String surname, String address, String phoneNumber, DateTime birthdate) {
        super(uuid, createdBy, createdDate, logicallyDeleted, deletedDate, editedDate);
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.birthdate = birthdate;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public DateTime getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(DateTime birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return "Customer{" +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthdate=" + birthdate +
                '}';
    }

    public static class Builder {
        private Customer customer;

        public Builder(){
            customer= new Customer();
        }


        public Builder name(String name){
            this.customer.setName(name);
            return this;
        }

        public Builder surname(String surname){
            this.customer.setSurname(surname);
            return this;
        }

        public Builder address(String address){
            this.customer.setAddress(address);
            return this;
        }

        public Builder phone(String phone){
            this.customer.setPhoneNumber(phone);
            return this;
        }

        public Builder birthdate(DateTime birthdate){
            this.customer.setBirthdate(birthdate);
            return this;
        }

        public Customer build(){
            return customer;
        }
    }
}
