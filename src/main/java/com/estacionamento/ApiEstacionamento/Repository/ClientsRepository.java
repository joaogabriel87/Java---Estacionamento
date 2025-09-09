package com.estacionamento.ApiEstacionamento.Repository;

import com.estacionamento.ApiEstacionamento.Entity.Clients.ClientsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientsRepository extends JpaRepository<ClientsEntity, Long> {
ClientsEntity findByDocument(String docs);

}
