package ar.com.ada.potflix.entities;


import java.util.*;

public class Genero {

    private String nombreGenero;

    private List<Pelicula> peliculas = new ArrayList<>();
    private List<Serie> series = new ArrayList<>();

    public String getNombreGenero() {
        return nombreGenero;
    }

    public void setNombreGenero(String nombreGenero) {
        this.nombreGenero = nombreGenero;
    }

    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public List<Serie> getSeries() {
        return series;
    }

    public void setSeries(List<Serie> series) {
        this.series = series;
    }
    

    
}
