package rs.org.amss.model;

public class CameraNew {
    //private int id;
    private String grupa, naziv;
    private String geografska_duzina,geografska_sirina;
    private String opis, tehnologija, url;
    private String ugaoKamere;

    public CameraNew() {
    }

    public CameraNew(String grupa, String naziv, String geografska_duzina, String geografska_sirina, String opis, String tehnologija, String url, String ugaoKamere) {
        this.grupa = grupa;
        this.naziv = naziv;
        this.geografska_duzina = geografska_duzina;
        this.geografska_sirina = geografska_sirina;
        this.opis = opis;
        this.tehnologija = tehnologija;
        this.url = url;
        this.ugaoKamere = ugaoKamere;
    }

    public String getGrupa() {
        return grupa;
    }

    public void setGrupa(String grupa) {
        this.grupa = grupa;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getGeografska_duzina() {
        return geografska_duzina;
    }

    public void setGeografska_duzina(String geografska_duzina) {
        this.geografska_duzina = geografska_duzina;
    }

    public String getGeografska_sirina() {
        return geografska_sirina;
    }

    public void setGeografska_sirina(String geografska_sirina) {
        this.geografska_sirina = geografska_sirina;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getTehnologija() {
        return tehnologija;
    }

    public void setTehnologija(String tehnologija) {
        this.tehnologija = tehnologija;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUgaoKamere() {
        return ugaoKamere;
    }

    public void setUgaoKamere(String ugaoKamere) {
        this.ugaoKamere = ugaoKamere;
    }
}
