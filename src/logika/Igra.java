package logika;

public class Igra {
	
	//Velikost igralne plošèe
	public static final int N = 15;
	
	//Igralno polje
	private Polje[][] plosca;
	
	//Igralec, ki je trenutno na potezi
	public Igralec naPotezi;
	
/**
* Nova igra, v zaèetni poziciji je prazna in na potezi je T.
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
