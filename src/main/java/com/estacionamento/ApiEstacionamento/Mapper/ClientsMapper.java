package com.estacionamento.ApiEstacionamento.Mapper;

import com.estacionamento.ApiEstacionamento.Dto.ClientsDto.ClientsDto;
import com.estacionamento.ApiEstacionamento.Dto.ClientsDto.ResponseClient;
import com.estacionamento.ApiEstacionamento.Dto.VehicleDto.VehicleDto;
import com.estacionamento.ApiEstacionamento.Entity.Clients.ClientsEntity;
import com.estacionamento.ApiEstacionamento.Entity.Vehicle.VehicleEntity;
import org.springframework.stereotype.Component;

@Component
public class ClientsMapper {

    public ClientsEntity toEntity(ClientsDto dto){
        return new ClientsEntity(
                dto.document(),
                dto.name(),
                dto.phone()
        );
    }

    public ClientsDto toDto(ClientsEntity entity){
        return new ClientsDto(
                entity.getDocument(),
                entity.getName(),
                entity.getPhone()
        );
    }

    public ResponseClient toResponse(ClientsEntity entity){
        return new ResponseClient(
                entity.getId(),
                entity.getName(),
                entity.getPhone()
        );
    }


}
