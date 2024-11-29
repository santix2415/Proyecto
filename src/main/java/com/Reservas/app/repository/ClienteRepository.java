package com.Reservas.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.Reservas.app.entity.Cliente;

public interface ClienteRepository extends MongoRepository<Cliente, String> {

}
