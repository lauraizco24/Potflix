package ar.com.ada.potflix.controllers;

import java.util.*;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.potflix.entities.Serie;
import ar.com.ada.potflix.entities.Temporada;
import ar.com.ada.potflix.models.request.ModifSerie;
import ar.com.ada.potflix.models.request.SerieReq;
import ar.com.ada.potflix.models.response.GenericResponse;
import ar.com.ada.potflix.services.SerieService;

@RestController
public class SerieController {
   
    
    @Autowired
    SerieService sS;

    @PostMapping("/series")
    public ResponseEntity<GenericResponse> crearActor(@RequestBody SerieReq serieReq) {

        Serie serie = sS.crearSerie(serieReq.titulo, serieReq.generos, serieReq.director,serieReq.actores,
        serieReq.temporadas);
        if (serie == null) {
            return ResponseEntity.badRequest().build();
        }
        GenericResponse gR = new GenericResponse();
        gR.isOk = true;
        gR.id = serie.get_id().toHexString();
        gR.mensaje = "La Serie fue añadida exitosamente";
        return ResponseEntity.ok(gR);

    }

    @GetMapping("/series")
    public ResponseEntity<List<Serie>> traerTodasLasSeries() {

        return ResponseEntity.ok(sS.obtenerSerie());
    }

    @GetMapping("/series/{id}")
    public ResponseEntity<Serie> traerSeriePorId(@PathVariable ObjectId id) {

        return ResponseEntity.ok(sS.buscarPorId(id));
    }

    @GetMapping("/series/titulo/{titulo}")
    public ResponseEntity<Serie> traerSeriePorTitulo(@PathVariable String titulo) {

        return ResponseEntity.ok(sS.buscarPorTitulo(titulo));
    }

    // /series?genero=Ciencia%20Ficción
    @GetMapping("/peliculas/genero/{genero}")
    public ResponseEntity<List<Serie>> traerSeriesPorGenero(@PathVariable String genero) {

        return ResponseEntity.ok(sS.buscarPorGenero(genero));

    }

    @GetMapping("/serie/temporadas/{id}")
    public ResponseEntity<List<Temporada>> traerTemporadasPorSerieId(@PathVariable ObjectId id) {

        return ResponseEntity.ok(sS.traerTemporadasPorSerieId(id));
    } 

  


    @PutMapping("/peliculas/{id}")
    public ResponseEntity<GenericResponse> modificarPelicula(@PathVariable ObjectId id, ModifSerie modifSerie) {
        Serie serie = sS.buscarPorId(id);
        if (serie == null) {
            return ResponseEntity.notFound().build();
        }

        else {

            Serie serieModificada = sS.modificarPelicula(serie, modifSerie);
            if (serieModificada == null) {
                return ResponseEntity.badRequest().build();
            } else {
                GenericResponse gR = new GenericResponse();
                gR.id = serieModificada.get_id().toHexString();
                gR.isOk = true;
                gR.mensaje = "Los datos de la Serie fueron actualizados exitosamente";

                return ResponseEntity.ok(gR);

            }
        }
    }


@PutMapping("/peliculas/calificacion/{id}")
ResponseEntity<GenericResponse> calificarLaSerie(@PathVariable ObjectId id, double calificacion){

    Serie serie = sS.buscarPorId(id);
    if(serie ==null){
        return ResponseEntity.notFound().build();
    }else{
       Serie serieCalificada = sS.calificarSerie(serie, calificacion);
       GenericResponse gR = new GenericResponse();
       gR.id = serieCalificada.get_id().toHexString();
       gR.isOk = true;
       gR.mensaje = "enviaste tu calificacion exitosamente, el puntaje de la Serie es: "+ serieCalificada.getCalificacion();
       return ResponseEntity.ok(gR);
    }

}



}
