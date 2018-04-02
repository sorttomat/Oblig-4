class Stabel<T> extends Lenkeliste<T> {
    public void leggPaa(T x) {
        //Legger element til i slutten av listen.
        super.leggTil(x);
    }

    public T hentUtSiste() {
        //Henter ut siste element uten aa fjerne det.
        return super.hent(super.stoerrelse()-1);
    }

    public T taAv() {
        //Fjerner og returnerer det siste elementet i listen.
        return super.fjern(super.stoerrelse()-1);
    }
}