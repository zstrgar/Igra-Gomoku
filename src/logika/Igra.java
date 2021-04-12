package logika;

import java.util.LinkedList;
import java.util.List;

import splosno.Koordinati;


public class Igra {
	
	//Velikost igralne plošèe
	public static final int N = 15;
	
	//Igralno polje
	private Polje[][] plosca;
	
	//Igralec, ki je trenutno na potezi
	public Igralec naPotezi;
	
/**
* Nova igra, v zaèetni poziciji je prazna in na potezi je CRN.
*/
	
	public Igra() {
		plosca = new Polje[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				plosca[i][j] = Polje.PRAZNO;
			}
		}
		naPotezi = Igralec.CRN;
	}
	
/**
* seznam možnih potez
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
 * TODO: 
 * public boolean odigraj(Koordinati koordinati)
 */
	
	
}
