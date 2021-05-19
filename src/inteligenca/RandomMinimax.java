package inteligenca;


import java.util.List;
import java.util.Random;

import logika.Igra;

import splosno.Koordinati;


import inteligenca.NajboljseOcenjenePozicije;

public class RandomMinimax extends Inteligenca {
	
	private static final Random RANDOM = new Random();
	
	private static final int ZMAGA = 100; // vrednost zmage, več kot vsaka druga ocena pozicije
	private static final int NEODLOC = 0;  // vrednost neodločene igre	
	
	private int globina;
	
	public RandomMinimax (int globina) {
		super();
		this.globina = globina;		
	}
	
	@Override
	public Koordinati izberiPotezo (Igra igra) {
		List<OcenjenaPozicija> ocenjenePozicije = najboljsePozicije(igra, this.globina);
		// System.out.println(ocenjenePozicije.size() + " potez z vrednostjo " + ocenjenePozicije.get(0).ocena);
		int i = RANDOM.nextInt(ocenjenePozicije.size());	//naključno izberemo eno izmed najboljših pozicij
		return ocenjenePozicije.get(i).poteza;		
	}
	
	/**
	 * Metoda najboljsePozicije vrne seznam vseh pozicij, ki imajo največjo vrednost z vidika trenutnega igralca na potezi
	 * @param igra
	 * @param globina
	 * @return Seznam Ocenjenih pozicij igre
	 */
	public static List<OcenjenaPozicija> najboljsePozicije(Igra igra, int globina) {
		NajboljseOcenjenePozicije najboljsePozicije = new NajboljseOcenjenePozicije();
		List<Koordinati> moznePoteze = igra.moznePoteze();
		for (Koordinati poteza: moznePoteze) {
			Igra kopijaIgre = new Igra(igra); 
			kopijaIgre.odigraj (poteza);	//poskusimo vsako potezo v novi kopiji igre
			int ocena;
			switch (kopijaIgre.stanje()) {
			case ZMAGA_BEL:
			case ZMAGA_CRN: ocena = ZMAGA; break; // poteza je zmagovalna poteza
			case NEODLOCENO: ocena = NEODLOC; break;
			default: //nekdo je na potezi
				if (globina==1) ocena = OceniPozicijo.oceniPozicijo(kopijaIgre,igra.igralecNaPotezi());
				else ocena = -najboljsePozicije(kopijaIgre,globina-1).get(0).ocena;   //negacija ocene z vidika nasportnika
			}
			najboljsePozicije.addIfBest(new OcenjenaPozicija(poteza, ocena));			
		}
		return najboljsePozicije.list();
	}

	
}

