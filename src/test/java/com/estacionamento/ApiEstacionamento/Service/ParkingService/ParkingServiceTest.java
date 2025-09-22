package com.estacionamento.ApiEstacionamento.Service.ParkingService;

import com.estacionamento.ApiEstacionamento.Dto.VehicleDto.VehicleDto;
import com.estacionamento.ApiEstacionamento.Entity.Parking.ParkingEntity;
import com.estacionamento.ApiEstacionamento.Entity.Parking.ParkingRecord;
import com.estacionamento.ApiEstacionamento.Entity.Vehicle.TypeEnum;
import com.estacionamento.ApiEstacionamento.Entity.Vehicle.VehicleEntity;
import com.estacionamento.ApiEstacionamento.Erro.ParkingIsOccupiedTrue;
import com.estacionamento.ApiEstacionamento.Erro.ParkingisNull;
import com.estacionamento.ApiEstacionamento.Erro.VehicleIsOccupied;
import com.estacionamento.ApiEstacionamento.Mapper.ParkingMapper;
import com.estacionamento.ApiEstacionamento.Repository.ParkingRecordRepository;
import com.estacionamento.ApiEstacionamento.Repository.ParkingRepository;
import com.estacionamento.ApiEstacionamento.Repository.VehicleRepository;
import com.estacionamento.ApiEstacionamento.Service.VehicleService.VehicleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.InstanceOfAssertFactories.optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.data.util.Predicates.isTrue;

@DataJpaTest
@ActiveProfiles("test")
class
ParkingServiceTest {
    @Mock
    ParkingRepository parkingRepository;
    @Mock
    VehicleRepository vehicleRepository;
    @Mock
    ParkingMapper parkingMapper;
    @Mock
    ParkingRecordRepository parkingRecordRepository;
    @Mock
    priceService priceService;
    @Mock
    codeService codeService;
    @Mock
    VehicleService vehicleService;
    @InjectMocks
    ParkingService parkingService;


    @Test
    @DisplayName("Entrada ao estacionamento realizada com sucesso")
    void entrada1() {
        VehicleDto v = new VehicleDto("ABC123", TypeEnum.CAR);
        VehicleEntity vehicleEntity = new VehicleEntity(v.placa(), v.tipo());

        // 1) Mocka que o veículo NÃO está ocupado
        when(vehicleRepository.isVehicleParkingOccupied(v.placa()))
                .thenReturn(Optional.of(false));

        // 2) Mocka a criação do veículo
        when(vehicleService.create(v))
                .thenReturn(vehicleEntity);

        // 3) Mocka a geração do código
        when(codeService.CodeParking(v.tipo()))
                .thenReturn("CODE123");

        // 4) Mocka o save do parking para retornar o mesmo objeto que foi passado
        when(parkingRepository.save(any(ParkingEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ParkingEntity parkingEntity = parkingService.entrada(v);

        // Assert
        assertThat(parkingEntity.getOccupied()).isTrue();
        assertThat(parkingEntity.getCurrent().getPrice()).isEqualTo(0.0);
        assertThat(parkingEntity.getCurrent().getVehicle().getPlate()).isEqualTo(v.placa());
        assertThat(parkingEntity.getCode()).isEqualTo("CODE123");

        // Verify (opcional, mas bom pra confirmar que mocks foram chamados)
        verify(vehicleRepository).isVehicleParkingOccupied(v.placa());
        verify(vehicleService).create(v);
        verify(codeService).CodeParking(v.tipo());
        verify(parkingRepository).save(any(ParkingEntity.class));
    }
    @Test
    @DisplayName("Testando caindo na excessão")
    void entrada2() {
        VehicleDto v = new VehicleDto("ABC123", TypeEnum.CAR);

        when(vehicleRepository.isVehicleParkingOccupied(v.placa())).thenReturn(Optional.of(true));

        assertThatThrownBy(() -> parkingService.entrada(v)).isInstanceOf(VehicleIsOccupied.class);

        verify(vehicleRepository).isVehicleParkingOccupied(v.placa());
        verifyNoInteractions(vehicleService, codeService, parkingRepository);
    }

    @Test
    @DisplayName("Saída do estacionamento realizada com sucesso")
    void saida() {
        String code = "ABC123-CODE";

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setPlate("ABC123");
        vehicle.setType(TypeEnum.CAR);

        ParkingRecord record = new ParkingRecord();
        record.setCheckin(LocalDateTime.now().minusHours(2));
        record.setVehicle(vehicle);
        record.setPrice(0.0);

        ParkingEntity parking = new ParkingEntity();
        parking.setCode(code);
        parking.setOccupied(true);
        parking.setCurrent(record);
        parking.getRecords().add(record);

        vehicle.setParking(parking);

        when(parkingRepository.findByCode(code)).thenReturn(parking);
        when(priceService.priceCheckout(record.getVehicle().getType(), record.getCheckin()))
                .thenReturn(25.0);
        when(parkingRepository.save(any(ParkingEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));


        ParkingEntity result = parkingService.saida(code);


        assertThat(result.getOccupied()).isFalse();
        assertThat(result.getRecords()).isNotEmpty();

        ParkingRecord finalRecord = result.getRecords().get(0);
        assertThat(finalRecord.getCheckout()).isNotNull();
        assertThat(finalRecord.getPrice()).isEqualTo(25.0);


        verify(parkingRepository).findByCode(code);
        verify(priceService).priceCheckout(vehicle.getType(), record.getCheckin());
        verify(parkingRepository).save(parking);
    }

    @Test
    @DisplayName("Testando caindo na excessão")
    void saida2() {

        when(parkingRepository.findByCode("ABC123")).thenReturn(null);

        assertThatThrownBy(() -> parkingService.saida("ABC123")).isInstanceOf(ParkingisNull.class);

        verify(parkingRepository).findByCode("ABC123");

    }

    @Test
    @DisplayName("Deve lançar ParkingIsOccupiedTrue se estacionamento já estiver desocupado")
    void saide3() {
        // Arrange
        String code = "ABC123-CODE";

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setPlate("ABC123");
        vehicle.setType(TypeEnum.CAR);

        ParkingRecord record = new ParkingRecord();
        record.setCheckin(LocalDateTime.now().minusHours(2));
        record.setVehicle(vehicle);

        ParkingEntity parking = new ParkingEntity();
        parking.setCode(code);
        parking.setOccupied(false);
        parking.setCurrent(record);
        parking.getRecords().add(record);

        vehicle.setParking(parking);

        when(parkingRepository.findByCode(code)).thenReturn(parking);


        ParkingIsOccupiedTrue exception = assertThrows(
                ParkingIsOccupiedTrue.class,
                () -> parkingService.saida(code)
        );

        assertThat(exception.getMessage())
                .isEqualTo("Estacionamento já está finalizado para o código: " + code);


        verify(parkingRepository).findByCode(code);
        verify(parkingRepository, never()).save(any(ParkingEntity.class));
        verifyNoInteractions(priceService);
    }

    @Test
    void report() {
    }
}