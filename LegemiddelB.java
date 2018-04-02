class LegemiddelB extends Legemiddel {
    /*
    LegemiddelB er subklassen til Legemiddel.
    */
    private int hvorVanedannende;

    public LegemiddelB(String navn, double pris, double virkestoff, int vanedannende) {
        super(navn, pris, virkestoff);
        hvorVanedannende = vanedannende;
    }

    @Override
    public String hentType() {
        return "b";
    }

    public int hentVanedannendeStyrke() {
        return hvorVanedannende;
    }

    @Override
    public String toString() {
        return super.toString() + "Vanedannende: " + hentVanedannendeStyrke() + "\n";
    }
}