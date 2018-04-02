class Pasient {
    private Stabel<Resept> resepter;
    private String navn;
    private static int idTeller = 0;
    private int pasientId;

    public Pasient(String name) {
        navn = name;
        resepter = new Stabel<Resept>();
        pasientId = idTeller;
        idTeller++;
    }

    public String hentNavn() {
        return navn;
    }

    public int hentPasientId() {
        return pasientId;
    }

    public Resept hentSisteResept() {
        return resepter.hentUtSiste();
    }

    public Stabel<Resept> hentAlleResepter() {
        return resepter;
    }

    public void leggTilResept(Resept resept) {
        resepter.leggPaa(resept);
    }

    @Override
    public String toString() {
        return "Navn: " + this.hentNavn() + "\nID: " + this.hentPasientId() + "\n";
    }
}