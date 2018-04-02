abstract class Legemiddel {
    /*
    Legemiddel er superklassen til LegemiddalA, LegemiddelB og LegemiddelC.
    */
    private static int idTeller = 0;
    private int id;
    private String navnLegemiddel;
    private double prisLegemiddel;
    private double virkestoffLegemiddel;

    public Legemiddel(String navn, double pris, double virkestoff) {
        navnLegemiddel = navn;
        prisLegemiddel = pris;
        virkestoffLegemiddel = virkestoff;
        id = idTeller;
        idTeller += 1;
    }

    public int hentId() {
        return id;
    }

    public String hentNavn() {
        return navnLegemiddel;
    }

    public double hentPris() {
        return prisLegemiddel;
    }

    public double hentVirkestoff() {
        return virkestoffLegemiddel;
    }

    public void nyPris(int nyPris) {
        prisLegemiddel = nyPris;
    }

    public String toString() {
        return "Navn paa legemiddel: " + hentNavn() + "\nLegemiddel-ID: " + hentId() + "\nPris: " + hentPris()
                + "\nVirkestoff (mg): " + hentVirkestoff() + "\n";
    }

    public abstract String hentType();
}