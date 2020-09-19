package ar.com.ada.potflix.entities;

import java.util.ArrayList;
import java.util.*;

public class Temporada {
    
    private List<Episodio> episodios = new ArrayList<>();
    private List<Websodio> websodios = new ArrayList<>();
    private int numero;

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        this.episodios = episodios;
    }

    public List<Websodio> getWebsodios() {
        return websodios;
    }

    public void setWebsodios(List<Websodio> websodios) {
        this.websodios = websodios;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    
}
