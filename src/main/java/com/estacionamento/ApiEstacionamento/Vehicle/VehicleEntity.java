package com.estacionamento.ApiEstacionamento.Vehicle;

import com.estacionamento.ApiEstacionamento.Ticket.TicketEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "Veiculos")
public class VehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "placa", unique = true)
    private String plate;
    @Column(name = "marca")
    private String brand;
    @Column(name = "cor")
    private String color;
    @Column(name = "modelo")
    private String model;
    @Column(name="tipo")
    @Enumerated(EnumType.STRING)
    private TypeEnum type;
    @JsonIgnore
    @OneToMany(mappedBy = "vehicle", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TicketEntity> tickets;


    public VehicleEntity(String plate, TypeEnum type) {
        this.plate = plate;
        this.type = type;
    }

    public void addTicket(TicketEntity ticket) {
        tickets.add(ticket);
        ticket.setVehicle(this);
    }
}
