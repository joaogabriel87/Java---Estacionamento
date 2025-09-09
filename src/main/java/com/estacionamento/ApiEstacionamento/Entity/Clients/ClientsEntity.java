package com.estacionamento.ApiEstacionamento.Entity.Clients;

import com.estacionamento.ApiEstacionamento.Entity.Vehicle.VehicleEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class ClientsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true, name = "documento")
    private String document;
    @Column(name = "nome")
    private String name;
    @Column(name = "telefone")
    private String phone;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<VehicleEntity> vehicles = new ArrayList<>();

    public ClientsEntity( String document, String name, String phone) {
        this.document = document;
        this.name = name;
        this.phone = phone;
    }

    public ClientsEntity() {

    }

    public void addVehicle(VehicleEntity vehicle) {
        vehicles.add(vehicle);
        vehicle.setClient(this);
    }
}
