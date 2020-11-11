package ehu.isad.eurobisioa;

import javafx.scene.image.Image;

public class Herrialdea {

    private Image bandera;
    private String herrialdea;
    private String artista;
    private String abestia;
    private int puntuak;

    public Herrialdea(Image bandera, String herrialdea, String artista, String abestia, int puntuak) {
        this.bandera = bandera;
        this.herrialdea = herrialdea;
        this.artista = artista;
        this.abestia = abestia;
        this.puntuak = puntuak;
    }
    public Image getBandera() {
        return bandera;
    }
    public String getHerrialdea() {
        return herrialdea;
    }
    public int getPuntuak() {
        return puntuak;
    }
    public void setPuntuak(int puntuak) {
        this.puntuak = puntuak;
    }
}
