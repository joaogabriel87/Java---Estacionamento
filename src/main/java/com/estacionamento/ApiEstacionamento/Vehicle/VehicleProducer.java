package com.estacionamento.ApiEstacionamento.Vehicle;

import com.estacionamento.ApiEstacionamento.Config.RabbitConfig;
import com.estacionamento.ApiEstacionamento.Parking.ParkingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateConfigurer;
import org.springframework.stereotype.Service;

@Service
public class VehicleProducer {
    private final RabbitTemplate rabbitTemplate;
    private static final Logger logger = LoggerFactory.getLogger(VehicleProducer.class);

    public VehicleProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendVehicleCreated(RequestLinkVehicle requestLinkVehicle) {
        logger.info("sendVehicleCreatedRabbit {}", requestLinkVehicle);
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE,"vehicleRoutingKey", requestLinkVehicle);
    }
}
