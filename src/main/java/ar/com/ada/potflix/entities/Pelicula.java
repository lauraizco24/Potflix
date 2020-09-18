package ar.com.ada.potflix.entities;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "peliculas")
public class Pelicula extends Contenido{
    
    private ObjectId _id;
    private boolean filmadaEnImax;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public boolean isFilmadaEnImax() {
        return filmadaEnImax;
    }

    public void setFilmadaEnImax(boolean filmadaEnImax) {
        this.filmadaEnImax = filmadaEnImax;
    }
    

}
