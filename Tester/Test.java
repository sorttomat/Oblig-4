class Test {
    public static void test(Object one, Object two) {
        /*
        Denne metoden returnerer resultatet av aa sjekke om et objekt er likt et annet objekt.
        Paa denne maaten kan jeg legge inn det jeg mener skal vaere riktig som parameter,
        og faar vite om det faktisk stemmer.
        */
        if (one.equals(two)) {
            System.out.println("Riktig");
        } else {
            System.out.println("Galt");
        }
    }
}