package ar.com.ada.potflix.controllers;

import java.util.*;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.potflix.entities.Pelicula;
import ar.com.ada.potflix.models.request.ModifPelicula;
import ar.com.ada.potflix.models.request.PeliculaReq;
import ar.com.ada.potflix.models.response.GenericResponse;
import ar.com.ada.potflix.services.PeliculaService;

@RestController
public class PeliculaController {

    @Autowired
    PeliculaService pS;

    @PostMapping("/pelicula")
    public ResponseEntity<GenericResponse> crearActor(@RequestBody PeliculaReq peliReq) {

        Pelicula pelicula = pS.crearPelicula(peliReq.titulo, peliReq.generos, peliReq.director, peliReq.actores,
                peliReq.filmadaEnImax);
        if (pelicula == null) {
            return ResponseEntity.badRequest().build();
        }
        GenericResponse gR = new GenericResponse();
        gR.isOk = true;
        gR.id = pelicula.get_id();
        gR.mensaje = "La pelicula fue añadida exitosamente";
        return ResponseEntity.ok(gR);

    }

    @GetMapping("/peliculas")
    public ResponseEntity<List<Pelicula>> traerTodasLasPeliculas() {

        return ResponseEntity.ok(pS.obtenerPeliculas());
    }

    @GetMapping("/peliculas/{id}")
    public ResponseEntity<Pelicula> traerPeliculaPorId(@PathVariable ObjectId id) {

        return ResponseEntity.ok(pS.buscarPorId(id));
    }

    @GetMapping("/peliculas/titulo/{titulo}")
    public ResponseEntity<Pelicula> traerPeliculaPorTitulo(@PathVariable String titulo) {

        return ResponseEntity.ok(pS.buscarPorTitulo(titulo));
    }

    // /peliculas?genero=Ciencia%20Ficción
    @GetMapping("/peliculas/genero/{genero}")
    public ResponseEntity<List<Pelicula>> traerPeliculasPorGenero(@PathVariable String genero) {

        return ResponseEntity.ok(pS.buscarPorGenero(genero));

    }

    @PutMapping("/peliculas/{id}")
    public ResponseEntity<GenericResponse> modificarPelicula(@PathVariable ObjectId id, ModifPelicula modifPeli) {
        Pelicula pelicula = pS.buscarPorId(id);
        if (pelicula == null) {
            return ResponseEntity.notFound().build();
        }

        else {

            Pelicula peliModificada = pS.modificarPelicula(pelicula, modifPeli);
            if (peliModificada == null) {
                return ResponseEntity.badRequest().build();
            } else {
                GenericResponse gR = new GenericResponse();
                gR.id = peliModificada.get_id();
                gR.isOk = true;
                gR.mensaje = "Los datos de la pelicula fueron actualizados exitosamente";

                return ResponseEntity.ok(gR);

            }
        }
    }


@PutMapping("/peliculas/calificacion/{id}")
ResponseEntity<GenericResponse> calificarLaPelicula(@PathVariable ObjectId id, double calificacion){

    Pelicula pelicula = pS.buscarPorId(id);
    if(pelicula ==null){
        return ResponseEntity.notFound().build();
    }else{
       Pelicula peliCalificada = pS.calificarPelicula(pelicula,calificacion);
       GenericResponse gR = new GenericResponse();
       gR.id = peliCalificada.get_id();
       gR.isOk = true;
       gR.mensaje = "enviaste tu calificacion exitosamente, el puntaje de la pelicula es: "+ peliCalificada.getCalificacion();
       return ResponseEntity.ok(gR);
    }

}




}