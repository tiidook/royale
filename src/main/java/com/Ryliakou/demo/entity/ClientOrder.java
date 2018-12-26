package com.Ryliakou.demo.entity;
import javax.persistence.*;
import java.util.Date;

@Entity
public class ClientOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String checkin;
    private String checkout;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    public ClientOrder() {


            }

    public ClientOrder(String checkin, String checkout, Client client,Room room) {
        this.checkin = checkin;
        this.checkout = checkout;
        this.client=client;
        this.room=room;

    }




    public Long getId() {        return id;    }

    public void setId(Long id) {        this.id = id;    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public Client getClient() {        return client;    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
