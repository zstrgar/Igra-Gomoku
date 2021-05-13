package inteligenca;

import logika.Igra;
import logika.Igralec;
import logika.Polje;
import logika.Vrsta;

public class OceniPozicijo {
	
	// Metoda oceniPozicijo za igro TicTacToe
	
	public static int oceniPozicijo(Igra igra, Igralec jaz) {
		int ocena = 0;
		for (Vrsta vrsta : Igra.VRSTE) {
			ocena = ocena + oceniVrsto(vrsta, igra, jaz);
		}
		return ocena;	
	}
	
	public static int oceniVrsto (Vrsta vrsta, Igra igra, Igralec jaz) {
		Polje[][] plosca = igra.getPlosca();
		int count_CRNO = 0;
		int count_BELO = 0;
		for (int k = 0; k < Igra.N && (count_CRNO == 0 || count_BELO == 0); k++) {
			switch (plosca[vrsta.x[k]][vrsta.y[k]]){
			case BELO: count_BELO += 1; break;
			case CRNO: count_CRNO += 1; break;
			case PRAZNO: break;
			}
		}
		if (count_BELO > 0 && count_CRNO > 0) { return 0; }
		else if(jaz == Igralec.BEL) { return count_BELO - count_CRNO; }
		else { return count_CRNO - count_BELO; }
	}
	

}


