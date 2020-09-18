package ar.com.ada.potflix.services;

import java.util.*;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import ar.com.ada.potflix.entities.Actor;
import ar.com.ada.potflix.repos.ActorRepository;

@Service
public class ActorService {

    @Autowired
    ActorRepository actorRepo;

    public boolean crearNuevoActor(Actor actor){
        actorRepo.save(actor);
        return true;
    }

    public Actor crearActor(String nombre, String nivel) {
        if (existeActor(nombre)) {
            return null;
        } else {
            Actor actor = new Actor();
            actor.setNivel(nivel);
            actor.setNombre(nombre);
            
            boolean actorCreado = crearNuevoActor(actor);
            if (actorCreado)
                return actor;
            else
                return null;
        }

    }

    boolean existeActor(String nombre) {
        Actor actor = actorRepo.findByNombre(nombre);
        if (actor != null)
            return true;
        else
            return false;
    }

    public List<Actor> obtenerActores(){
        return actorRepo.findAll();
    }

    public Actor buscarPorId(ObjectId idActor) {
        Optional<Actor> opActor = actorRepo.findById(idActor);

        if (opActor.isPresent())
            return opActor.get();
        else
            return null;
    }

}
