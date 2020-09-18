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
import ar.com.ada.potflix.entities.Pelicula;
import ar.com.ada.potflix.models.request.ModifPelicula;
import ar.com.ada.potflix.repos.PeliculaRepository;

@Service
public class PeliculaService {

    @Autowired
    PeliculaRepository peliRepo;

    public boolean crearNuevaPelicula(Pelicula pelicula) {
        peliRepo.save(pelicula);
        return true;
    }

    public Pelicula crearPelicula(String titulo, List<Genero> generos, Director director, List<Actor> actores,
            boolean filmadaEnImax) {
        if (existePelicula(titulo)) {
            return null;
        } else {
            Pelicula pelicula = new Pelicula();
            pelicula.setTitulo(titulo);
            pelicula.setGeneros(generos);
            pelicula.setDirector(director);
            pelicula.setActores(actores);
            pelicula.setFilmadaEnImax(filmadaEnImax);

            boolean peliculaCreada = crearNuevaPelicula(pelicula);
            if (peliculaCreada)
                return pelicula;
            else
                return null;
        }

    }

    boolean existePelicula(String titulo) {
        Pelicula pelicula = peliRepo.findByTitulo(titulo);
        if (pelicula != null)
            return true;
        else
            return false;
    }

    public Pelicula buscarPorTitulo(String titulo) {
        Pelicula pelicula = peliRepo.findByTitulo(titulo);
        return pelicula;
    }

    public List<Pelicula> obtenerPeliculas() {
        return peliRepo.findAll();
    }

    public Pelicula buscarPorId(ObjectId idPelicula) {
        Optional<Pelicula> opPelicula = peliRepo.findById(idPelicula);

        if (opPelicula.isPresent())
            return opPelicula.get();
        else
            return null;
    }

    public boolean tieneGenero(Pelicula pelicula, String genero) {
        return pelicula.getGeneros().stream().filter(g -> g.getNombreGenero().equals(genero)).count() > 0;
    }

    public List<Pelicula> buscarPorGenero(String genero) {

        return obtenerPeliculas().stream().filter(p -> this.tieneGenero(p, genero)).collect(Collectors.toList());
        /*
         * return obtenerPeliculas().stream() .filter(p-> p.getGeneros().stream()
         * .filter(g -> g.getNombreGenero().equals(genero)).count() >
         * 0).collect(Collectors.toList());
         */
    }

    public Pelicula modificarPelicula(Pelicula pelicula, ModifPelicula modifPelicula) {

        pelicula.setTitulo(modifPelicula.titulo);
        pelicula.setGeneros(modifPelicula.generos);
        pelicula.setActores(modifPelicula.actores);
        pelicula.setDirector(modifPelicula.director);
        pelicula.setFilmadaEnImax(modifPelicula.filmadaEnImax);

        boolean peliculaModificada = this.crearNuevaPelicula(pelicula);
        if (peliculaModificada) {
            return pelicula;
        } else {
            return null;
        }

    }

    public Pelicula calificarPelicula(Pelicula peli, double calificacion) {

        peli.setCalificacion(calificacion);
        boolean peliCalificada = this.crearNuevaPelicula(peli);
        if (peliCalificada) {
            return peli;

        } else {
            return null;
        }

    }

}

// Profe lo estoy viendo, pero hay algo que no entiendo, por que el count?

// El filter, filtra datos, es como filtrar filas en EXCEL, entonces de todo lo
// filtrado
// el count es la cantidad, si hay datos, es porque la cantidad es maayor a 0
// en el tiene genero se arma ese metodo chiquito
// ahora en buscar por genero, en vez de hacer 2 filters y terminar con muchos
// parentesis
// se resuelve asi: se aplica el filter a las pelis, pero con la funcion
// "tieneGenero"(que es otro filter)
// y finalmente se transforma a lista

// osea que al yo colocar filter, aunque no le diga que lo convierta en lista,
// igual tengo una lista de los generos?

// nop, en realidad es un STREAM de generos, tecnicamente ese es el concepto, lo
// podes pensar como una lista
// pero stream se refiere a un flujo de GENEROS en este caso, como si fuese el
// cause de un rio
// y algunos metodos de lista, como count() te sirven

// ok, entendido!

// no me borre los coments jeje

// voy a probar
