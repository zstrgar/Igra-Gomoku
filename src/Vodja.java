import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

import logika.Igra;
import logika.Igralec;
import splosno.Koordinati;


public class Vodja {
	
	private static enum VrstaIgralca{ R, C; }
	
	private static BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
	
	private static Map<Igralec, VrstaIgralca> vrstaIgralca;
	
	public static void igramo() {
		while(true) {
			System.out.println("Nova igra. Prosim, da izberete:");
			System.out.println(" 1 - B èlovek, T raèunalnik");
			System.out.println(" 2 - B raèunalnik, T èlovek");
			System.out.println(" 3 - B èlovek, T èlovek");
			System.out.println(" 4 - izhod");
			String s = r.readLine();
			if (s.equals("1")) {
				vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
				vrstaIgralca.put(Igralec.B, VrstaIgralca.C); 
				vrstaIgralca.put(Igralec.T, VrstaIgralca.R); 			
			} else if (s.equals("2")) {
				vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
				vrstaIgralca.put(Igralec.B, VrstaIgralca.R); 
				vrstaIgralca.put(Igralec.T, VrstaIgralca.C); 			
			} else if (s.equals("3")) {
				vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
				vrstaIgralca.put(Igralec.B, VrstaIgralca.C); 
				vrstaIgralca.put(Igralec.T, VrstaIgralca.C); 			
			} else if (s.equals("4")) {
				System.out.println("Nasvidenje!");
				break;
			} else {
				System.out.println("Vnos ni veljaven");
				continue;
			}
			// Èe je s == "1", "2" ali "3"
			Igra igra = new Igra ();
			igranje : while (true) {
				switch (igra.stanje()) {
				case ZMAGA_B: 
					System.out.println("Zmagal je igralec B");
					System.out.println("Zmagovalna vrsta " + igra.zmagovalnaVrsta().toString());
					break igranje;
				case ZMAGA_T: 
					System.out.println("Zmagal je igralec T");
					System.out.println("Zmagovalna vrsta " + igra.zmagovalnaVrsta().toString());
					break igranje;
				case NEODLOCENO: 
					System.out.println("Igra je neodloèena");
					break igranje;
				case V_TEKU: 
					Igralec igralec = igra.naPotezi;
					VrstaIgralca vrstaNaPotezi = vrstaIgralca.get(igralec);
					Koordinati poteza = null;
					switch (vrstaNaPotezi) {
					case C: 
						poteza = clovekovaPoteza(igra);
						break;
					case R:
						poteza = racunalnikovaPoteza(igra);
						break;
					}
					System.out.println("Igralec " + igralec + " je igral " + poteza);
				}
			}
		}
	}
	
	private static Random random = new Random ();

/** raèunalnikova poteza*/
	public static Koordinati racunalnikovaPoteza(Igra igra) {
		List<Koordinati> moznePoteze = igra.poteze();
		int randomIndex = random.nextInt(moznePoteze.size());
		Koordinati poteza = moznePoteze.get(randomIndex);
		igra.odigraj(poteza);
		return poteza;		
	}

/**èlovekova poteza*/
	public static Koordinati clovekovaPoteza(Igra igra) throws IOException {
		while (true) {
			System.out.println("Igralec " + igra.naPotezi.toString() +
					" vnesite potezo \"x y\"");
			String s = r.readLine();
			int i = s.indexOf (' '); // kje je presledek
			if (i == -1 || i  == s.length()) { 
				System.out.println("Napaèen format"); continue; 
			}
			String xString = s.substring(0,i);
			String yString = s.substring(i+1);
			int x, y;
			try {
				x = Integer.parseInt(xString);
				y = Integer.parseInt(yString);		
			} catch (NumberFormatException e) {
				System.out.println("Napaèen format"); continue; 
			}
			if (x < 0 || x >= Igra.N || y < 0 || y >= Igra.N){
				System.out.println("Napaèen format"); continue; 			
			}
			Koordinati poteza = new Koordinati(x,y);
			if (igra.odigraj(poteza)) return poteza;
			System.out.println(poteza.toString() + " ni možna");
		}
	}

}
