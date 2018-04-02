class Hovedprogram {
    public static void main(String[] args) {
        Lege hege = new Lege("Dr. Hege");
        Lege adam = new Lege("Dr. Adam");
        Lege aage = new Lege("Dr. Aage");
        Lege ferdinand = new Lege("Dr. Ferdinand");
        Lege åse = new Lege("Dr. Åse");
        Lege paus = new Lege("Dr. Paus");
        Lege ueland = new Lege("Dr. Ueland");
        SortertLenkeliste<Lege> listeLeger = new SortertLenkeliste<Lege>();
        listeLeger.leggTil(hege);
        listeLeger.leggTil(åse);
        listeLeger.leggTil(ferdinand);
        listeLeger.leggTil(adam);
        listeLeger.leggTil(aage);
        listeLeger.leggTil(ueland);
        listeLeger.leggTil(paus);


        Legesystem legesystem = new Legesystem();
        legesystem.legeprogram();
    }
}