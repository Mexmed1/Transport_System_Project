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

public class Checkpoint {
    private int id;
    private String title;
    private boolean longStop;
    private LocalDate dateArrived;


    public Checkpoint(String title, boolean longStop,LocalDate dateArrived) {
        this.title = title;
        this.longStop = longStop;
        this.dateArrived = dateArrived;
    }

    public Checkpoint(int id, String title, boolean longStop, LocalDate dateArrived, int destinationId) {
        this.id = id;
        this.title = title;
        this.longStop = longStop;
        this.dateArrived =dateArrived;
    }

    public String toString() {
        if(!longStop){
            return "Checkpoint city:  " + title  + "  Type of stop:  " + "short stop" + " " + " " + "Date: "+ dateArrived ;
        } else return "Checkpoint city:  " + title  +"  Type of stop:  " + "long stop" + " "+ " "+  "Date: " +dateArrived ;
    }
}
