package com.Ryliakou.demo.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String login;
    private String pass;
    private boolean active;

    private String fio;


    private String email;
    private String phone;




    @ElementCollection(targetClass = Role.class,fetch = FetchType.EAGER)
    @CollectionTable(name="client_role",joinColumns =@JoinColumn(name="client_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public  Client(){

    }

    public Client(String fio, String login, String pass, String email, String phone,boolean active) {


        this.fio = fio;
        this.login = login;
        this.pass = pass;
        this.email = email;
        this.phone = phone;
        this.active=active;
    }
    public Client(Long id, String fio, String login, String pass, String email, String phone,boolean active) {

        this.id=id;
        this.fio = fio;
        this.login = login;
        this.pass = pass;
        this.email = email;
        this.phone = phone;
        this.active=active;
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
