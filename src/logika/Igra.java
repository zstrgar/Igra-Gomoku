package logika;

public class Igra {
	
	//Velikost igralne plo��e
	public static final int N = 15;
	
	//Igralno polje
	private Polje[][] plosca;
	
	//Igralec, ki je trenutno na potezi
	public Igralec naPotezi;
	
/**
* Nova igra, v za�etni poziciji je prazna in na potezi je T.
*/
	
	public Igra() {
		plosca = new Polje[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				plosca[i][j] = Polje.PRAZNO;
			}
		}
		naPotezi = Igralec.T;
	}
	
/** 
 * TODO: 
 * public boolean odigraj(Koordinati koordinati)
 */
	
	
}
