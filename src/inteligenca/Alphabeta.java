package inteligenca;

import java.util.List;

import logika.Igra;
import logika.Igralec;

import splosno.Koordinati;

import inteligenca.OceniPozicijo;

public class Alphabeta extends Inteligenca {
	
	private static final int ZMAGA = 100; // vrednost zmage
	private static final int ZGUBA = -ZMAGA;  // vrednost poraza
	private static final int NEODLOC = 0;  // vrednost neodločene igre	
	
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
		List<Koordinati> moznePoteze = igra.moznePoteze();
		Koordinati kandidat = moznePoteze.get(0); // Možno je, da se ne spremini vrednost kanditata. Zato ne more biti null.
		for (Koordinati p: moznePoteze) {
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.odigraj (p);
			int ocenap;
			switch (kopijaIgre.stanje()) {
			case ZMAGA_BEL: ocenap = (jaz == Igralec.BEL ? ZMAGA : ZGUBA); break;
			case ZMAGA_CRN: ocenap = (jaz == Igralec.CRN ? ZMAGA : ZGUBA); break;
			case NEODLOCENO: ocenap = NEODLOC; break;
			default:
				// Nekdo je na potezi
				if (globina == 1) ocenap = OceniPozicijo.oceniPozicijo(kopijaIgre, jaz);
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

}
