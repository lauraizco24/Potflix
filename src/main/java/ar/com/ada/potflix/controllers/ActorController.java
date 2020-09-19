package ar.com.ada.potflix.controllers;

import java.util.*;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.potflix.entities.Actor;
import ar.com.ada.potflix.entities.Serie;
import ar.com.ada.potflix.models.request.ActorReq;
import ar.com.ada.potflix.models.request.ModifActor;
import ar.com.ada.potflix.models.response.GenericResponse;
import ar.com.ada.potflix.services.ActorService;
import ar.com.ada.potflix.services.SerieService;

@RestController
public class ActorController {
    @Autowired
    ActorService aS;

    @Autowired
    SerieService serieService;

    @PostMapping("/actores")
    public ResponseEntity<GenericResponse> crearActor(@RequestBody ActorReq aR) {

        Actor actor = aS.crearActor(aR.nombre, aR.nivel);
        if (actor == null) {
            return ResponseEntity.badRequest().build();
        }
        GenericResponse gR = new GenericResponse();
        gR.isOk = true;
        gR.id = actor.get_id();
        gR.mensaje = "El actor fue creado exitosamente";
        return ResponseEntity.ok(gR);

    }

    @GetMapping("/actores")
    public ResponseEntity<List<Actor>> traerTodosLosActores() {

        return ResponseEntity.ok(aS.obtenerActores());
    }

    @PutMapping("/actores/{id}")
    ResponseEntity<GenericResponse> modificarActor(@PathVariable ObjectId id, @RequestBody ModifActor modifActor) {
        GenericResponse gR = new GenericResponse();
        Actor actor = aS.buscarPorId(id);
        if (actor == null) {
            return ResponseEntity.badRequest().build();
        }

        else {

            actor.setNombre(modifActor.nombre);
            actor.setNivel(modifActor.nivel);
            aS.crearNuevoActor(actor);
            gR.id = actor.get_id();
            gR.isOk = true;
            gR.mensaje = "El actor fue encontrado y modificado con exito";

        }
        return ResponseEntity.ok(gR);

    }

    @GetMapping("/actores/{id}/series")
    ResponseEntity<List<Serie>> seriesActor(@PathVariable ObjectId id) {

        return ResponseEntity.ok(serieService.obtenerSeriesByActor(id));

    }
}
