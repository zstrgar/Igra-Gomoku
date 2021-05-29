package inteligenca;

import logika.Igra;

import splosno.KdoIgra;
import splosno.Koordinati;

public class Inteligenca extends KdoIgra{  //podrazred razreda splosno.KdoIgra
	
	protected int algoritem = 1; // 1=alphabeta, 2=minimax

	public Inteligenca() {
		super("Algebros");  // ime skupine
	}
	
	/**
	 * Metoda izberiPotezo, v Igri igra izbere potezo v skladu z algoritmom, ki smo ga znotraj metode navedli (in jo odigra)
	 * @param igra
	 * @return
	 * @throws Exception
	 */
	public Koordinati izberiPotezo(Igra igra) throws Exception {
		// TODO debug lahko izbrises da ne izpisuje v konzolo
		// TODO dodaj MonteCarlo in ostale
		System.out.println("Izbrani algoritem je: " + algoritem);
		if (algoritem==1) return new Alphabeta(2).izberiPotezo(igra);
		else if (algoritem == 2) return new Minimax(2).izberiPotezo(igra);
		else{
			throw new Exception("Napačna izbira algoritma!");
		}
	}

	public void zamenjajAlgoritem(int i) {
		this.algoritem = i;
	}

}
