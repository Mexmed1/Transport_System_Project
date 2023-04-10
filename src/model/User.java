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
public abstract class User {
    private int id;
    private String login;
    private String password;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private  String phoneNum;

    public User(String login, String password, String name, String surname, LocalDate birthDate, String phoneNum) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.phoneNum = phoneNum;
    }
}
