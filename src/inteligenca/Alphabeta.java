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
	
	private static final int STEVILO_GLEDANIH = 10; //stevilo moznih potez, na katerih bomo delali a-b pruning
	
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
		
		List<Koordinati> topPoteze = vrniTopOcenjenePoteze(15, moznePoteze, igra, jaz);
		
		for (Koordinati p: topPoteze) {
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.odigraj(p);
			int ocenap;
			switch (kopijaIgre.stanje()) {
			case ZMAGA_BEL: ocenap = (jaz == Igralec.BEL ? ZMAGA : ZGUBA); break;
			case ZMAGA_CRN: ocenap = (jaz == Igralec.CRN ? ZMAGA : ZGUBA); break;
			case NEODLOCENO: ocenap = NEODLOC; break;
			default:
				// Nekdo je na potezi
				
				OcenjevalecPozicije ocenjevalecPozicije = new OcenjevalecPozicije();
				if (globina == 1) ocenap = ocenjevalecPozicije.oceniPozicijo(kopijaIgre, jaz);
				else ocenap = alphabetaPoteze (kopijaIgre, globina-1, alpha, beta, jaz).ocena;
			}
			
			if (igra.igralecNaPotezi() == jaz) { // Maksimiramo oceno
				if (ocenap > ocena) { // mora biti > namesto >=
					ocena = ocenap;
					kandidat = p;
					alpha = Math.max(alpha,ocena);
				}
				
			} else { // igra.naPotezi() != jaz, torej minimiziramo oceno
				if (ocenap < ocena) { // mora biti < namesto <=
					ocena = ocenap;
					kandidat = p;
					beta = Math.min(beta, ocena);					
				}	
			}
			
			if (alpha >= beta) // Izstopimo iz "for loop", saj ostale poteze ne pomagajo
				return new OcenjenaPozicija (kandidat, ocena);
		}
		return new OcenjenaPozicija (kandidat, ocena);
	}
	
	
	private static List<Koordinati> vrniTopOcenjenePoteze(int k, Set<Koordinati> moznePoteze, Igra igra, Igralec jaz) {
		PriorityQueue<OcenjenaPozicija> rangiranePozicije = new PriorityQueue(Collections.reverseOrder());
		
		for (Koordinati poteza: moznePoteze) {
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.odigraj(poteza);
			OcenjevalecPozicije ocenjevalecPozicije = new OcenjevalecPozicije();
			int ocena = ocenjevalecPozicije.oceniPozicijo(kopijaIgre, jaz);
			
			rangiranePozicije.add(new OcenjenaPozicija(poteza, ocena));
		}
		
		List<Koordinati> topPoteze = new ArrayList<>();
		
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
