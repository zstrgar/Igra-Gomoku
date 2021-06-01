package inteligenca;

import logika.Igra;

import splosno.KdoIgra;
import splosno.Koordinati;

public class Inteligenca extends KdoIgra{  //podrazred razreda splosno.KdoIgra
	
	protected int algoritem = 1;
	
	public Inteligenca() {
		super("Algebros");  // ime skupine
	}
	
	/**
	 * Metoda izberiPotezo, v Igri igra izbere potezo v skladu z algoritmom, ki smo ga znotraj metode navedli (in jo odigra)
	 * @param igra
	 * @return
	 * @throws Exception
	 */
	public Koordinati izberiPotezo(Igra igra) {
		if (algoritem == 1) return new Alphabeta(2).izberiPotezo(igra);
		else if (algoritem == 2) return new Minimax(2).izberiPotezo(igra);
		else return new Alphabeta(2).izberiPotezo(igra);
	}

	public void zamenjajAlgoritem(int i) {
		this.algoritem = i;
	}


}
