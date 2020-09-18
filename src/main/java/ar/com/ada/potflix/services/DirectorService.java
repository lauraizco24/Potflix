package ar.com.ada.potflix.services;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.potflix.entities.Director;
import ar.com.ada.potflix.repos.DirectorRepository;

@Service
public class DirectorService {

    @Autowired
    DirectorRepository directorRepo;
    
    public boolean crearNuevoDirector(Director director){
        directorRepo.save(director);
        return true;
    }

    public Director crearDirector(String nombre, String estilo) {
        if (existeDirector(nombre)) {
            return null;
        } else {
            Director director = new Director();
            director.setEstilo(estilo);
            director.setNombre(nombre);
            
            boolean directorCreado = crearNuevoDirector(director);
            if (directorCreado)
                return director;
            else
                return null;
        }

    }

    boolean existeDirector(String nombre) {
        Director director = directorRepo.findByNombre(nombre);
        if (director != null)
            return true;
        else
            return false;
    }

    public List<Director> obtenerDirectores(){
        return directorRepo.findAll();
    }

    public Director buscarPorId(ObjectId idDirector) {
        Optional<Director> opDirector = directorRepo.findById(idDirector);

        if (opDirector.isPresent())
            return opDirector.get();
        else
            return null;
    }
}
