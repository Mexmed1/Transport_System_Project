package model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Destination {
    private  int id;
    private String startCity;
    private long startLn;
    private long startLat;
    private  String endCity;
    private long endLn;
    private long endLat;
    private LocalDate dateCreated;
    private LocalDate dateUpdated;
    private List<Checkpoint> checkpointList;
    private Truck truck;
    private Manager responsibleManagers;
    private Cargo cargo;




    public Destination(int id, String startCity, long startLn, long startLat, String endCity, long endLn, long endLat, int truckId, int responsibleManagersId, int cargoId) {
        this.id = id;
        this.startCity = startCity;
        this.startLn = startLn;
        this.startLat = startLat;
        this.endCity = endCity;
        this.endLn = endLn;
        this.endLat = endLat;
    }

    public Destination(String startCity, long startLn, long startLat, String endCity, long endLn, long endLat, Truck truck, Manager responsibleManagers, Cargo cargo) {
        this.startCity = startCity;
        this.startLn = startLn;
        this.startLat = startLat;
        this.endCity = endCity;
        this.endLn = endLn;
        this.endLat = endLat;
        this.truck = truck;
        this.responsibleManagers = responsibleManagers;
        this.cargo = cargo;
    }
    public String toString(){
        return "Start city : " + startCity + " , Start Ln : " + startLn + " , Start Lat : " + startLat + " , End city : " + endCity + " , End Ln : " + endLn + " , End Lat : " +endLat;
    }
}
