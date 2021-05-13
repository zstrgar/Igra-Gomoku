package inteligenca;

import splosno.Koordinati;

public class OcenjenaPozicija {
	
	Koordinati poteza;
	int ocena;
	
	public OcenjenaPozicija (Koordinati poteza, int ocena) {
		this.poteza = poteza;
		this.ocena = ocena;
	}
	
	public int compareTo (OcenjenaPozicija op) {
		if (this.ocena < op.ocena) return -1;
		else if (this.ocena > op.ocena) return 1;
		else return 0;
	}

}

