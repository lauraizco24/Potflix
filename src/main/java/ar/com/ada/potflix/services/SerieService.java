package ar.com.ada.potflix.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.potflix.entities.Actor;
import ar.com.ada.potflix.entities.Director;
import ar.com.ada.potflix.entities.Genero;
import ar.com.ada.potflix.entities.Serie;
import ar.com.ada.potflix.entities.Temporada;
import ar.com.ada.potflix.models.request.ModifSerie;
import ar.com.ada.potflix.repos.SerieRepository;

@Service
public class SerieService {

    @Autowired
    SerieRepository serieRepo;

    public boolean crearNuevaSerie(Serie serie) {
        serieRepo.save(serie);
        return true;
    }

    public Serie crearSerie(String titulo, List<Genero> generos, Director director, List<Actor> actores, List<Temporada> temporadas) {
        if (existeSerie(titulo)) {
            return null;
        } else {
            Serie serie = new Serie();
            serie.setTitulo(titulo);
            serie.setGeneros(generos);
            serie.setDirector(director);
            serie.setActores(actores);
            serie.setTemporadas(temporadas);

            boolean serieCreada = crearNuevaSerie(serie);
            if (serieCreada)
                return serie;
            else
                return null;
        }

    }

    boolean existeSerie(String titulo) {
        Serie serie = serieRepo.findByTitulo(titulo);
        if (serie != null)
            return true;
        else
            return false;
    }

    public Serie buscarPorTitulo(String titulo) {
        Serie serie = serieRepo.findByTitulo(titulo);
        return serie;
    }

    public List<Serie> obtenerSerie() {
        return serieRepo.findAll();
    }

    public Serie buscarPorId(ObjectId idSerie) {
        Optional<Serie> opSerie = serieRepo.findById(idSerie);

        if (opSerie.isPresent())
            return opSerie.get();
        else
            return null;
    }

    public boolean tieneGenero(Serie serie, String genero) {
        return serie.getGeneros().stream().filter(g -> g.getNombreGenero().equals(genero)).count() > 0;
    }

    public List<Serie> buscarPorGenero(String genero) {

        return obtenerSerie().stream().filter(p -> this.tieneGenero(p, genero)).collect(Collectors.toList());
        /*
         * return obtenerPeliculas().stream() .filter(p-> p.getGeneros().stream()
         * .filter(g -> g.getNombreGenero().equals(genero)).count() >
         * 0).collect(Collectors.toList());
         */
    }


    public List<Temporada> traerTemporadasPorSerieId(ObjectId id){

        return buscarPorId(id).getTemporadas();
    }

    public Serie modificarPelicula(Serie serie, ModifSerie modifSerie) {

        serie.setTitulo(modifSerie.titulo);
        serie.setGeneros(modifSerie.generos);
        serie.setActores(modifSerie.actores);
        serie.setDirector(modifSerie.director);
        serie.setTemporadas(modifSerie.temporadas);

        boolean serieModificada = this.crearNuevaSerie(serie);
        if (serieModificada) {
            return serie;
        } else {
            return null;
        }

    }

    public Serie calificarSerie(Serie serie, double calificacion) {

        serie.setCalificacion(calificacion);
        boolean serieCalificada = this.crearNuevaSerie(serie);
        if (serieCalificada) {
            return serie;

        } else {
            return null;
        }

    }


    
}
