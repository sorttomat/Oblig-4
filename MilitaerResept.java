class MilitaerResept extends ReseptHvit {
    /*
    MilitaerResept er subklassen til ReseptHvit, som igjen er subklassen til Resept.
    */

    public MilitaerResept(Legemiddel legem, Lege utskrLege, Pasient pas, int rt) {
        super(legem, utskrLege, pas, rt);
    }

    public double prisAaBetale() {
        return 0.0;
    }

    public String toString() {
        return super.toString();
    }
}