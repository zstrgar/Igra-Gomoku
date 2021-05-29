package inteligenca;

import java.util.LinkedList;
import java.util.List;

public class NajboljseOcenjenePozicije {
	
	private LinkedList<OcenjenaPozicija> pomnilnik;
		
	public NajboljseOcenjenePozicije() {
		this.pomnilnik = new LinkedList<OcenjenaPozicija> ();
	}
	
	/**
	 * Metoda addIfBest primerja dve poziciji in v pomnilnik z OcenjenimiPozicijami doda boljšo
	 * @param ocenjenaPozicija (neka pozicija z oceno)
	 */
	public void addIfBest(OcenjenaPozicija ocenjenaPozicija) {
		if (pomnilnik.isEmpty()) pomnilnik.add(ocenjenaPozicija);
		else {
			OcenjenaPozicija ocenjenevanaPozicija = pomnilnik.getFirst();
			switch (ocenjenaPozicija.compareTo(ocenjenevanaPozicija)) {
			case 1: 
				pomnilnik.clear();  // ocenjenaPoteza > ocenjevanaPozicija. Ocenjena pozicija je boljša, zato vse odstranimo in jo dodamo.
			case 0: // ali 1
				pomnilnik.add(ocenjenaPozicija); // ocenjenaPozicija >= ocenjevanaPozicija. Pozicijo dodamo v seznam, če je enaka kot so že notri
			}			
		}
	}
	
	
	public List<OcenjenaPozicija> list() {
		return (List<OcenjenaPozicija>) pomnilnik;
	}

}

