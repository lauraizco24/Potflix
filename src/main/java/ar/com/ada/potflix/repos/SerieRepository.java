package ar.com.ada.potflix.repos;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.potflix.entities.Serie;
@Repository
public interface SerieRepository extends MongoRepository<Serie,ObjectId>{
    Serie findByTitulo(String titulo);
}
