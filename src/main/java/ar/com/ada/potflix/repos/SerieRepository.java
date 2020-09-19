package ar.com.ada.potflix.repos;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import ar.com.ada.potflix.entities.Serie;
@Repository
public interface SerieRepository extends MongoRepository<Serie,ObjectId>{
    Serie findByTitulo(String titulo);

    Serie findBy_id(ObjectId _id);

    //En este caso busca por "actores._id", y el "." se reemplaza por un "_"
    //entonces qeuda Actores__id
    List<Serie> findByActores__id(ObjectId actorId);

    //Es igual al de arriba, excepto que pongo el query como filtro
    @Query(value = "{ 'actores._id' : ?0 }")
    List<Serie> findSeriesByActor_IdEntero(ObjectId muestraId);

    @Query(value = "{ 'actores._id' : ?0 }", fields = "{ '_id' : 1, 'titulo' : 1, 'actores' : 1 }")
    List<Serie> findSeriesByActores_IdSoloInfoActor(ObjectId muestraId);

}
