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

public class Driver extends User {
    private LocalDate medCertificateDate;
    private String medCertificateNumber;
    private String driverLicense;

    public Driver(int id, String login, String password, String name, String surname, LocalDate birthDate, String phoneNum, LocalDate medCertificateDate, String medCertificateNumber, String driverLicense) {
        super(id, login, password, name, surname, birthDate, phoneNum);
        this.medCertificateDate = medCertificateDate;
        this.medCertificateNumber = medCertificateNumber;
        this.driverLicense = driverLicense;
    }

    public Driver(String login, String password, String name, String surname, LocalDate birthDate, String phoneNum, LocalDate medCertificateDate, String medCertificateNumber, String driverLicense) {
        super(login, password, name, surname, birthDate, phoneNum);
        this.medCertificateDate = medCertificateDate;
        this.medCertificateNumber = medCertificateNumber;
        this.driverLicense = driverLicense;
    }
}
