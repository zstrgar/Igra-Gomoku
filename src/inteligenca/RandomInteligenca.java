package inteligenca;

import java.util.Random;
import java.util.Set;

import logika.Igra;
import splosno.Koordinati;

public class RandomInteligenca extends Inteligenca{
	
	private static Random random = new Random ();
	
	public RandomInteligenca() {
		super(1);
	}
	
	/**
	 * RandomInteligenca naključno izbere potezo iz množice vseh možnih potez
	 */
	public Koordinati izberiPotezo(Igra igra) {
		Set<Koordinati> moznePoteze = igra.moznePoteze();        
		Koordinati iskanaPoteza = null;
		int velikost = moznePoteze.size();
		int randomIndex = random.nextInt(velikost-1);
		int stevec = 0;
		for(Koordinati poteza : moznePoteze) {
			if(stevec == randomIndex){
				iskanaPoteza = poteza; 
				break;
			}
			else stevec++;
		}
		return iskanaPoteza;
	}
	
}
