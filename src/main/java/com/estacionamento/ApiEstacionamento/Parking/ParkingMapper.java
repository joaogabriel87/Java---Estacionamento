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
                parkingDto.taxa_moto_ad()
        );


    }

    public ParkingDto toParkingDto(ParkingEntity parkingEntity) {
        return new ParkingDto(
                parkingEntity.getName(),
                parkingEntity.getCapacityCar(),
                parkingEntity.getCapacityMoto(),
                parkingEntity.getTaxaCarro(),
                parkingEntity.getTaxaMoto(),
                parkingEntity.getTaxaCarroAd(),
                parkingEntity.getTaxaMotoAd()
        );

    }

    public ResponseParking toResponseParking(ParkingEntity entity) {
        return new ResponseParking(
                entity.getName(),
                entity.getCapacityCar(),
                entity.getCapacityMoto()
        );


    }


}
