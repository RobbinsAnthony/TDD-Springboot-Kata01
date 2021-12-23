package com.codedifferently.phonebook.people.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class PhoneNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String phoneNumber;
    private Boolean isMobile;

    public PhoneNumber(String s) {
    }

    public PhoneNumber(Integer id, String phoneNumber, Boolean isMobile) {
        this.phoneNumber = phoneNumber;
        this.isMobile = isMobile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getMobile() {
        return isMobile;
    }

    public void setMobile(Boolean mobile) {
        isMobile = mobile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumber that = (PhoneNumber) o;
        return Objects.equals(id, that.id) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(isMobile, that.isMobile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber, isMobile);
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "id=" + id +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", isMobile=" + isMobile +
                '}';
    }
}
