package ar.com.ada.potflix.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import ar.com.ada.potflix.entities.Actor;
import ar.com.ada.potflix.entities.Director;
import ar.com.ada.potflix.entities.Episodio;
import ar.com.ada.potflix.entities.Genero;
import ar.com.ada.potflix.entities.Serie;
import ar.com.ada.potflix.entities.Temporada;
import ar.com.ada.potflix.models.request.ModifSerie;
import ar.com.ada.potflix.repos.SerieRepository;

@Service
public class SerieService {

    @Autowired
    SerieRepository serieRepo;

    @Autowired
    MongoTemplate mongoTemplate;

    public boolean crearNuevaSerie(Serie serie) {
        serieRepo.save(serie);
        return true;
    }

    public Serie crearSerie(String titulo, List<Genero> generos, Director director, List<Actor> actores,
            List<Temporada> temporadas) {
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

    public List<Temporada> traerTemporadasPorSerieId(ObjectId id) {

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

    public List<Serie> obtenerSeriesByActor(ObjectId actorId) {

        //Trae todas las Series.
        /*List<Serie> series = serieRepo.findAll().stream()
            .filter(s -> s.getActores().stream()
            .filter(a-> a.get_id().equals(actorId.toHexString())).count() > 0)
            .collect(Collectors.toList());*/
        
        //Busco por un metodo del repo
        //List<Serie> series = serieRepo.findByActores__id(actorId);

        //En este caso trae solo info del Actor.
        List<Serie> series = serieRepo.findSeriesByActores_IdSoloInfoActor(actorId);
        //List<Serie> series = serieRepo.findSeriesByActor_IdEntero(actorId);
        return series;
    }

    public List<Episodio> obtenerEpisodiosSerie(ObjectId serieId) {

        return this.buscarPorId(serieId).getTemporadas().stream().map(t -> t.getEpisodios()).flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public Episodio obtenerEpisodioPorNroEpisodio(ObjectId serieId, Integer temporadaNro, Integer episodioNumero) {

        return obtenerEpisodioPorNroEpisodioVersionPerfomante(serieId, temporadaNro, episodioNumero);
    }

    public Episodio obtenerEpisodioPorNroEpisodioVersionPesada(ObjectId serieId, Integer temporadaNro,
            Integer episodioNumero) {

        Episodio episodio = serieRepo.findBy_id(serieId).getTemporadas().stream()
                .filter(t -> t.getNumero() == temporadaNro).findFirst().get()
                .getEpisodios().stream()
                .filter(e -> e.getNumero() == episodioNumero).findFirst().get();

        return episodio;
    }

    public Episodio obtenerEpisodioPorNroEpisodioVersionPerfomante(ObjectId serieId, Integer temporadaNro,
            Integer episodioNumero) {

        /*
         * 
         * Este es el PIPELINE EN JS
        [
            {
            '$match': {
                '_id': new ObjectId('5f652e4f857bbf55ecbccfe6')
                }
            },
            {
            '$project': {
                'temporadas': 1
                }
            },
            {
                '$unwind': {
                    'path': '$temporadas'
                }
            },
            {
            '$match': {
                'temporadas.numero': 5
                }
            },
            {
            '$replaceRoot': {
                'newRoot': '$temporadas'
                }
            }, 
            {
            '$unwind': {
                'path': '$episodios'
                }
            }, 
            {
            '$replaceRoot': {
                'newRoot': '$episodios'
                }
            }, 
            {
            '$match': {
                'numero': 7
                }
            }
        ]
         * 
         * 
         */

        // Pipelines
        // Stage1: filtro por la serie especifica
        MatchOperation matchSerieStage = Aggregation.match(new Criteria("_id").is(serieId));

        // Stage2: Proyectar solo temporadas
        ProjectionOperation projectTemporadaStage = Aggregation.project("temporadas");

        // Stage3: Unwind temporada (las separa)
        UnwindOperation unwindTemporadaStage = Aggregation.unwind("temporadas");

        // Stage4: Match temporada(filtra de las separadas)
        MatchOperation matchTemporadaStage = Aggregation.match(new Criteria("temporadas.numero").is(temporadaNro));

        // Stage5: Reemplaza la raiz para que tome ahora el de documentos
        ReplaceRootOperation replaceRootTemporadaStage = Aggregation.replaceRoot("temporadas");

        // Stage6: Unwind episodios (separa los episodios)
        UnwindOperation unwindEpisodiosStage = Aggregation.unwind("episodios");

        // Stage7: Reemplaza la raiz para que tome ahora el de documentos
        ReplaceRootOperation replaceRootEpisodiosStage = Aggregation.replaceRoot("episodios");
        // Stage8: Match episodio(filtra de los separados)
        MatchOperation matchEpisodioStage = Aggregation.match(new Criteria("numero").is(episodioNumero));

        Aggregation aggregation = Aggregation.newAggregation(matchSerieStage, projectTemporadaStage,
                unwindTemporadaStage, matchTemporadaStage, replaceRootTemporadaStage, unwindEpisodiosStage,
                replaceRootEpisodiosStage, matchEpisodioStage);

        AggregationResults<Episodio> result = mongoTemplate.aggregate(aggregation, "series", Episodio.class);

        Episodio episodio = result.getUniqueMappedResult();
        return episodio;

    }

}
