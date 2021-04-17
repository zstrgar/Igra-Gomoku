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
	
	// Pomo�en seznam vseh vrst na plo��i.
	private static final List<Vrsta> VRSTE = new LinkedList<Vrsta>();
	
	static {
		// Ta koda se izvede na za�etku, ko se prvi� po�ene program.
		// Njena naloga je, da prepozna vrednosti stati�nih
		// spremenljivk.
		
		// Prepoznavanje, �e smo dobili 5 v vrsto.
		int[][] mozneSmeri = {{1,0}, {0,1}, {1,1}, {1,-1}};  //smer {1,0} -> desno, {0,1} -> gor, {1,1} -> desno gor, {1, -1} -> desno dol
		for (int x = 0; x < N; x++) {
			for (int y = 0; y < N; y++) {
				for (int[] smer : mozneSmeri) {
					int dx = smer[0];	//premik v x smeri (desno, levo)
					int dy = smer[1];	//premik v y smeri (gor,dol)
					// �e je skrajno polje terice �e na plo��i, jo dodamo med terice
					if ((0 <= x + (5-1) * dx) && (x + (5-1) * dx < N) && 
						(0 <= y + (5-1) * dy) && (y + (5-1) * dy < N)) {
						int[] vrsta_x = new int[5];			//dol�ina seznama je 5, ker igramo 5 v vrsto
						int[] vrsta_y = new int[5];
						for (int k = 0; k < 5; k++) {
							vrsta_x[k] = x + dx * k;
							vrsta_y[k] = y + dy * k;
						}
						VRSTE.add(new Vrsta(vrsta_x, vrsta_y));
					}
				}
			}
		}
	}
	
	
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
		
		naPotezi = Igralec.BEL;
	}
	
		/**
	 * @return igralna plosca
	 */
	public Polje[][] getPlosca () {
		return plosca;
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
	 * metoda cigavaVrsta(Vrsta vrsta) vrne igralca, ki ima zmagovalno vrsto (5vVrsto)
	 * @param vrsta
	 * @return IGrgalec (BEL ali CRN) ali null, �e ni zmagovalne vrste
	 */
	private Igralec cigavaVrsta(Vrsta vrsta) {
		int count_BELO = 0;
		int count_CRNO = 0;
		for (int k = 0; k < 5 && (count_BELO == 0 || count_CRNO == 0); k++) {
			switch (plosca[vrsta.x[k]][vrsta.y[k]]) {
			case BELO: count_BELO += 1; break;
			case CRNO: count_CRNO += 1; break;
			case PRAZNO: break;
			}
		}
		if (count_BELO == 5) { return Igralec.BEL; }
		else if (count_CRNO == 5) { return Igralec.CRN; }
		else { return null; }
	}

	/**
	 * metoda zmagovalnaVrsta() vrne zmagovalno vrsto (�e jo nek igralec ima)
	 * @return zmagovalna vrsta, ali {@null}, �e je ni
	 */
	public Vrsta zmagovalnaVrsta() {
		for (Vrsta vrsta : VRSTE) {
			Igralec lastnik = cigavaVrsta(vrsta);
			if (lastnik != null) return vrsta;
		}
		return null;
	}
	
	
	/**
	 * metoda Stanje() vrne trenutno stanje igre (ZMAGA, NEODLOCENO, V_TEKU)
	 * @return trenutno stanje igre
	 */
	public Stanje stanje() {
		// Ali imamo zmagovalca?
		Vrsta vrsta = zmagovalnaVrsta();
		if (vrsta != null) {
			switch (plosca[vrsta.x[0]][vrsta.y[0]]) {
			case BELO: return Stanje.ZMAGA_BEL; 
			case CRNO: return Stanje.ZMAGA_CRN;
			case PRAZNO: assert false;
			}
		}
		// Ali imamo kak�no prazno polje?
		// �e ga imamo, igre ni konec in je nekdo na potezi
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (plosca[i][j] == Polje.PRAZNO) return Stanje.V_TEKU;
			}
		}
		// Polje je polno, rezultat je neodlo�en
		return Stanje.NEODLOCENO;
	}
	

	/**
	 * metoda veljavnostPoteze(Koordinati poteza) preveri, �e je poteza veljavna
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
	 * metoda odigraj(Koordinati poteza) najprej preveri, �e je poteza veljavna 
	 * �e je, spremeni vrednost igralca na potezi ter ga nastavi na nasprotnika
	 * @param poteza
	 * @return true, �e je poteza veljavna, false - �e ni
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
