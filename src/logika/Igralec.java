package logika;

/**
 * Možni igralci. (C - vrn, B - bel)
 */

public enum Igralec {
	T, B;

	public Igralec nasprotnik() {
		return (this == T ? B : T);
	}

	public Polje getPolje() {
		return (this == T ? Polje.T : Polje.B);
	}
}
