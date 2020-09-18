package ar.com.ada.potflix.services;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.potflix.entities.Genero;
import ar.com.ada.potflix.repos.PeliculaRepository;
import ar.com.ada.potflix.repos.SerieRepository;

@Service
public class GeneroService {
    
    @Autowired
    PeliculaRepository pRepo;
    @Autowired
    SerieRepository sRepo;
    @Autowired
    PeliculaService pS;

    public List<Genero> obtenerGeneros(){
        return pS.obtenerPeliculas().stream().map(p ->p.getGeneros()).flatMap(List::stream)
        .collect(Collectors.toList());
    }

}
