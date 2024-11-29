package com.Reservas.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.Reservas.app.entity.Recepcionista;

public interface RecepcionistaRepository extends MongoRepository<Recepcionista, String> {
	@Query("SELECT e FROM Estudiante e ORDER BY e.puntaje DESC") // Cambia 'puntaje' al nombre correcto del campo que guarda la puntuación
    List<Recepcionista> findTop3ByOrderByPuntajeDesc();

}
