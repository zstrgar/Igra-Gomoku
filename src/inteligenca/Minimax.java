package inteligenca;

import java.util.List;

import logika.Igra;
import logika.Igralec;

import splosno.Koordinati;

import inteligenca.OceniPozicijo;

public class Minimax extends Inteligenca {
	
	private static final int ZMAGA = 100; // vrednost zmage
	private static final int PORAZ = -ZMAGA;  // vrednost izgube
	private static final int NEODLOC = 0;  // vrednost neodločene igre	
	
	private int globina;
	
	public Minimax (int globina) {
		super("minimax globina " + globina);
		this.globina = globina;
	}
	
	@Override
	public Koordinati izberiPotezo (Igra igra) {
		OcenjenaPozicija najboljsaPoteza = minimax(igra, this.globina, igra.igralecNaPotezi());
		return najboljsaPoteza.poteza;	
	}
	
	// vrne najboljso ocenjeno potezo z vidike igralca jaz
	public OcenjenaPozicija minimax(Igra igra, int globina, Igralec jaz) {
		OcenjenaPozicija najboljsaPoteza = null;
		List<Koordinati> moznePoteze = igra.moznePoteze();
		for (Koordinati p: moznePoteze) {
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.odigraj (p);
			int ocena;
			switch (kopijaIgre.stanje()) {
			case ZMAGA_BEL: ocena = (jaz == Igralec.BEL ? ZMAGA : PORAZ); break;
			case ZMAGA_CRN: ocena = (jaz == Igralec.CRN ? ZMAGA : PORAZ); break;
			case NEODLOCENO: ocena = NEODLOC; break;
			default:
				// nekdo je na potezi
				if (globina == 1) ocena = OceniPozicijo.oceniPozicijo(kopijaIgre, jaz);
				// globina > 1
				else ocena = minimax(kopijaIgre, globina-1, jaz).ocena;	
			}
			if (najboljsaPoteza == null 
					// max, če je p moja poteza
					|| jaz == igra.igralecNaPotezi() && ocena > najboljsaPoteza.ocena
					// sicer min 
					|| jaz != igra.igralecNaPotezi() && ocena < najboljsaPoteza.ocena)
				najboljsaPoteza = new OcenjenaPozicija(p, ocena);		
		}
		return najboljsaPoteza;
	}
}