package com.estacionamento.ApiEstacionamento.Ticket;

import com.estacionamento.ApiEstacionamento.Parking.ParkingEntity;
import com.estacionamento.ApiEstacionamento.Parking.ParkingRepository;
import com.estacionamento.ApiEstacionamento.Vehicle.TypeEnum;
import com.estacionamento.ApiEstacionamento.Vehicle.VehicleDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TicketController.class)
class TicketControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TicketService ticketService;

    @MockitoBean
    private TicketMapper ticketMapper;


    @Test
    void entradaComSucesso() throws Exception {

        VehicleDto dto = new VehicleDto("ABCD123", TypeEnum.MOTORCYCLE);
        String local = "BLOCO1";

        TicketEntity ticketMock = new TicketEntity();
        ticketMock.setId(1L);
        ticketMock.setCodeTicket(987654321L);

        when(ticketService.entrada(any(VehicleDto.class), eq(local)))
                .thenReturn(ticketMock);


        mockMvc.perform(post("/api/ticket/enter/{localParking}", local)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isOk());

        verify(ticketService, times(1)).entrada(any(VehicleDto.class), eq(local));
    }

    @Test
    void saidaComSucesso() throws Exception {
        String local = "BLOCO1";

        TicketEntity ticketMock = new TicketEntity();
        ticketMock.setId(1L);
        ticketMock.setCodeTicket(987654321L);

        when(ticketService.saida(any(String.class), eq(ticketMock.getCodeTicket())))
                .thenReturn(ticketMock);
        mockMvc.perform(post("/api/ticket/exit/{codeTicket}/{localParking}", ticketMock.getCodeTicket(), local)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                        .andDo(print())
                        .andExpect(status().isOk());


        verify(ticketService, times(1)).saida(any(String.class), eq(ticketMock.getCodeTicket()));


    }
}