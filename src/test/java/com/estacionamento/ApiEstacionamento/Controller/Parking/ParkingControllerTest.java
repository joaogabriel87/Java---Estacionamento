package com.estacionamento.ApiEstacionamento.Controller.Parking;

import com.estacionamento.ApiEstacionamento.Dto.ParkingDto.ResponseParking;
import com.estacionamento.ApiEstacionamento.Dto.ParkingDto.ResponseParkingExit;
import com.estacionamento.ApiEstacionamento.Dto.VehicleDto.VehicleDto;
import com.estacionamento.ApiEstacionamento.Entity.Parking.ParkingEntity;
import com.estacionamento.ApiEstacionamento.Entity.Vehicle.TypeEnum;
import com.estacionamento.ApiEstacionamento.Mapper.ParkingMapper;
import com.estacionamento.ApiEstacionamento.Repository.ParkingRepository;
import com.estacionamento.ApiEstacionamento.Service.ParkingService.ParkingService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    @Autowired
    private ParkingService parkingService;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/parking";
    }

    @Test
    @Order(1)
    @Transactional
    void enterParking() {
        VehicleDto dto = new VehicleDto("7654324", TypeEnum.CAR);

        ResponseEntity<ResponseParking> response = restTemplate.postForEntity(baseUrl, dto, ResponseParking.class);
        System.out.println("DEBUG - Status: " + response.getStatusCode());
        System.out.println("DEBUG - Body: " + response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();

        List<ParkingEntity> parkingEntries = parkingRepository.findAll();
        assertThat(parkingEntries).hasSize(1);

        ParkingEntity parking = parkingEntries.get(0);
        assertThat(parking.getCurrent().getVehicle().getPlate()).isEqualTo("7654324");
        assertThat(parking.getCurrent().getVehicle().getType()).isEqualTo(TypeEnum.CAR);


    }

    @Test
    @Transactional
    @Order(2)
    void exitParking() {

        VehicleDto dto = new VehicleDto("7654324", TypeEnum.CAR);

        ResponseEntity<ResponseParking> response1 = restTemplate.postForEntity(baseUrl, dto, ResponseParking.class);
        String parkingCode = response1.getBody().code();




        String url =  UriComponentsBuilder.fromHttpUrl(baseUrl).path("/{code}/checkout").buildAndExpand(parkingCode).toUriString();


        ResponseEntity<ResponseParkingExit> response2 = restTemplate.exchange(url,  HttpMethod.PATCH,HttpEntity.EMPTY, ResponseParkingExit.class);
        assertThat(response2.getBody()).isNotNull();
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response2.getBody().current().price()).isEqualTo(0.0);

    }
}