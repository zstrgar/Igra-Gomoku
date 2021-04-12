package logika;

/**
 * Možni igralci.
 */

public enum Igralec {  //to je (poseben) razred enum
		CRN, BEL;

	public Igralec nasprotnik() {
		if (this == CRN) return BEL;
		else return CRN;
	}

	public Polje getPolje() {  // s tem zvem, katero polje je igral igralec
		if (this == CRN) return Polje.CRNO;
		else return Polje.BELO;
	}
}
