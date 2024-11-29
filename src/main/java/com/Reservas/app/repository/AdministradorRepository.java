package com.Reservas.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.Reservas.app.entity.Administrador;

public interface AdministradorRepository extends MongoRepository<Administrador, String> {

}
