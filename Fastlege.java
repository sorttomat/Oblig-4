class Fastlege extends Lege implements Kommuneavtale {
    /*
    Fastlege er en subklasse av Lege, som også implementerer interfacet Kommuneavtale.
    */
    private int avtalenummer;

    public Fastlege(String navn, int avtalenr) {
        super(navn);
        avtalenummer = avtalenr;
    }

    public int hentAvtalenummer() {
        return avtalenummer;
    }

    public String toString() {
        return super.toString() + "Avtalenummer: " + hentAvtalenummer() + "\n";
    }
}