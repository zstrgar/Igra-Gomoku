package inteligenca;

import logika.Igra;

import splosno.KdoIgra;
import splosno.Koordinati;

public class Inteligenca extends KdoIgra{  //podrazred razreda splosno.KdoIgra
	
	public Inteligenca() {
		super("Algebros");  // ime skupine
	}

	// TODO !! *********************************************************************** 
	
	public Koordinati izberiPotezo(Igra igra) {
		return new Alphabeta(2).izberiPotezo(igra);
	}

}
