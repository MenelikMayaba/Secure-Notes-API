package aCompany.entity;


import jakarta.persistence.*;
import aCompany.entity.Roles;

import javax.management.relation.Role;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String Username;

    private String Password;

    private Roles Role;

    public User(){
        this.Role = Roles.USER;

    }


    public User(String username, String password, Roles role) {
        Username = username;
        Password = password;
        Role = role;
    }


    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Roles getRole() {
        return Roles.valueOf(Role.toString());
    }

    public void setRole(Roles role) {
        this.Role = role;
    }
}
