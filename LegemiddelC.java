class LegemiddelC extends Legemiddel {
    /*
    LegemiddelC er subklassen til Legemiddel, men den er helt lik så jeg ser ikke helt hvorfor
    den finnes.
    */
    public LegemiddelC(String navn, double pris, double virkestoff) {
        super(navn, pris, virkestoff);
    }

    @Override
    public String hentType() {
        return "c";
    }

}
