# API Estacionamento

## Visão Geral

Aplicação backend desenvolvida em **Java + Spring Boot** para gerenciamento de estacionamento, incluindo controle de vagas, veículos, tickets de entrada/saída, cálculo de preços e mensageria com RabbitMQ. O projeto segue uma arquitetura em camadas (Controller, Service, Repository) e expõe APIs REST.

## Principais Tecnologias

* Java
* Spring Boot
* Spring Data JPA
* Hibernate
* RabbitMQ
* REST API
* Maven
* JUnit

## Arquitetura

```
Controller -> Service -> Repository -> Database
                 |
              Mapper / DTO
```

## Módulos Principais

### Parking

Responsável pelo gerenciamento das vagas.

* `ParkingController`
* `ParkingService`
* `ParkingRepository`
* `ParkingEntity`
* DTOs: `RequestCreateParking`, `ResponseParking`, `ResponseParkingVaga`

Funcionalidades:

* Criação de vagas
* Consulta de disponibilidade
* Relatórios de ocupação

### Vehicle

Gerenciamento de veículos.

* `VehicleController`
* `VehicleService`
* `VehicleRepository`
* `VehicleEntity`

Funcionalidades:

* Cadastro de veículos
* Associação veículo/vaga
* Integração via RabbitMQ (`VehicleProducer`)

### Ticket

Controle de entrada e saída de veículos.

* `TicketController`
* `TicketService`
* `TicketRepository`
* `TicketEntity`

Funcionalidades:

* Registro de entrada
* Registro de saída
* Cálculo de tempo e valor

### Services Auxiliares

* `priceService`: cálculo de valores
* `codeService`: geração de códigos

### Tratamento de Erros

* `GlobalExceptionHandler`
* Exceções customizadas como `ParkingIsOccupiedTrue`, `VehicleIsOccupied`

## Testes

O projeto possui testes unitários e de integração utilizando JUnit, cobrindo Controllers, Services e Repositories.

## Execução do Projeto

1. Configurar banco de dados e RabbitMQ
2. Ajustar `application.properties`
3. Executar:

```bash
mvn spring-boot:run
```

## Objetivo do Projeto

Demonstrar domínio em backend Java, arquitetura REST, mensageria e boas práticas de desenvolvimento.
