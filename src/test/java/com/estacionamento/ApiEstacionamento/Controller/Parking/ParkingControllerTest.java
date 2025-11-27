package com.estacionamento.ApiEstacionamento.Controller.Parking;

import com.estacionamento.ApiEstacionamento.Parking.ParkingDto;
import com.estacionamento.ApiEstacionamento.Parking.ResponseParking;
import com.estacionamento.ApiEstacionamento.Parking.ParkingEntity;
import com.estacionamento.ApiEstacionamento.Parking.ParkingRepository;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ParkingControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ParkingRepository parkingRepository;

    @Autowired
    @LocalServerPort
    private int port;

    private String baseUrl;


    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/parking";
    }

    @Test
    @Order(1)
    void enterParking() {

        ParkingDto dto = new ParkingDto(
                "BLOCO12",
                54,
                86,
                new BigDecimal("15.0"),
                new BigDecimal("8.0"),
                new BigDecimal("5.0"),
                new BigDecimal("2.0")
        );


        ResponseEntity<ResponseParking> response = restTemplate.postForEntity(
                baseUrl,
                dto,
                ResponseParking.class
        );


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().nome()).isEqualTo("BLOCO12");
        assertThat(response.getBody().quantidade_carro()).isEqualTo(54);
        assertThat(response.getBody().quantidade_moto()).isEqualTo(86);

        ParkingEntity saved = parkingRepository.findByName("BLOCO12");
        assertThat(saved).isNotNull();
        assertThat(saved.getName()).isEqualTo("BLOCO12");
    }

}