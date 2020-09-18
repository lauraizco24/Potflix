package ar.com.ada.potflix.models.request;

import java.util.ArrayList;

import ar.com.ada.potflix.entities.*;

import java.util.*;


public class ModifSerie {

    public String titulo;
    public List<String> premios = new ArrayList<>();
    public Director director;
    public List<Genero> generos = new ArrayList<>();
    public List<Actor> actores = new ArrayList<>();
    public List<Temporada> temporadas = new ArrayList<>();
    
}
