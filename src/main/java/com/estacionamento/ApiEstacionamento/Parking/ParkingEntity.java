package com.estacionamento.ApiEstacionamento.Parking;

import com.estacionamento.ApiEstacionamento.Ticket.TicketEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "Estacionamento")
public class ParkingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome_bloco",unique = true)
    private String name;
    @Column(name = "capacidade_carro")
    private int capacityCar;
    @Column(name = "capacidade_moto")
    private int capacityMoto;
    @Column(name = "capacidade_maxima_carro")
    private int capacityMaxCar;
    @Column(name = "capacidade_maxima_moto")
    private int capacityMaxMoto;
    @Column(name = "taxa_carro", nullable = false, precision = 10, scale = 2)
    private BigDecimal taxaCarro;
    @Column(name = "taxa_moto", nullable = false, precision = 10, scale = 2)
    private BigDecimal taxaMoto;
    @Column(name = "taxa_carro_add", nullable = false, precision = 10, scale = 2)
    private BigDecimal taxaCarroAd;
    @Column(name = "taxa_moto_add", nullable = false, precision = 10, scale = 2)
    private BigDecimal taxaMotoAd;
    @JsonIgnore
    @OneToMany(mappedBy = "parking", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TicketEntity> tickets;


    public ParkingEntity(String name, int capacityCar, int capacityMoto, BigDecimal taxaCarro, BigDecimal taxaMoto, BigDecimal taxaCarroAd, BigDecimal taxaMotoAd, int capacityMaxCar, int capacityMaxMoto  ) {
        this.name = name;
        this.capacityCar = capacityCar;
        this.capacityMoto = capacityMoto;
        this.taxaCarro = taxaCarro;
        this.taxaMoto = taxaMoto;
        this.taxaCarroAd = taxaCarroAd;
        this.taxaMotoAd = taxaMotoAd;
        this.capacityMaxCar = capacityMaxCar;
        this.capacityMaxMoto = capacityMaxMoto;
    }


}
