package com.estacionamento.ApiEstacionamento.Controller.Ticket;

import com.estacionamento.ApiEstacionamento.Parking.ParkingDto;
import com.estacionamento.ApiEstacionamento.Parking.ResponseParking;
import com.estacionamento.ApiEstacionamento.Ticket.ResponseTicket;
import com.estacionamento.ApiEstacionamento.Vehicle.VehicleDto;
import com.estacionamento.ApiEstacionamento.Parking.ParkingEntity;
import com.estacionamento.ApiEstacionamento.Ticket.TicketEntity;
import com.estacionamento.ApiEstacionamento.Vehicle.TypeEnum;
import com.estacionamento.ApiEstacionamento.Parking.ParkingRepository;
import com.estacionamento.ApiEstacionamento.Ticket.TicketRepository;
import com.estacionamento.ApiEstacionamento.Vehicle.VehicleRepository;
import com.estacionamento.ApiEstacionamento.Parking.ParkingService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TicketControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ParkingRepository parkingRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ParkingService parkingService;

    @Autowired
    @LocalServerPort
    private int port;

    private String baseUrl;


    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/ticket";
        ticketRepository.deleteAll();
        vehicleRepository.deleteAll();
        parkingRepository.deleteAll();
    }

    @Test
    @Order(1)
    @Transactional
    void shouldCreateTicketWhenVehicleEntersParking() {

        ParkingDto parkingDto = new ParkingDto(
                "BLOCO3",
                54,
                86,
                new BigDecimal("15.0"),
                new BigDecimal("8.0"),
                new BigDecimal("5.0"),
                new BigDecimal("2.0")
        );


        String parkingUrl = "http://localhost:" + port + "/api/parking";
        ResponseEntity<ResponseParking> parkingResponse = restTemplate.postForEntity(
                parkingUrl,
                parkingDto,
                ResponseParking.class
        );

        assertThat(parkingResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        String parkingName = parkingResponse.getBody().nome();

        VehicleDto vehicleDto = new VehicleDto(
                "1234567",
                TypeEnum.CAR
        );


        String url = baseUrl + "/" + parkingName;

        ResponseEntity<ResponseTicket> response = restTemplate.postForEntity(
                url,
                vehicleDto,
                ResponseTicket.class
        );

        System.out.println("Status: " + response.getStatusCode());
        System.out.println("Body: " + response.getBody());



        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().codigo()).isNotNull();


        TicketEntity savedTicket = ticketRepository.findBycodeTicket(response.getBody().codigo());
        assertThat(savedTicket).isNotNull();
        assertThat(savedTicket.getVehicle().getPlate()).isEqualTo("1234567");


        ParkingEntity updatedParking = parkingRepository.findByName(parkingDto.name());
        assertThat(updatedParking.getTickets()).hasSize(1);
        assertThat(updatedParking.getCapacityCar()).isEqualTo(53);
    }

}