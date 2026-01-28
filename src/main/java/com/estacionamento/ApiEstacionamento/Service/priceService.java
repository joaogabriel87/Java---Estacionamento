package com.estacionamento.ApiEstacionamento.Service;

import com.estacionamento.ApiEstacionamento.Parking.ParkingEntity;
import com.estacionamento.ApiEstacionamento.Parking.ParkingService;
import com.estacionamento.ApiEstacionamento.Vehicle.TypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
@Service
public class priceService {
    private static final Logger logger = LoggerFactory.getLogger(priceService.class);

    public BigDecimal priceCheckout(TypeEnum type, LocalDateTime checkin, ParkingEntity parking) {
        logger.info("priceCheckout");
        BigDecimal basePrice = (type == TypeEnum.CAR) ? parking.getTaxaCarro() : parking.getTaxaMoto();
        BigDecimal extraPrice = (type == TypeEnum.CAR) ? parking.getTaxaCarroAd() : parking.getTaxaMotoAd();

        logger.info("baseprice {}", basePrice);
        logger.info("extraprice {}", extraPrice);

        Duration duration = Duration.between(checkin, LocalDateTime.now());

        logger.info("duration {}", duration);


        if (duration.toMinutes() <= 15) {
            return BigDecimal.ZERO;
        }


       long totalMinutes = duration.toMinutes();
        logger.info("totalMinutes {}", totalMinutes);
        long totalHours = (long) Math.ceil(totalMinutes / 60.0);
        logger.info("totalHours {}", totalHours);

        if (totalHours <= 1){
            logger.info("basePrice return {}", basePrice);
            return basePrice;
        }

        long extraHours = totalHours - 1;
        BigDecimal extraCharges =  extraPrice.multiply(BigDecimal.valueOf(extraHours));
        logger.info("extraCharges {}", extraCharges);

        BigDecimal newPrice = basePrice.add(extraCharges);
        logger.info("baseprice extra {}", newPrice);
        return newPrice;


    }
}
