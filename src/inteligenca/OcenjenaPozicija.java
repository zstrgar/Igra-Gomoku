package inteligenca;

import splosno.Koordinati;

public class OcenjenaPozicija {
	
	Koordinati poteza;
	int ocena;
	
	/**
	 * OcenjenaPozicija nosi podatek o potezi z oceno 
	 * @param poteza
	 * @param ocena
	 */
	
	public OcenjenaPozicija (Koordinati poteza, int ocena) {
		this.poteza = poteza;
		this.ocena = ocena;
	}
	
	public int compareTo (OcenjenaPozicija ocenjenaPozicija) {
		if (this.ocena < ocenjenaPozicija.ocena) return -1;
		else if (this.ocena > ocenjenaPozicija.ocena) return 1;
		else return 0;
	}

}

