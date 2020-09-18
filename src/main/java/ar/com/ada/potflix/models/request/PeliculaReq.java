package ar.com.ada.potflix.models.request;

import java.util.ArrayList;
import java.util.List;

import ar.com.ada.potflix.entities.Actor;
import ar.com.ada.potflix.entities.Director;
import ar.com.ada.potflix.entities.Genero;

public class PeliculaReq {

    public String titulo;
    public List<String> premios = new ArrayList<>();
    public Director director;
    public List<Genero> generos = new ArrayList<>();
    public List<Actor> actores = new ArrayList<>();
    public boolean filmadaEnImax;

}
