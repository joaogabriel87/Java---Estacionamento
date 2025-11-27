package com.estacionamento.ApiEstacionamento.Service;

import com.estacionamento.ApiEstacionamento.Parking.ParkingEntity;
import com.estacionamento.ApiEstacionamento.Vehicle.TypeEnum;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
@Service
public class priceService {
    public BigDecimal priceCheckout(TypeEnum type, LocalDateTime checkin, ParkingEntity parking) {
        BigDecimal basePrice = (type == TypeEnum.CAR) ? parking.getTaxaCarro() : parking.getTaxaMoto();
        BigDecimal extraPrice = (type == TypeEnum.CAR) ? parking.getTaxaCarroAd() : parking.getTaxaMotoAd();

        Duration duration = Duration.between(checkin, LocalDateTime.now());


        if (duration.toMinutes() <= 15) {
            return BigDecimal.ZERO;
        }


       long totalMinutes = duration.toMinutes();
        long totalHours = (long) Math.ceil(totalMinutes / 60.0);

        if (totalHours <= 1){
            return basePrice;
        }

        long extraHours = totalHours - 1;
        BigDecimal extraCharges =  extraPrice.multiply(BigDecimal.valueOf(extraHours));

        return basePrice.add(extraCharges);


    }
}
