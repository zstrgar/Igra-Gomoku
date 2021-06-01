package vodja;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingWorker;

import gui.GlavnoOkno;
import inteligenca.Inteligenca;
import logika.Igra;
import logika.Igralec;
import splosno.Koordinati;


public class Vodja {
	
	// private static enum VrstaIgralca{ RACUNALNIK, CLOVEK; }
	
	// private static BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
	
	public static Map<Igralec, VrstaIgralca> vrstaIgralca = null;
		
	public static GlavnoOkno okno;
	
	public static Igra igra = null;
	
	public static boolean clovekNaVrsti = false;

	public static int zamikRacunalnikovePoteze = 0;
		
	public static void igramoNovoIgro () {
		igra = new Igra ();
		igramo ();
	}
	
	public static void igramoNovoIgro (int n, int m) {
		igra = new Igra (n, m);
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
			// preverimo ce so ze izbrani igralci
			if (Vodja.vrstaIgralca==null) {
				// TODO: debug lahko zbrises print
				System.out.println("Izberi igro");
				break;
			}
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
	
	// private static Random random = new Random ();
	
	public static Inteligenca racunalnikovaInteligenca = new Inteligenca();

	// swing worker
	public static void racunalnikovaPoteza() {
		Igra zacetnaIgra = igra;
		SwingWorker<Koordinati, Void> worker = new SwingWorker<Koordinati, Void> () {
			@Override
			protected Koordinati doInBackground() {
				Koordinati poteza = racunalnikovaInteligenca.izberiPotezo(igra);
				try {TimeUnit.SECONDS.sleep(zamikRacunalnikovePoteze);} catch (Exception e) {};
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
