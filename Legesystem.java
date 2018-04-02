import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.InputMismatchException;
import java.io.IOException;
import java.io.Writer;
import java.io.FileWriter;
import java.util.Map;
import java.util.HashMap;
import java.io.File;
import java.util.Scanner;

class Legesystem {
	SortertLenkeliste<Lege> leger = new SortertLenkeliste<Lege>();
	Lenkeliste<Resept> resepter = new Lenkeliste<Resept>();
	Lenkeliste<Pasient> pasienter = new Lenkeliste<Pasient>();
	Lenkeliste<Legemiddel> legemidler = new Lenkeliste<Legemiddel>();

	public void legeprogram() {
		try {
			int valg = spoerOmInt("Velkommen!\n1. Les inn data fra fil.\n2. Gå til hovedmeny.\n");
			switch (valg) {
			case 1:
				String fileName = spoerOmString("Oppgi filnavn: \n");
				lesInnFraFil(fileName);
				hovedmeny();
				break;
			case 2:
				hovedmeny();
				break;
			default:
				System.out.println("Det skjønte jeg ikke, så jeg tar deg med til hovedmenyen.");
				hovedmeny();
				break;
			}
		} catch (InputMismatchException mis) {
			System.out.println("Feil i input!");
			hovedmeny();
		}
	}

	private void lesInnFraFil(String fileName) {
		try {
			File infile = new File(fileName);
			Scanner in = new Scanner(infile);
			String mode = null;

			while (in.hasNextLine()) {
				String heleLinjen = in.nextLine();
				String[] line = heleLinjen.replaceAll(" ", "").split(",");
				if (heleLinjen.startsWith("#")) {
					String kategori = heleLinjen.split(" ")[1];
					mode = kategori;
					continue;
				}
				switch (mode) {
				case "Pasienter":
					opprettPasientFraFil(line);
					break;

				case "Legemidler":
					opprettLegemiddelFraFil(line);
					break;

				case "Leger":
					opprettLegeFraFil(line);
					break;

				case "Resepter":
					opprettReseptFraFil(line);
					break;
				}
			}
			in.close();

		} catch (FileNotFoundException ex) {
			//lesInnFraFil(spoerOmString("Fant ikke filen. Vennligst oppgi filnavn: "));
		} catch (Exception x) {
			System.out.println("Some exception ocurred");
		}
	}

	private void opprettPasientFraFil(String[] line) {
		String navnPasient = line[0];
		pasienter.leggTil(new Pasient(navnPasient));
	}

	private void opprettLegemiddelFraFil(String[] line) {
		String navnLegemiddel = line[0];
		String type = line[1];
		double pris = Double.parseDouble(line[2]);
		double virkestoff = Double.parseDouble(line[3]);
		Legemiddel legemiddel = null;
		switch (type) {
		case "a":
			int styrke = Integer.parseInt(line[4]);
			legemiddel = new LegemiddelA(navnLegemiddel, pris, virkestoff, styrke);
			break;
		case "b":
			int vanedannende = Integer.parseInt(line[4]);
			legemiddel = new LegemiddelB(navnLegemiddel, pris, virkestoff, vanedannende);
			break;
		case "c":
			legemiddel = new LegemiddelC(navnLegemiddel, pris, virkestoff);
			break;
		}
		legemidler.leggTil(legemiddel);
	}

	private void opprettLegeFraFil(String[] line) {
		String navnLege = line[0];
		int avtalenummer = Integer.parseInt(line[1]);
		Lege lege = null;
		switch (avtalenummer) {
		case 0:
			lege = new Lege(navnLege);
			break;
		default:
			lege = new Fastlege(navnLege, avtalenummer);
			break;
		}
		leger.leggTil(lege);
	}

	private void opprettReseptFraFil(String[] line) {
		String typeResept = line[0];
		int legemiddelNummer = Integer.parseInt(line[1]);
		String legeNavn = line[2];
		int pasientId = Integer.parseInt(line[3]);
		int reit;
		Lege lege = finnLege(legeNavn);
		Pasient pasient = finnPasient(pasientId);
		Legemiddel legemiddel = finnLegemiddel(pasientId);
		Resept resept = null;

		switch (typeResept) {
		case "blaa":
			reit = Integer.parseInt(line[4]);
			resept = new ReseptBlaa(legemiddel, lege, pasient, reit);
			break;

		case "hvit":
			reit = Integer.parseInt(line[4]);
			resept = new ReseptHvit(legemiddel, lege, pasient, reit);
			break;

		case "prevensjon":
			resept = new PResept(legemiddel, lege, pasient);
			break;

		case "militaer":
			reit = Integer.parseInt(line[4]);
			resept = new MilitaerResept(legemiddel, lege, pasient, reit);
			break;
		}
		resepter.leggTil(resept);
		pasient.leggTilResept(resept);
		lege.leggTilResept(resept);
	}

	private void hovedmeny() {
		int valg;
		do {
			valg = spoerOmInt(skrivHovedmeny());
			while (valg == 0) {
				valg = spoerOmInt("Ugyldig valg. Vennligst tast inn et tall mellom 1 og 6: ");
			}

			switch (valg) {
			case 1:
				System.out.println(this);
				break;

			case 2:
				opprettElement();
				break;

			case 3:
				brukResept();
				break;

			case 4:
				skrivUtStatistikk();
				break;

			case 5:
				skrivTilFil("tilFil.txt");
				break;
			}
		} while (valg != 6);
	}

	private void opprettElement() {
		int valgElement = spoerOmInt(leggTilElementMeny());
		while (valgElement == 0) {
			System.out.println();
			valgElement = spoerOmInt("Ugyldig valg. Vennligst prøv igjen: ");
		}

		switch (valgElement) {
		case 1:
			leggTilLege();
			break;

		case 2:
			leggTilPasient();
			break;

		case 3:
			leggTilResept();
			break;

		case 4:
			leggTilLegemiddel();
			break;
		}
	}

	private void leggTilLege() {
		String navnLege = spoerOmString("Vennligst oppgi navn: ");
		while (finnLege(navnLege) != null) {
			if (spoerOmString("Denne legen finnes allerede. Vil du oppgi en ny lege? j/n. ").equals("j")) {
				navnLege = spoerOmString("Vennligst oppgi navn: ");
			} else {
				return;
			}
		}

		int valg = spoerOmInt("Hvis fastlege, oppgi avtalenummer. Ellers tast 1: ");
		while (valg == 0) {
			valg = spoerOmInt("Ugyldig valg. Vennligst tast inn et tall: ");
		}

		Lege lege;
		if (valg != 1) {
			lege = new Fastlege(navnLege, valg);
		} else {
			lege = new Lege(navnLege);
		}

		leger.leggTil(lege);
		System.out.println("Lege lagt til! \n");
	}

	private void leggTilPasient() {
		String navnPasient = spoerOmString("Vennligst oppgi navn: ");
		while (finnPasient(navnPasient) != null) {
			if (spoerOmString("Denne pasienten finnes allerede. Vil du oppgi en ny pasient? j/n. ").equals("j")) {
				navnPasient = spoerOmString("Vennligst oppgi navn: ");
			} else {
				return;
			}
		}

		Pasient pasient = new Pasient(navnPasient);
		pasienter.leggTil(pasient);
		System.out.println("Pasient lagt til! \n");
	}

	private void leggTilResept() {
		try {
			if (leger.stoerrelse() == 0 || pasienter.stoerrelse() == 0 || legemidler.stoerrelse() == 0) {
				System.out.println("Mangler informasjon i systemet for å kunne legge til resept.");
				return;
			}

			printLeger();
			String legeNavn = spoerOmString("Oppgi legens navn: ");
			while (finnLege(legeNavn) == null) {
				legeNavn = spoerOmString("Fant ikke legen, oppgi legens navn: ");
			}

			printPasienter();
			String pasientNavn = spoerOmString("Oppgi pasientens navn: ");
			while (finnPasient(pasientNavn) == null) {
				pasientNavn = spoerOmString("Fant ikke pasienten, oppgi pasientens navn: ");
			}

			printLegemidler();
			String legemiddelNavn = spoerOmString("Oppgi legemiddel: ");
			while (finnLegemiddel(legemiddelNavn) == null) {
				legemiddelNavn = spoerOmString("Fant ikke legemiddelet, oppgi legemiddel: ");
			}

			int typeResept = spoerOmInt("Oppgi type resept (1: Hvit, 2: Blå. 3: P-Resept. 4: Militærresept.): ");
			while (typeResept != 1 && typeResept != 2 && typeResept != 3 && typeResept != 4) {
				typeResept = spoerOmInt(
						"Dette valget finnes ikke. Oppgi type resept (1: Hvit, 2: Blå. 3: P-Resept. 4: Militærresept.): ");
			}

			int reit;
			Lege lege = finnLege(legeNavn);
			Pasient pasient = finnPasient(pasientNavn);
			Legemiddel legemiddel = finnLegemiddel(legemiddelNavn);
			Resept nyResept = null;

			switch (typeResept) {
			case 1:
				reit = spoerOmInt("Vennligst oppgi antall reit: ");
				nyResept = opprettHvitResept(legemiddel, lege, pasient, reit);
				break;

			case 2:
				reit = spoerOmInt("Vennligst oppgi antall reit: ");
				nyResept = opprettBlaaResept(legemiddel, lege, pasient, reit);
				break;

			case 3:
				nyResept = opprettPResept(legemiddel, lege, pasient);
				break;

			case 4:
				reit = spoerOmInt("Vennligst oppgi antall reit: ");
				nyResept = opprettMilitaerResept(legemiddel, lege, pasient, reit);
				break;
			}
			lege.leggTilResept(nyResept);
			pasient.leggTilResept(nyResept);
			resepter.leggTil(nyResept);
			System.out.println("Resept lagt til! \n");

		} catch (Exception ex) {
			System.out.println("Noe gikk galt i leggTilResept.");
			return;
		}
	}

	private PResept opprettPResept(Legemiddel legemiddel, Lege lege, Pasient pasient) { //opprett og legg til
		return new PResept(legemiddel, lege, pasient);
	}

	private MilitaerResept opprettMilitaerResept(Legemiddel legemiddel, Lege lege, Pasient pasient, int reit) {
		return new MilitaerResept(legemiddel, lege, pasient, reit);
	}

	private ReseptHvit opprettHvitResept(Legemiddel legemiddel, Lege lege, Pasient pasient, int reit) {
		return new ReseptHvit(legemiddel, lege, pasient, reit);
	}

	private ReseptBlaa opprettBlaaResept(Legemiddel legemiddel, Lege lege, Pasient pasient, int reit) {
		return new ReseptBlaa(legemiddel, lege, pasient, reit);
	}

	private void leggTilLegemiddel() {
		try {
			String navn = spoerOmString("Oppgi navn: ");
			double pris = spoerOmDouble("Oppgi pris: ");
			double virkestoff = spoerOmDouble("Oppgi virkestoff: ");
			Legemiddel nyttLegemiddel = null;

			int valg = spoerOmInt(legemiddelMeny());
			while (valg == 0) {
				valg = spoerOmInt("Ugyldig valg. Vennligst tast inn et tall mellom 1 og 3: ");
			}

			switch (valg) {
			case 1:
				int styrke = spoerOmInt("Oppgi styrke: ");
				nyttLegemiddel = opprettLegemiddelA(navn, pris, virkestoff, styrke);
				break;

			case 2:
				int vanedannende = spoerOmInt("Oppgi vanedannende: ");
				nyttLegemiddel = opprettLegemiddelB(navn, pris, virkestoff, vanedannende);
				break;

			case 3:
				nyttLegemiddel = opprettLegemiddelC(navn, pris, virkestoff);
				break;

			default:
				System.out.println("Ikke godkjent valg.");
				leggTilLegemiddel();
				break;
			}

			legemidler.leggTil(nyttLegemiddel);
			System.out.println("Legemiddel lagt til!");

		} catch (InputMismatchException ex) {
			System.out.println("Noe ble feil i leggTilLegemiddel, mest sannsynlig noe med en int eller double.");
			leggTilLegemiddel();
		}
	}

	private LegemiddelA opprettLegemiddelA(String navn, double pris, double virkestoff, int styrke) {
		return new LegemiddelA(navn, pris, virkestoff, styrke);
	}

	private LegemiddelB opprettLegemiddelB(String navn, double pris, double virkestoff, int vanedannende) {
		return new LegemiddelB(navn, pris, virkestoff, vanedannende);
	}

	private LegemiddelC opprettLegemiddelC(String navn, double pris, double virkestoff) {
		return new LegemiddelC(navn, pris, virkestoff);
	}

	private void brukResept() {
		if (resepter.stoerrelse() == 0) {
			System.out.println("Det finnes ingen resepter i systemet.");
			return;
		}

		printPasienter();
		String pasientNavn = spoerOmString("Hvem vil du se oversikt over resepter for?");
		while (finnPasient(pasientNavn) == null) {
			pasientNavn = spoerOmString("Vennligst oppgi en eksisterende pasient: ");
		}

		printResepterForPasient(pasientNavn);
		int reseptId = spoerOmInt("Vennligst oppgi ID'en på resepten du ønsker å bruke: ");
		while (finnReseptTilPasient(reseptId, pasientNavn) == null) {
			reseptId = spoerOmInt("Finner ikke resepten, vennligst oppgi ID'en på resepten du ønsker å bruke: ");
		}

		Resept resept = finnReseptTilPasient(reseptId, pasientNavn);

		while (spoerOmString("Ønsker du å bruke " + resept.hentLegemiddel().hentNavn() + "? j/n").equals("n")) {
			reseptId = spoerOmInt("Vennligst oppgi ID'en på resepten du ønsker å bruke: ");
			resept = finnReseptTilPasient(reseptId, pasientNavn);
		}

		resept.brukResept();
		System.out.println("Resept brukt, gjenværende reit er: " + resept.hentReit());
	}

	private Lege finnLege(String navn) {
		for (Lege lege : leger) {
			if (lege.hentNavn().equals(navn)) {
				return lege;
			}
		}
		return null;
	}

	private Pasient finnPasient(String navn) {
		for (Pasient pasient : pasienter) {
			if (pasient.hentNavn().equals(navn)) {
				return pasient;
			}
		}
		return null;
	}

	private Pasient finnPasient(int id) {
		for (Pasient pasient : pasienter) {
			if (pasient.hentPasientId() == id) {
				return pasient;
			}
		}
		return null;
	}

	private Legemiddel finnLegemiddel(String navn) {
		for (Legemiddel legemiddel : legemidler) {
			if (legemiddel.hentNavn().equals(navn)) {
				return legemiddel;
			}
		}
		return null;
	}

	private Legemiddel finnLegemiddel(int id) {
		for (Legemiddel legemiddel : legemidler) {
			if (legemiddel.hentId() == id) {
				return legemiddel;
			}
		}
		return null;
	}

	private Resept finnReseptTilPasient(int reseptId, String pasient) {
		for (Resept resept : finnPasient(pasient).hentAlleResepter()) {
			if (resept.hentId() == reseptId) {
				return resept;
			}
		}
		return null;
	}

	private void skrivUtStatistikk() {
		int svar = spoerOmInt(statistikkmeny());
		while (svar != 1 && svar != 2 && svar != 3) {
			svar = spoerOmInt("Ikke gyldig svaralternativ. Prøv igjen: ");
		}

		switch (svar) {
		case 1:
			System.out
					.println("Totalt antall resepter med vanedannende legemidler er: " + antallResepterVanedannende());
			break;

		case 2:
			System.out.println(
					"Totalt antall vanedannende resepter utskrevne til militære er: " + antallVanedannendeMilitaer());
			break;

		case 3:
			System.out.println("Statistikk om mulig misbruk av narkotika: ");
			System.out.println("Leger som har skrevet ut vanedannende legemidler på resept: ");
			printAntallLegerVanedannende();
			System.out.println("Pasienter med gyldig resept på vanedannende legemidler: ");
			printAntallPasienterVanedannende();
			break;
		}
	}

	private void skrivTilFil(String filename) {
		try {
			Writer writer = new FileWriter(filename);
			String legemidler = "# Legemidler (navn, type, pris, virkestoff [, styrke]\n";
			String leger = "# Leger (navn, avtalenr / 0 hvis ingen avtale)\n";
			String pasienter = "# Pasienter (navn)\n";
			String resepter = "# Resepter (type, legemiddelNummer, legeNavn, persID, [reit])\n";

			writer.write(legemidler);
			for (Legemiddel legemiddel : this.legemidler) {
				String skrivLegemiddel = "";
				skrivLegemiddel += legemiddel.hentNavn() + ", " + legemiddel.hentType() + ", " + legemiddel.hentPris()
						+ ", " + legemiddel.hentVirkestoff() + ", ";
				if (legemiddel.hentType().equals("b")) {
					skrivLegemiddel += ((LegemiddelB) legemiddel).hentVanedannendeStyrke();
				} else if (legemiddel.hentType().equals("a")) {
					skrivLegemiddel += ((LegemiddelA) legemiddel).hentNarkotiskStyrke();
				}
				writer.write(skrivLegemiddel += "\n");
			}

			writer.write(leger);
			for (Lege lege : this.leger) {
				String skrivLege = "";
				skrivLege += lege.hentNavn() + ", ";
				if (lege instanceof Fastlege) {
					skrivLege += ((Fastlege) lege).hentAvtalenummer();
				} else {
					skrivLege += 0;
				}
				writer.write(skrivLege + "\n");
			}

			writer.write(pasienter);
			for (Pasient pasient : this.pasienter) {
				writer.write(pasient.hentNavn() + "\n");
			}

			writer.write(resepter);
			for (Resept resept : this.resepter) {
				String skrivResept = "";
				if (resept instanceof MilitaerResept) {
					skrivResept += "militaer, ";
				} else if (resept instanceof PResept) {
					skrivResept += "prevensjon, ";
				} else {
					skrivResept += resept.farge() + ", ";
				}
				skrivResept += resept.hentId() + ", " + resept.hentLege().hentNavn() + ", " + resept.hentPasientId()
						+ ", " + resept.hentReit();
				writer.write(skrivResept + "\n");
			}

			writer.close();

		} catch (IOException ex) {
		}
	}

	private int antallResepterVanedannende() {
		int antall = 0;
		for (Resept resept : resepter) {
			if (resept.hentLegemiddel() instanceof LegemiddelB) {
				antall++;
			}
		}
		return antall;
	}

	private int antallVanedannendeMilitaer() {
		int antall = 0;
		for (Resept resept : resepter) {
			if (resept.hentLegemiddel() instanceof LegemiddelB && resept instanceof MilitaerResept) {
				antall++;
			}
		}
		return antall;
	}

	private void printAntallLegerVanedannende() {
		int antall;
		for (Lege lege : leger) {
			antall = 0;
			for (Resept resept : lege.hentUtAlleResepter()) {
				if (resept.hentLegemiddel() instanceof LegemiddelB) {
					antall += 1;
				}
			}
			if (antall > 0) {
				System.out.println("Lege: " + lege.hentNavn() + " Antall: " + antall);
			}
		}
	}

	private void printAntallPasienterVanedannende() {
		int antall;
		for (Pasient pasient : pasienter) {
			antall = 0;
			for (Resept resept : pasient.hentAlleResepter()) {
				if (resept.hentLegemiddel() instanceof LegemiddelB && resept.bruk()) {
					antall++;
				}
			}
			if (antall > 0) {
				System.out.println("Pasient: " + pasient.hentNavn() + " Antall: " + antall);
			}
		}
	}

	private String spoerOmString(String print) {
		try {
			System.out.println(print);
			Scanner strng = new Scanner(System.in);
			return strng.nextLine();

		} catch (InputMismatchException ex) {
			return null;
		}
	}

	private int spoerOmInt(String print) {
		try {
			System.out.println(print);
			Scanner nt = new Scanner(System.in);
			return nt.nextInt();

		} catch (InputMismatchException ex) {
			return 0;
		}
	}

	private double spoerOmDouble(String print) {
		try {
			System.out.println(print);
			Scanner dbl = new Scanner(System.in);
			return Double.parseDouble(dbl.nextLine());

		} catch (InputMismatchException ex) {
			return 0.0;
		}
	}

	private String statistikkmeny() {
		return "Hva slags statistikk ønsker du å se? \n"
				+ "1. Totalt antall utskrevne resepter på vanedannende legemidler.\n"
				+ "2. Totalt antall vanedannende resepter utskrevne til militære.\n"
				+ "3. Statistikk om mulig misbruk av narkotika.\n";
	}

	private String skrivHovedmeny() {
		return "Velkommen til Christines fantastiske legesystem!\n" + "Hva ønsker du å gjøre?\n"
				+ "1. Skriv ut en fullstendig oversikt over systemet.\n" + "2. Opprette og legge til nye elementer.\n"
				+ "3. Bruke en resept.\n" + "4. Skriv ut statistikk.\n" + "5. Skrive alle data til en fil.\n"
				+ "6. Avslutt program.\n";
	}

	private String leggTilElementMeny() {
		return "Hva ønsker du å legge til?\n" + "1. Lege.\n" + "2. Pasient.\n" + "3. Resept.\n" + "4. Legemiddel\n";
	}

	private String legemiddelMeny() {
		return "Hva slags type legemiddel vil du legg til?\n" + "1. Type A.\n" + "2. Type B.\n" + "3. Type C.\n";
	}

	private void printLeger() {
		for (Lege lege : leger) {
			System.out.println(lege.hentNavn());
		}
	}

	private void printLegemidler() {
		for (Legemiddel legemiddel : legemidler) {
			System.out.println(legemiddel.hentNavn());
		}
	}

	private void printPasienter() {
		for (Pasient pasient : pasienter) {
			System.out.println(pasient.hentNavn());
		}
	}

	private void printResepterForPasient(String pasientNavn) {
		System.out.println("Henter resepter for " + pasientNavn);
		for (Resept resept : finnPasient(pasientNavn).hentAlleResepter()) {
			System.out.println("Legemiddel: " + resept.hentLegemiddel().hentNavn() + " ID: " + resept.hentId()
					+ " Reit: " + resept.hentReit() + "\n");
		}
	}

	@Override
	public String toString() {
		String print = "";
		print += "Leger: \n";
		for (Lege lege : leger) {
			print += lege.toString();
			print += "\n";
		}

		print += "Resepter: \n";
		for (Resept resept : resepter) {
			print += resept.toString();
			print += "\n";
		}

		print += "Pasienter: \n";
		for (Pasient pasient : pasienter) {
			print += pasient.toString();
			print += "\n";
		}
		
		print += "Legemidler: \n";
		for (Legemiddel legemiddel : legemidler) {
			print += legemiddel.toString();
			print += "\n";
		}
		return print;
	}
}