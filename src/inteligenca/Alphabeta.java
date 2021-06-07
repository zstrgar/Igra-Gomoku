package inteligenca;

import java.util.Set;

import logika.Igra;
import logika.Igralec;

import splosno.Koordinati;

public class Alphabeta extends Inteligenca {
	
	private static final int ZMAGA = 1000; // vrednost zmage
	private static final int ZGUBA = -ZMAGA;  // vrednost poraza
	private static final int NEODLOC = 0;  // vrednost neodločene igre
	
	private int globina;
	
	public Alphabeta (int globina) {
		super(3);
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

}
