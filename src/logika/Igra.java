package logika;

import java.util.LinkedList;
import java.util.List;

import splosno.Koordinati;


public class Igra {
	
	//Velikost igralne plo��e
	public static final int N = 15;  //final, da je N fiksiran
	
	//Igralna plosca, ki sestoji iz polj(prazno, crno, belo)
	private Polje[][] plosca;
	
	//Igralec (crn, bel), ki je trenutno na potezi
	public Igralec naPotezi;
	
/**
* Nova igra, na za�etku je prazna in na potezi je CRN.
*/
	public Igra() {  				//konstruktor
		
		plosca = new Polje[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				plosca[i][j] = Polje.PRAZNO;
			}
		}
		
		naPotezi = Igralec.CRN;
	}
	
	/**
	 * metoda moznePoteze() vrne seznam List<Koordinati> mo�nih potez 
	 * @return moznePoteze
	 */
	public List<Koordinati> moznePoteze() {
		LinkedList<Koordinati> moznePoteze = new LinkedList<Koordinati>();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (plosca[i][j] == Polje.PRAZNO) {
					moznePoteze.add(new Koordinati(i, j));
				}
			}
		}
		return moznePoteze;
	}
	

	/**
	 * metoda veljavnostPoteze preveri, �e je poteza veljavna
	 * preveri, �e je polje prazno in �e sta koordinati X in Y med 0 in 15
	 * @param poteza
	 * @return true �e je veljavna, false, �e ni
	 */
	public boolean jeVeljavnaPoteza(Koordinati poteza) {   //NVM �e se sploh rabi to metodo
		if (plosca[poteza.getX()][poteza.getY()] != Polje.PRAZNO) return false;
		if (poteza.getX() < 0 || poteza.getX() > N) return false;
		if (poteza.getY() < 0 || poteza.getY() > N) return false;
		return true;
	}
	
	
	/**
	 * metoda odigraj(Koordinati poteza) vrne logi�no vrednost 
	 * spremeni vrednost igralca na potezi ter ga nastavi na nasprotnika
	 * @param poteza
	 * @return true, �e je polje prazno, false �e ni
	 */
	public boolean odigraj(Koordinati poteza) {
		if (jeVeljavnaPoteza(poteza)) {
			plosca[poteza.getX()][poteza.getY()] = naPotezi.getPolje();
			naPotezi = naPotezi.nasprotnik();
			return true;
		}
		else {
			return false;
		}
	}
	
	
	
	
	
}
