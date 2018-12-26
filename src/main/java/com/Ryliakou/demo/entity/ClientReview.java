package com.Ryliakou.demo.entity;
import javax.persistence.*;
import java.util.Date;

@Entity
public class ClientReview {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    public ClientReview(){

    }

    public ClientReview(String text, Client client){
        this.text=text;
        this.client=client;
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
