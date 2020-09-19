package ar.com.ada.potflix.entities;

import java.util.*;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "series")
public class Serie extends Contenido{
    
    @Field(order = 10)
    private List<Temporada> temporadas = new ArrayList<>();

    public List<Temporada> getTemporadas() {
        return temporadas;
    }

    public void setTemporadas(List<Temporada> temporadas) {
        this.temporadas = temporadas;
    }

    
    
}
