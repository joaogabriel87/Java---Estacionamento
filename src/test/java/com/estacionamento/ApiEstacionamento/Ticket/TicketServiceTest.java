package com.estacionamento.ApiEstacionamento.Ticket;

import com.estacionamento.ApiEstacionamento.Erro.ParkingisNull;
import com.estacionamento.ApiEstacionamento.Erro.VehicleIsOccupied;
import com.estacionamento.ApiEstacionamento.Parking.ParkingEntity;
import com.estacionamento.ApiEstacionamento.Parking.ParkingRepository;
import com.estacionamento.ApiEstacionamento.Parking.ParkingService;
import com.estacionamento.ApiEstacionamento.Vehicle.TypeEnum;
import com.estacionamento.ApiEstacionamento.Vehicle.VehicleDto;
import com.estacionamento.ApiEstacionamento.Vehicle.VehicleEntity;
import com.estacionamento.ApiEstacionamento.Vehicle.VehicleRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class TicketServiceTest {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ParkingRepository parkingRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @BeforeEach
    void setUp() {

        LocalDateTime now = LocalDateTime.now();

        // Criar parking para teste
        ParkingEntity parking = new ParkingEntity();
        parking.setName("BLOCO2");
        parking.setCapacityMoto(25);
        parking.setCapacityCar(30);
        parking.setCapacityMaxMoto(50);
        parking.setCapacityMaxCar(60);
        parking.setTaxaMoto(new BigDecimal("5.0"));
        parking.setTaxaCarro(new BigDecimal("15.0"));
        parking.setTaxaMotoAd(new BigDecimal("3.0"));
        parking.setTaxaCarroAd(new BigDecimal("10.0"));
        parkingRepository.save(parking);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setPlate("ABCD234");
        vehicle.setType(TypeEnum.MOTORCYCLE);
        vehicleRepository.save(vehicle);

        TicketEntity ticket = new TicketEntity();
        ticket.setCodeTicket(520266450L);
        ticket.setParking(parking);
        ticket.setCheckin(now.minusHours(2));
        ticket.setStatus(StatusEnum.USO);
        ticket.setVehicle(vehicle);
        ticketRepository.save(ticket);

    }

    @Test
    void entradaComSucesso() {
        // Arrange
        String local = "BLOCO2";
        VehicleDto dto = new VehicleDto("ABCD123", TypeEnum.MOTORCYCLE);

        ParkingEntity parkingAntes = parkingRepository.findByName(local);
        int capacidadeAntes = parkingAntes.getCapacityMoto();

        // Act
        TicketEntity ticket = ticketService.entrada(dto, local);

        // Assert
        assertThat(ticket).isNotNull();
        assertThat(ticket.getId()).isNotNull();
        assertThat(ticket.getVehicle().getPlate()).isEqualTo("ABCD123");
        assertThat(ticket.getParking().getName()).isEqualTo(local);


        // Verifica se está persistido no banco
        TicketEntity ticketDoBanco = ticketRepository.findById(ticket.getId()).orElseThrow();
        assertThat(ticketDoBanco).isEqualTo(ticket);

        // Verifica se a capacidade diminuiu
        ParkingEntity parkingDepois = parkingRepository.findByName(local);
        assertThat(parkingDepois.getCapacityMoto())
                .isEqualTo(capacidadeAntes - 1);

        // Verifica se o veículo foi criado
        VehicleEntity vehicleDoBanco = vehicleRepository.findByPlate("ABCD123");
        assertThat(vehicleDoBanco).isNotNull();
    }

    @Test
    void saidaComSucesso() {
        Long idTicket = 520266450L;
        String local = "BLOCO2";

        ParkingEntity parkingAntes = parkingRepository.findByName(local);
        int capacidadeAntes = parkingAntes.getCapacityMoto();
        TicketEntity ticketCode = ticketRepository.findBycodeTicket(idTicket);


        TicketEntity ticket = ticketService.saida(local, ticketCode.getCodeTicket());

        assertThat(ticket).isNotNull();
        assertThat(ticket.getId()).isNotNull();
        assertThat(ticket.getVehicle().getPlate()).isEqualTo("ABCD234");
        assertThat(ticket.getStatus()).isEqualTo(StatusEnum.FINALIZADO);
        assertThat(ticket.getPrice()).isEqualTo(new BigDecimal("8.0"));

        ParkingEntity parkingDepois = parkingRepository.findByName(local);
        assertThat(parkingDepois.getCapacityMoto())
                .isEqualTo(capacidadeAntes + 1);

    }


    @Test
    void VeiculoJaOcupado(){

        String local = "BLOCO2";
        VehicleDto dto = new VehicleDto("XYZ9999", TypeEnum.MOTORCYCLE);


        TicketEntity primeiraEntrada = ticketService.entrada(dto, local);
        assertThat(primeiraEntrada).isNotNull();

        assertThatThrownBy(() -> ticketService.entrada(dto, local))
                .isInstanceOf(VehicleIsOccupied.class)
                .hasMessageContaining("já esta com estacionamento ativo");
    }

    @Test
    void saidaParkingNull(){
        // Arrange
        String localInexistente = "BLOCO_INEXISTENTE";  
        Long ticketId = 123456L;

        // Act & Assert
        ParkingisNull exception = assertThrows(
                ParkingisNull.class,
                () -> ticketService.saida(localInexistente, ticketId)
        );

    }

}