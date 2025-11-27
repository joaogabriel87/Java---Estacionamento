package com.estacionamento.ApiEstacionamento.Ticket;

import com.estacionamento.ApiEstacionamento.Parking.ParkingEntity;
import com.estacionamento.ApiEstacionamento.Vehicle.VehicleEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ticket")
public class TicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="codigo_ticket", unique = true, nullable = false)
    private Long codeTicket;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estacionamento_id", nullable = false)
    private ParkingEntity parking;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veiculo_id", nullable = false)
    private VehicleEntity vehicle;
    @Column(name = "horario_chegada",  nullable = false)
    private LocalDateTime checkin;
    @Column(name = "horario_saida")
    private LocalDateTime checkout;
    @Column(name = "preco", precision = 10, scale = 2)
    private BigDecimal price;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    public TicketEntity(Long codeTicket, VehicleEntity vehicle,  ParkingEntity parking, LocalDateTime checkin) {
        this.codeTicket = codeTicket;
        this.parking = parking;
        this.vehicle = vehicle;
        this.checkin = checkin;
        this.status = StatusEnum.USO;
    }
}
