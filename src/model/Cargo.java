package model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Cargo {
    private int id;
    private String title;
    private LocalDate dateCreated;
    private LocalDate dateUpdated;
    private double weight;
    private CargoType cargoType;
    private String description;
    private  String customer;



    public Cargo(int id, String title, double weight, CargoType cargoType, String description, String customer, LocalDate dateCreated) {
        this.id = id;
        this.title = title;
        this.weight = weight;
        this.cargoType = cargoType;
        this.description = description;
        this.customer = customer;
        this.dateCreated = dateCreated;
    }

    public Cargo(LocalDate dateCreated, LocalDate dateUpdated) {
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    public String toString(){
        return "Title : " + title + " , Weight : " + weight  + " , Cargo type : " + cargoType + " , Description : " + description + " , Customer : " + customer + " , Date created : " + dateCreated;
    }
}
