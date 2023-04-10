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

public class Manager extends User {
    private String email;
    private  LocalDate employmentDate;
    private  boolean isAdmin;

    public Manager(String login, String password, String name, String surname, LocalDate birthDate, String phoneNum, String email) {
        super(login, password, name, surname, birthDate, phoneNum);
        this.employmentDate = LocalDate.now();
        this.email = email;
    }

    public Manager(String login, String password, String name, String surname, LocalDate birthDate, String phoneNum, boolean isAdmin) {
        super(login, password, name, surname, birthDate, phoneNum);
        this.employmentDate = LocalDate.now();
        this.isAdmin = isAdmin;
    }


    public Manager(int id, String login, String password, String name, String surname, LocalDate birthDate, String phoneNum, String email, LocalDate employmentDate, boolean isAdmin) {
        super(id, login, password, name, surname, birthDate, phoneNum);
        this.email = email;
        this.employmentDate = employmentDate;
        this.isAdmin = isAdmin;
    }

    public String toString(){
        return "Name Manager : "+ getName() + " , Surname : " + getSurname();
    }
}
