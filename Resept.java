abstract class Resept {
    /*
    Resept er superklassen til ReseptHvit og ReseptBlaa.
    */
    private Legemiddel legemiddel;
    private Lege utskrivendeLege;
    private Pasient pasient;
    private int reit;
    private static int idTeller = 0;
    private int id;

    public Resept(Legemiddel legem, Lege utskrLege, Pasient pas, int rt) {
        legemiddel = legem;
        utskrivendeLege = utskrLege;
        pasient = pas;
        reit = rt;
        id = idTeller;
        idTeller += 1;
    }

    public int hentPasientId() {
        return pasient.hentPasientId();
    }

    public int hentReit() {
        return reit;
    }

    public int hentId() {
        return id;
    }

    public Legemiddel hentLegemiddel() {
        return legemiddel;
    }

    public Lege hentLege() {
        return utskrivendeLege;
    }

    public boolean bruk() {
        if (reit > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public void brukResept() {
        reit --;
    }

    public String toString() {
        return "Resept-ID: " + id + "\n" + utskrivendeLege.toString() + 
        legemiddel.toString() + "Pasient-ID: " + hentPasientId() + "\n" + "Reit: " + hentReit() + "\n";
    }

    public abstract String farge();

    public abstract  double prisAaBetale();




}