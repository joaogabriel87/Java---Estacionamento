package com.estacionamento.ApiEstacionamento.Service.ParkingService;

import com.estacionamento.ApiEstacionamento.Entity.Vehicle.TypeEnum;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
@Service
public class priceService {
    public Double priceCheckout(TypeEnum type, LocalDateTime checkin) {
        double basePrice = (type == TypeEnum.CAR) ? 15.0 : 8.0;
        double extraPrice = (type == TypeEnum.CAR) ? 5.0 : 2.0;

        Duration duration = Duration.between(checkin, LocalDateTime.now());


        if (duration.toMinutes() <= 15) {
            return 0.0;
        }


        long extraHours = Math.max(0, duration.toHours());

        return basePrice + (extraHours * extraPrice);
    }
}
