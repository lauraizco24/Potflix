package ar.com.ada.potflix.repos;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.potflix.entities.Director;

@Repository
public interface DirectorRepository extends MongoRepository<Director, ObjectId> {

    Director findByNombre(String nombre);
    
}
