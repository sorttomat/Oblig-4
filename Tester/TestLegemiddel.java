class TestLegemiddel extends Test {
    public static void main(String[] args) {
        /*
        Her blir alle legemidlene testet for alle parametrene, samt ID.
        */
        LegemiddelA legemiddelA = new LegemiddelA("Ibux", 150.0, 200.0, 20);
        LegemiddelB legemiddelB = new LegemiddelB("Paracet", 250.0, 50.0, 31);
        LegemiddelC legemiddelC = new LegemiddelC("Nesespray", 15.50, 20.0);

        test("Ibux", legemiddelA.hentNavn());
        test(150.0, legemiddelA.hentPris());
        test(200.0, legemiddelA.hentVirkestoff());
        test(20, legemiddelA.hentNarkotiskStyrke());
        test(0, legemiddelA.hentId());

        test("Paracet", legemiddelB.hentNavn());
        test(250.0, legemiddelB.hentPris());
        test(50.0, legemiddelB.hentVirkestoff());
        test(31, legemiddelB.hentVanedannendeStyrke());
        test(1, legemiddelB.hentId());

        test("Nesespray", legemiddelC.hentNavn());
        test(15.50, legemiddelC.hentPris());
        test(20.0, legemiddelC.hentVirkestoff());
        test(2, legemiddelC.hentId());

        System.out.println();
        System.out.println(legemiddelA);
        System.out.println(legemiddelB);
        System.out.println(legemiddelC);

    }
}