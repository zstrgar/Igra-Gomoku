package inteligenca;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.PriorityQueue;

import logika.Igra;
import logika.Igralec;

import splosno.Koordinati;

public class Alphabeta extends Inteligenca {
	
	private static final int ZMAGA = 1000; // vrednost zmage
	private static final int ZGUBA = -ZMAGA;  // vrednost poraza
	private static final int NEODLOC = 0;  // vrednost neodločene igre
	
	private static final int K_NAJBOLJSIH = 15; //stevilo najboljsih potez, na katerih bomo delali a-b pruning
	
	private int globina;
	
	public Alphabeta (int globina) {
		super();
		this.globina = globina;
	}
	
	@Override
	public Koordinati izberiPotezo (Igra igra) {
		// Na začetku alpha = ZGUBA in beta = ZMAGA
		return alphabetaPoteze(igra, this.globina, ZGUBA, ZMAGA, igra.igralecNaPotezi()).poteza;
	}
	
	public static OcenjenaPozicija alphabetaPoteze(Igra igra, int globina, int alpha, int beta, Igralec jaz) {
		int ocena;
		// Če sem računalnik, maksimiramo oceno z začetno oceno ZGUBA
		// Če sem pa človek, minimiziramo oceno z začetno oceno ZMAGA
		if (igra.igralecNaPotezi() == jaz) {ocena = ZGUBA;} else {ocena = ZMAGA;}
		
		
		Set<Koordinati> moznePoteze = igra.moznePoteze();
		Koordinati kandidat = moznePoteze.iterator().next(); // Možno je, da se ne spremini vrednost kanditata. Zato ne more biti null.
		
		//Odkomentiraj ti dve vrstici, če želiš algoritem samo na topPotezah (ki ne deluje dovolj dobro)
		//List<Koordinati> topPoteze = vrniTopOcenjenePoteze(K_NAJBOLJSIH, moznePoteze, igra); //Skrajšamo seznam moznih potez, tako da ocenimo vse mozne poteze in samo najboljših k damo v nov seznam topPotez
		//for (Koordinati poteza: topPoteze) {	//algoritem izvedemo na topPotezah
		
		//Zakomentiraj, če želiš algoritem na topPotezah in ne na moznihPotezah
		for (Koordinati poteza: moznePoteze) {	//algoritem izvedemo samo na moznihPotezah
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.odigraj(poteza);
			int ocenaPozicije;
			switch (kopijaIgre.stanje()) {
			case ZMAGA_BEL: ocenaPozicije = (jaz == Igralec.BEL ? ZMAGA : ZGUBA); break;
			case ZMAGA_CRN: ocenaPozicije = (jaz == Igralec.CRN ? ZMAGA : ZGUBA); break;
			case NEODLOCENO: ocenaPozicije = NEODLOC; break;
			default:
				// Nekdo je na potezi
				
				OcenjevalecPozicije ocenjevalecPozicije = new OcenjevalecPozicije();
				if (globina == 1) ocenaPozicije = ocenjevalecPozicije.oceniPozicijo(kopijaIgre, jaz);
				else ocenaPozicije = alphabetaPoteze (kopijaIgre, globina-1, alpha, beta, jaz).ocena;
			}
			
			if (igra.igralecNaPotezi() == jaz) { // Maksimiramo oceno
				if (ocenaPozicije > ocena) { // mora biti > namesto >=
					ocena = ocenaPozicije;
					kandidat = poteza;
					alpha = Math.max(alpha,ocena);
				}
				
			} else { // igra.naPotezi() != jaz, torej minimiziramo oceno
				if (ocenaPozicije < ocena) { // mora biti < namesto <=
					ocena = ocenaPozicije;
					kandidat = poteza;
					beta = Math.min(beta, ocena);					
				}	
			}
			
			if (alpha >= beta) // Izstopimo iz "for loop", saj ostale poteze ne pomagajo
				return new OcenjenaPozicija (kandidat, ocena);
		}
		return new OcenjenaPozicija (kandidat, ocena);
	}
	
	/**
	 * Metoda vrniTopOcenjenePoteze vrne seznam najboljših k potez iz množice moznePoteze v igri.
	 * @param k - koliko najboljših želimo
	 * @param moznePoteze
	 * @param igra
	 * @param jaz - z vidika katerega igralca gledamo
	 * @return seznam najboljših k potez (topPoteze)
	 */
	private static List<Koordinati> vrniTopOcenjenePoteze(int k, Set<Koordinati> moznePoteze, Igra igra, Igralec jaz) {
		//Razvrsti elemente od največjega do najmanjšega 
		PriorityQueue<OcenjenaPozicija> rangiranePozicije = new PriorityQueue(Collections.reverseOrder()); //po defaultu jih razvršča od najmanjšega do največjega, zato reverse!
		
		//Vsako potezo odigra in jo oceni ter shrani v rangiranePozicije
		for (Koordinati poteza: moznePoteze) {
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.odigraj(poteza);
			OcenjevalecPozicije ocenjevalecPozicije = new OcenjevalecPozicije();
			int ocena = ocenjevalecPozicije.oceniPozicijo(kopijaIgre, jaz);
			
			rangiranePozicije.add(new OcenjenaPozicija(poteza, ocena));
		}
		
		
		List<Koordinati> topPoteze = new ArrayList<>();
		
		//iz rangiranihPozicij jih najboljših k odvzamemo
		for (int i=0; i<k;i++) {
			OcenjenaPozicija ocenjenaPozicija = rangiranePozicije.poll();
			
			if(ocenjenaPozicija != null) {
				topPoteze.add(ocenjenaPozicija.poteza);
				System.out.println(ocenjenaPozicija.ocena);
			}
 		}
		return topPoteze;
		
	}

}