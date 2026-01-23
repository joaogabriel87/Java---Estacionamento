package com.estacionamento.ApiEstacionamento.Vehicle;

import com.estacionamento.ApiEstacionamento.Config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateConfigurer;
import org.springframework.stereotype.Service;

@Service
public class VehicleProducer {
    private final RabbitTemplate rabbitTemplate;

    public VehicleProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendVehicleCreated(RequestLinkVehicle requestLinkVehicle) {
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE,"vehicleRoutingKey", requestLinkVehicle);
    }
}
