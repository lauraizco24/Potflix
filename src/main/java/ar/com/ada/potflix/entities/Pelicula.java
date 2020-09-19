package ar.com.ada.potflix.entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "peliculas")
public class Pelicula extends Contenido{
    
    private boolean filmadaEnImax;

    public boolean isFilmadaEnImax() {
        return filmadaEnImax;
    }

    public void setFilmadaEnImax(boolean filmadaEnImax) {
        this.filmadaEnImax = filmadaEnImax;
    }
    

}
