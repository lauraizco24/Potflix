package ar.com.ada.potflix.models.request;

import java.util.*;

import ar.com.ada.potflix.entities.*;

public class ModifPelicula {

    public String titulo;
    public List<String> premios = new ArrayList<>();
    public Director director;
    public List<Genero> generos = new ArrayList<>();
    public List<Actor> actores = new ArrayList<>();
    public boolean filmadaEnImax;
    
}
