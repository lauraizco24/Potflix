package ar.com.ada.potflix.repos;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.potflix.entities.Pelicula;

@Repository
public interface PeliculaRepository  extends MongoRepository<Pelicula,ObjectId>{
    
    Pelicula findByTitulo(String titulo);
}
