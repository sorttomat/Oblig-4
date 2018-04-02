class TestResepter extends Test {
    public static void main(String[] args) {
        /*
        Dette programmet tester alle resept-typene.
        */
        LegemiddelA legemiddelA = new LegemiddelA("Ibux", 150.0, 200.0, 20);
        LegemiddelB legemiddelB = new LegemiddelB("pPiller", 250.0, 50.0, 31);
        LegemiddelC legemiddelC = new LegemiddelC("Nesespray", 15.50, 20.0);

        Lege lege = new Lege("Henrik");
        Fastlege fastlege = new Fastlege("Bernt", 123);
        Lege lege2 = new Lege("Lisa");

        ReseptBlaa reseptBlaa = new ReseptBlaa(legemiddelA, lege, 0, 5);
        PResept pResept = new PResept(legemiddelB, fastlege, 1);
        MilitaerResept mResept = new MilitaerResept(legemiddelC, lege2, 2, 3);

        p("resept blå");
        test("Ibux", reseptBlaa.hentLegemiddel().hentNavn());
        test("Henrik", reseptBlaa.hentLege().hentNavn());
        test(37.5, reseptBlaa.prisAaBetale());
        test("blaa", reseptBlaa.farge());
        p("");

        p("P-resept");
        test(134.0, pResept.prisAaBetale());
        test("Bernt", pResept.hentLege().hentNavn());
        test(1, pResept.hentId());
        p("");

        p("militaer-resept");
        test(0.0, mResept.prisAaBetale());
        test(2, mResept.hentLegemiddel().hentId());

        p("");
        System.out.println(reseptBlaa);
        System.out.println(pResept);
        System.out.println(mResept);
    }

    public static void p(String tekst) {
        System.out.println(tekst);
    }
}
