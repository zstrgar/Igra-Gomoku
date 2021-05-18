package inteligenca;

import logika.Igra;
import logika.Igralec;
import logika.Polje;
import logika.Vrsta;

public class OceniPozicijo {
	
	/**
	 * Metoda oceniPozicijo oceni pozicijo igre igra za igralca jaz (po navadi je to igralec na potezi),
	 * ocena dobimo tako, da seštejemo vse delne vrste za igralca jaz in odštejemo vse delne vrste za igralca nasprotnika.
	 * Odštevanje v resnici pomeni prištevanje nasprotne vrednosti, ki jo nastavimo že v metodi oceniVrsto.
	 * @param igra
	 * @param jaz
	 * @return ocena
	 */
	public static int oceniPozicijo(Igra igra, Igralec jaz) {
		int ocena = 0;
		for (Vrsta vrsta : Igra.VRSTE) {
			ocena = ocena + oceniVrsto(vrsta, igra, jaz);
		}
		return ocena;	
	}
	
	/**
	 * Metoda oceniVrsto oceni določeno vrsto v igri igra z vidika igralca jaz.
	 * @param vrsta
	 * @param igra
	 * @param jaz
	 * @return Število (preštetih žetonov v vrsti (negativna za št. črnih, pozitivno št. za število belih)
	 */
	public static int oceniVrsto (Vrsta vrsta, Igra igra, Igralec jaz) {
		Polje[][] plosca = igra.getPlosca();
		int count_CRNO = 0;
		int count_BELO = 0;
		//Zanka po poljih vrste dolžine M. Zanko izvedemo le, če je število belih in črnih enako 0, ali pa je največ eden izmed števcev različen od 0.
		for (int k = 0; k < Igra.M && (count_CRNO == 0 || count_BELO == 0); k++) {
			switch (plosca[vrsta.x[k]][vrsta.y[k]]){
			case BELO: count_BELO += 1; break;
			case CRNO: count_CRNO += 1; break;
			case PRAZNO: break;
			}
		}
		//Če je v vrsti hkrati bel in črn žeton, je ta vrsta slaba, zato ima vrednost 0.
		if (count_BELO > 0 && count_CRNO > 0) { return 0; } 
		//Rezultat je ali -countCRNO, ali countBELO 
		else if(jaz == Igralec.BEL) { return count_BELO - count_CRNO; }
		//Rezultat je ali -countBELO, ali countCRNO
		else { return count_CRNO - count_BELO; }
	}
	

}


