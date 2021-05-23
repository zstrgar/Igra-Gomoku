package inteligenca;

import splosno.Koordinati;

public class OcenjenaPozicija {
	
	Koordinati poteza;
	int ocena;
	
	/**
	 * OcenjenaPozicija nosi podatek o poziciji z oceno 
	 * @param poteza
	 * @param ocena
	 */
	public OcenjenaPozicija (Koordinati poteza, int ocena) {
		this.poteza = poteza;
		this.ocena = ocena;
	}
	
	/**
	 * Z metodo compareTo primerjamo dve poziciji, 
	 * če je ocena primerjane pozicije slabša, vrne -1, če je enaka vrne 0, če je boljša vrne 1.
	 * @param ocenjenaPozicija
	 * @return -1, 1 ali 0
	 */
	public int compareTo (OcenjenaPozicija ocenjenaPozicija) {
		if (this.ocena < ocenjenaPozicija.ocena) return -1;
		else if (this.ocena > ocenjenaPozicija.ocena) return 1;
		else return 0;
	}

}

