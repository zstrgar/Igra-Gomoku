package vodja;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import logika.Igra;
import logika.Igralec;
import splosno.Koordinati;

import javax.swing.SwingWorker;

import gui.GlavnoOkno;
import inteligenca.Inteligenca;
import inteligenca.Minimax;


public class Vodja {
	
	// private static enum VrstaIgralca{ RACUNALNIK, CLOVEK; }
	
	// private static BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
	
	public static Map<Igralec, VrstaIgralca> vrstaIgralca;
		
	public static GlavnoOkno okno;
	
	public static Igra igra = null;
	
	public static boolean clovekNaVrsti = false;
		
	public static void igramoNovoIgro () {
		igra = new Igra ();
		igramo ();
	}
	


	public static void igramo () {
		okno.osveziGUI();
		switch (igra.stanje()) {
		case ZMAGA_CRN: 
		case ZMAGA_BEL: 
		case NEODLOCENO: 
			return; // odhajamo iz metode igramo
		case V_TEKU: 
			Igralec igralec = igra.igralecNaPotezi;
			VrstaIgralca vrstaNaPotezi = vrstaIgralca.get(igralec);
			switch (vrstaNaPotezi) {
			case C: 
				clovekNaVrsti = true;
				break;
			case R:
				racunalnikovaPoteza();
				break;
			}
		}
	}
	
	private static Random random = new Random ();
	
	private static Inteligenca racunalnikovaInteligenca = new Minimax(3);

	// swing worker
	public static void racunalnikovaPoteza() {
		Igra zacetnaIgra = igra;
		SwingWorker<Koordinati, Void> worker = new SwingWorker<Koordinati, Void> () {
			@Override
			protected Koordinati doInBackground() {
				Koordinati poteza = racunalnikovaInteligenca.izberiPotezo(igra);
				try {TimeUnit.SECONDS.sleep(2);} catch (Exception e) {};
				// List<Koordinati> moznePoteze = igra.moznePoteze();        // tole se nuca za "algoritem" random, ko rač. random izbira svoje poteze
				//int randomIndex = random.nextInt(moznePoteze.size());
				//return moznePoteze.get(randomIndex);
				return poteza;
			}
			@Override
			protected void done () {
				Koordinati poteza = null;
				try {poteza = get();} catch (Exception e) {};
				if (igra == zacetnaIgra) {
					igra.odigraj(poteza);
					igramo ();
				}
			}
		};
		worker.execute();
	}

	public static void clovekovaPoteza(Koordinati poteza) {
		if (igra.odigraj(poteza)) clovekNaVrsti = false;
		igramo ();
	}


}
