package com.estacionamento.ApiEstacionamento.Parking;

import org.springframework.stereotype.Component;

@Component
public class ParkingMapper {

    ParkingRepository parkingRepository;

    public ParkingEntity toParkingEntity(ParkingDto parkingDto) {
        return new ParkingEntity(
                parkingDto.name(),
                parkingDto.capacityCar(),
                parkingDto.capacityMoto(),
                parkingDto.taxa_carro(),
                parkingDto.taxa_moto(),
                parkingDto.taxa_carro_ad(),
                parkingDto.taxa_moto_ad(),
                parkingDto.capacityTotalCar(),
                parkingDto.capacityTotalMoto()
        );
    }


    public ResponseParking toResponseParking(ParkingEntity entity) {
        return new ResponseParking(
                entity.getName(),
                entity.getCapacityCar(),
                entity.getCapacityMoto()
        );

    }
    public ResponseParkingVaga toResponseVagas(ParkingEntity entity){
        return new ResponseParkingVaga(
                entity.getCapacityMaxCar(),
                entity.getCapacityMaxCar() - entity.getCapacityCar(),
                entity.getCapacityMaxMoto(),
                entity.getCapacityMaxMoto() - entity.getCapacityMoto()
        );
        }


}
