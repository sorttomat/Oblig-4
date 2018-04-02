class PResept extends ReseptHvit {
    /*
    PResept er subklassen til reseptHvit, som igjen er subklassen til Resept.
    */
    protected static int avslag;

    public PResept(Legemiddel legem, Lege utskrLege, Pasient pas) {
        super(legem, utskrLege, pas, 3);
        avslag = 116;
    }

    private double regnUtAvslag(double pris) {
        if (pris > avslag) {
            return pris - avslag;
        }
        return 0.0;
    }

    public double prisAaBetale() {
        double fullPris = super.hentLegemiddel().hentPris();
        return regnUtAvslag(fullPris);
    }

    public String toString() {
        return super.toString();
    }

}