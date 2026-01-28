package com.estacionamento.ApiEstacionamento.Users;

import com.estacionamento.ApiEstacionamento.Parking.ParkingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
@Service
public class UserServices {
    private static final Logger logger = LoggerFactory.getLogger(UserServices.class);

    public Boolean existsByEmail(String email)
            throws URISyntaxException, IOException, InterruptedException {

        logger.info("ExistByEmail initi {}", email);
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjEsInN1YiI6ImpvYWdhYnJpZWw5QGdtYWlsLmNvbSIsImV4cCI6MTc2OTEzMjE2MywiaWF0IjoxNzY5MTI4NTYzfQ.yjLZNKS-0tgyVH3JaNzmM2UFYjgCbwFaTD_hip7fxtI";
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8081/api/user/find/" + email))
                .header("Authorization", "Bearer " + token)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() == 404){
            logger.info("User not found {}", email);
            return false;
        }
        logger.info("User found {}", response);
        return response.statusCode() == 200;
    }

}
