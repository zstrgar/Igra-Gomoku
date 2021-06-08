package logika;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import splosno.Koordinati;


public class Igra {

  //Velikost igralne plošče
  public static int N = 15;

  //Koliko žetonov želimo imeti v vrsti, da zmagamo
  public static int M = 5;
  
  //Nastavimo okolico, kjer iščemo možne poteze (2 - gleda okolico za 2 stran od zapolnjenih polj)
  private static final int OKOLICA_MOZNE_POTEZE = 2;

  //Igralna plosca, ki sestoji iz polj(prazno, crno, belo)
  private Polje[][] plosca;

  //Igralec (crn, bel), ki je trenutno na potezi
  public Igralec igralecNaPotezi;

  // Pomožen seznam vseh vrst na plošči.
  public static List<Vrsta> VRSTE;

  public LinkedList<Koordinati> odigranePoteze;


  public void zazeniPlosco() {
    // Ta koda se izvede na začetku, ko se prvič požene program.
    // Njena naloga je, da prepozna vrednosti statičnih
    // spremenljivk.
    VRSTE = new LinkedList<Vrsta>();
    
    // Prepoznavanje, če smo dobili M v vrsto.
    int[][] mozneSmeri = {{1, 0}, {0, 1}, {1, 1},
        {1, -1}};  //smer {1,0} -> desno, {0,1} -> gor, {1,1} -> desno gor, {1, -1} -> desno dol
    for (int x = 0; x < N; x++) {
      for (int y = 0; y < N; y++) {
        for (int[] smer : mozneSmeri) {
          int dx = smer[0];  //premik v x smeri (desno, levo)
          int dy = smer[1];  //premik v y smeri (gor,dol)
          // Če je skrajno polje terice že na plošči, jo dodamo med terice
          if ((0 <= x + (M - 1) * dx) && (x + (M - 1) * dx < N) &&
              (0 <= y + (M - 1) * dy) && (y + (M - 1) * dy < N)) {
            int[] vrsta_x = new int[M];      //dolžina seznama je M, ker igramo M v vrsto
            int[] vrsta_y = new int[M];
            for (int k = 0; k < M; k++) {
              vrsta_x[k] = x + dx * k;
              vrsta_y[k] = y + dy * k;
            }
            VRSTE.add(new Vrsta(vrsta_x, vrsta_y));
          }
        }
      }
    }
  }


  /**
   * Nova igra, na začetku je prazna in na potezi je BEL.
   */
  public Igra() {          				//konstruktor
    zazeniPlosco();
    plosca = new Polje[N][N];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        plosca[i][j] = Polje.PRAZNO;
      }
    }
    igralecNaPotezi = Igralec.BEL;
    this.odigranePoteze = new LinkedList<Koordinati>();
  }

  public Igra(int n, int m) {          //konstruktor
    N = n;
    M = m;
    zazeniPlosco();
    plosca = new Polje[N][N];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        plosca[i][j] = Polje.PRAZNO;
      }
    }
    igralecNaPotezi = Igralec.BEL;
    this.odigranePoteze = new LinkedList<Koordinati>();
  }

  public Igra(Igra igra) {    		// ustvarimo kopijo igre!
    this.plosca = new Polje[N][N];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        this.plosca[i][j] = igra.plosca[i][j];
      }
    }
    this.igralecNaPotezi = igra.igralecNaPotezi;
    this.odigranePoteze = new LinkedList<Koordinati>();
    for (Koordinati p : igra.odigranePoteze) this.odigranePoteze.add(p);
  }


  public Polje[][] getPlosca() {
    return plosca;
  }


  public Igralec igralecNaPotezi() {
    return igralecNaPotezi;
  }

  /**
   * metoda moznePoteze() vrne množico Set<Koordinati> moznih potez omejene na oklico že odigranih potez
   * @return moznePoteze
   */
  public Set<Koordinati> moznePoteze() {
	    Set<Koordinati> vseMoznePoteze = new HashSet<>();
	    Set<Koordinati> mozneSosednePoteze = new HashSet<>();

	    for (int i = 0; i < N; i++) {
	      for (int j = 0; j < N; j++) {
	        if (plosca[i][j] == Polje.PRAZNO) {
	          vseMoznePoteze.add(new Koordinati(i, j));
	        }

	        if (plosca[i][j] != Polje.PRAZNO) {
	          for (int dx = -OKOLICA_MOZNE_POTEZE; dx <= OKOLICA_MOZNE_POTEZE; dx++) {
	            for (int dy = -OKOLICA_MOZNE_POTEZE; dy <= OKOLICA_MOZNE_POTEZE; dy++) {

	              Koordinati kandidatniKoordinati = new Koordinati(i + dx, j + dy);

	              if (jeVeljavnaPoteza(kandidatniKoordinati)) {
	                mozneSosednePoteze.add(kandidatniKoordinati);
	              }
	            }
	          }
	        }
	      }
	    }
	    return (mozneSosednePoteze.isEmpty()) ? vseMoznePoteze : mozneSosednePoteze;
	  }

  /**
   * metoda cigavaVrsta(Vrsta vrsta) vrne igralca, ki ima zmagovalno vrsto (M v Vrsto)
   * @param Vrsta vrsta
   * @return Igralec (BEL ali CRN) ali null, ce ni zmagovalne vrste
   */
  private Igralec cigavaVrsta(Vrsta vrsta) {
    int count_BELO = 0;
    int count_CRNO = 0;
    for (int k = 0; k < M && (count_BELO == 0 || count_CRNO == 0); k++) {
      switch (plosca[vrsta.x[k]][vrsta.y[k]]) {
        case BELO:
          count_BELO += 1;
          break;
        case CRNO:
          count_CRNO += 1;
          break;
        case PRAZNO:
          break;
      }
    }
    if (count_BELO == M) {
      return Igralec.BEL;
    } else if (count_CRNO == M) {
      return Igralec.CRN;
    } else {
      return null;
    }
  }

  /**
   * metoda zmagovalnaVrsta() vrne zmagovalno vrsto (ce jo nek igralec ima)
   * @return zmagovalna vrsta, ali {@null}, ce je ni
   */
  public Vrsta zmagovalnaVrsta() {
    for (Vrsta vrsta : VRSTE) {
      Igralec lastnik = cigavaVrsta(vrsta);
			if (lastnik != null) {
				return vrsta;
			}
    }
    return null;
  }


  /**
   * metoda Stanje() vrne trenutno stanje igre (ZMAGA, NEODLOCENO, V_TEKU)
   * @return trenutno stanje igre
   */
  public Stanje stanje() {
    // Ali imamo zmagovalca?
    Vrsta vrsta = zmagovalnaVrsta();
    if (vrsta != null) {
      switch (plosca[vrsta.x[0]][vrsta.y[0]]) {
        case BELO:
          return Stanje.ZMAGA_BEL;
        case CRNO:
          return Stanje.ZMAGA_CRN;
        case PRAZNO:
          assert false;
      }
    }
    // Ali imamo kaksno prazno polje?
    // ce ga imamo, igre ni konec in je nekdo na potezi
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
				if (plosca[i][j] == Polje.PRAZNO) {
					return Stanje.V_TEKU;
				}
      }
    }
    // Polje je polno, rezultat je neodlocen
    return Stanje.NEODLOCENO;
  }


  /**
   * metoda veljavnostPoteze(Koordinati poteza) preveri, ce je poteza veljavna, preveri, ce je polje
   * prazno in ce sta koordinati X in Y med 0 in N
   * @param poteza
   * @return true ce je veljavna, false, ce ni
   */
  public boolean jeVeljavnaPoteza(Koordinati poteza) { 
		if (poteza.getX() < 0 || poteza.getX() >= N) {
			return false;
		}
		if (poteza.getY() < 0 || poteza.getY() >= N) {
			return false;
		}
		if (plosca[poteza.getX()][poteza.getY()] != Polje.PRAZNO) {
			return false;
		}
    return true;
  }


  /**
   * metoda odigraj(Koordinati poteza) najprej preveri, ce je poteza veljavna ce je, spremeni
   * vrednost igralca na potezi ter ga nastavi na nasprotnika
   * @param poteza
   * @return true, ce je bila poteza odigrana, false - ce ni
   */
  public boolean odigraj(Koordinati poteza) {
    if (this.jeVeljavnaPoteza(poteza)) {
      plosca[poteza.getX()][poteza.getY()] = igralecNaPotezi.getPolje();
      // Potezo dodamo v odigranePoteze.
			this.odigranePoteze.add(poteza);
      igralecNaPotezi = igralecNaPotezi.nasprotnik();
      return true;
    } else return false;
  }


  /**
   * razveljavi zadnjo potezo
   * @return
   */
	public boolean razveljaviPotezo() {
		// if (lahko razveljavimo potezo)
		if ((this.odigranePoteze.size() >= 1) && (this.stanje() == Stanje.V_TEKU)) {
			// odstranimo zadnjo potezo in jo shranimo v spremenljivko
			Koordinati zadnjaPoteza = this.odigranePoteze.removeLast();
			// ustrezno polje na plošči nastavimo na prazno
			this.plosca[zadnjaPoteza.getX()][zadnjaPoteza.getY()] = Polje.PRAZNO;
			// zamenjamo igralca na potezi.
			this.igralecNaPotezi = this.igralecNaPotezi.nasprotnik();
			return true;
		}
		// nobena poteza še ni bila odigrana
		return false;
	}
		

}
