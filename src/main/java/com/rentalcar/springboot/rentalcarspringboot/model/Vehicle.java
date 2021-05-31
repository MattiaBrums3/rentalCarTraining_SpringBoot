package com.rentalcar.springboot.rentalcarspringboot.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "vehicle")
public class Vehicle implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "model")
    private String model;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "licensePlate")
    private String licensePlate;

    @Column(name = "yearOfRegistration")
    private int yearOfRegistration;

    @ManyToOne
    @JoinColumn(name = "idCategory")
    private Category category;

    public Vehicle() {}

    public Vehicle(String model, String manufacturer, String licensePlate, int yearOfRegistration,
                   Category category) {
        this.model = model;
        this.manufacturer = manufacturer;
        this.licensePlate = licensePlate;
        this.yearOfRegistration = yearOfRegistration;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public int getYearOfRegistration() {
        return yearOfRegistration;
    }

    public void setYearOfRegistration(int yearOfRegistration) {
        this.yearOfRegistration = yearOfRegistration;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
