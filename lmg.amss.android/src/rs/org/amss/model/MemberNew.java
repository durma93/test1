package rs.org.amss.model;

public class MemberNew {

    private String datumOd,
            datumDo,
            ime,
            prezime,
            registracija,
            serija,
            clanskiBroj;

    public MemberNew() {
    }

    public MemberNew(String datumOd, String datumDo, String ime, String prezime, String registracija, String serija, String clanskiBroj) {
        this.datumOd = datumOd;
        this.datumDo = datumDo;
        this.ime = ime;
        this.prezime = prezime;
        this.registracija = registracija;
        this.serija = serija;
        this.clanskiBroj = clanskiBroj;
    }

    public String getDatumOd() {
        return datumOd;
    }

    public void setDatumOd(String datumOd) {
        this.datumOd = datumOd;
    }

    public String getDatumDo() {
        return datumDo;
    }

    public void setDatumDo(String datumDo) {
        this.datumDo = datumDo;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getRegistracija() {
        return registracija;
    }

    public void setRegistracija(String registracija) {
        this.registracija = registracija;
    }

    public String getSerija() {
        return serija;
    }

    public void setSerija(String serija) {
        this.serija = serija;
    }

    public String getClanskiBroj() {
        return clanskiBroj;
    }

    public void setClanskiBroj(String clanskiBroj) {
        this.clanskiBroj = clanskiBroj;
    }
}
