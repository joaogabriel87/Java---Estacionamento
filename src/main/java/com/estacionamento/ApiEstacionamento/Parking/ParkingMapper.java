package com.estacionamento.ApiEstacionamento.Parking;

import org.springframework.stereotype.Component;

@Component
public class ParkingMapper {

    ParkingRepository parkingRepository;

    public ParkingEntity toParkingEntity(RequestCreateParking requestCreateParking) {
        return new ParkingEntity(
                requestCreateParking.name(),
                requestCreateParking.taxa_carro(),
                requestCreateParking.taxa_moto(),
                requestCreateParking.taxa_carro_ad(),
                requestCreateParking.taxa_moto_ad(),
                requestCreateParking.capacityTotalCar(),
                requestCreateParking.capacityTotalMoto()
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
