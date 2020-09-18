package ar.com.ada.potflix.entities;

import java.util.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "series")
public class Serie extends Contenido{
    
    private ObjectId _id;
    private List<Temporada> temporadas = new ArrayList<>();

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public List<Temporada> getTemporadas() {
        return temporadas;
    }

    public void setTemporadas(List<Temporada> temporadas) {
        this.temporadas = temporadas;
    }

    
    
}
