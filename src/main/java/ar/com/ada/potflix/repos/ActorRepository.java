package ar.com.ada.potflix.repos;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.potflix.entities.Actor;

@Repository
public interface ActorRepository extends MongoRepository<Actor, ObjectId> {
    
    Actor findByNombre(String nombre);
}
