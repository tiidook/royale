package com.Ryliakou.demo.entity;
import javax.persistence.*;

@Entity
public class Req {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;
    public Req(){

    }

    public Req(String text){
        this.text=text;

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
}
