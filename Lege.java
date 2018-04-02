class Lege implements Comparable<Lege> {
    /*
    Lege er superklassen til Fastlege.
    */
    private String navnLege;
    private Lenkeliste<Resept> utskrevedeResepter;

    public Lege(String navn) {
        navnLege = navn;
        utskrevedeResepter = new Lenkeliste<Resept>();
    }

    public String hentNavn() {
        return navnLege;
    }

    public String toString() {
        return "Navn paa lege: " + hentNavn() + "\n";
    }

    public void leggTilResept(Resept resept) {
        utskrevedeResepter.leggTil(resept);
    }

    public Lenkeliste<Resept> hentUtAlleResepter() {
        return utskrevedeResepter;
    }
    
    @Override
    public int compareTo(Lege other) {
        return this.hentNavn().compareTo(other.hentNavn());
    }
}