package logika;

/**
 * Mozni igralci.
 */

public enum Igralec {  
		CRN, BEL;
	
	/**
	 * metoda nasprotnik() vrne Igralca nasprotnika
	 * @return ce je trenutni igralec BEL, vrne CRN
	 */
	public Igralec nasprotnik() {
		if (this == CRN) return BEL;
		else return CRN;
	}
	
	/**
	 * metoda getPolje() vrne polje, ki ga je igralec odigral
	 * @return barva polja (BELO, CRNO)
	 */
	public Polje getPolje() {  // s tem zvem, katero polje je igral igralec
		if (this == CRN) return Polje.CRNO;
		else return Polje.BELO;
	}
}
