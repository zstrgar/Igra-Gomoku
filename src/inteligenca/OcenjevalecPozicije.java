package inteligenca;

import logika.Igra;
import logika.Igralec;
import logika.Polje;
import logika.Vrsta;

public class OcenjevalecPozicije {
	
	
	// nastavimo vrednosti uteži, da igra ve, katere pozicije so več vredne
  private static final int UTEZ_FIVE_IN_ROW = 500;
  private static final int UTEZ_STRAIGHT_FOUR = 500;
  private static final int UTEZ_FOUR_IN_ROW = 100;
  private static final int UTEZ_THREE_IN_ROW = 80;
  private static final int UTEZ_BROKEN_THREE = 50;
  private static final int UTEZ_TWO_IN_ROW = 5;
  private static final int UTEZ_SINGLE = 1;

  private final StatistikaPozicije statistikaBeli = new StatistikaPozicije();
  private final StatistikaPozicije statistikaCrni = new StatistikaPozicije();

  public OcenjevalecPozicije() {

  }

  /**
   * Metoda oceniPozicijo oceni pozicijo igre igra za igralca jaz (po navadi je to igralec na
   * potezi), oceno dobimo tako, da seštejemo vse delne vrste za igralca jaz in odštejemo vse delne
   * vrste za igralca nasprotnika. 
   * @param igra
   * @param jaz  - igralec, ki želi oceno
   * @return ocena
   */
  public int oceniPozicijo(Igra igra, Igralec jaz) {
    for (Vrsta vrsta : Igra.VRSTE) {
      oceniVrsto(vrsta, igra, jaz);
    }
    //preverimo, kateri igralec je jaz in ustrezno odštejemo statistike
    if (jaz == Igralec.BEL) {
      return sestejOcene(statistikaBeli) - sestejOcene(statistikaCrni);
    } else {
      return sestejOcene(statistikaCrni) - sestejOcene(statistikaBeli);
    }
  }

  /**
   * Metoda oceniVrsto oceni določeno vrsto v igri igra z vidika igralca jaz.
   * @param vrsta - vrsta, ki jo ocenjujemo
   * @param igra
   * @param jaz   - igralec, ki želi oceno
   * @return 
   */
  private void oceniVrsto(Vrsta vrsta, Igra igra, Igralec jaz) {
    String nizVrsta = vrstaToString(vrsta, igra);

    //oceni igralec beli
    // preštej five in row
    if (nizVrsta.contains("BBBBB")) statistikaBeli.fiveInRow++;

    //preštej three-in-row
    if (nizVrsta.contains("PBBBP")) statistikaBeli.threeInRow++;
    

    // preštej črni five-in-row
    if (nizVrsta.contains("CCCCC")) statistikaCrni.fiveInRow++;

    //preštej črni three-in-row
    if (nizVrsta.contains("PCCCP")) statistikaCrni.threeInRow++;
    

    Vrsta razsirjenaVrsta = razsiriVrsto(vrsta);

    if (razsirjenaVrsta != null) {
      String nizRazsirjenaVrsta = vrstaToString(razsirjenaVrsta, igra);

      //beli
      // preštej, če ima beli straight four
      if (nizRazsirjenaVrsta.contains("PBBBBP")) statistikaBeli.straightFour++;
      

      //preštej, če ima beli four in row
      if (nizRazsirjenaVrsta.contains("PBBBBC") || nizRazsirjenaVrsta.contains("CBBBBP")) {
        statistikaBeli.fourInRow++;
      }

      //preštej, če ima beli broken three
      if (nizRazsirjenaVrsta.contains("PBBPBP") || nizRazsirjenaVrsta.contains("PBPBBP")) {
        statistikaBeli.brokenThree++;
      }

      // preštej, če imam beli two in row
      statistikaBeli.twoInRow =
          statistikaBeli.twoInRow + countSubstring("BBP", nizRazsirjenaVrsta);

      // preštej, če ima beli one in row
      statistikaBeli.single = statistikaBeli.single + countSubstring("BP", nizRazsirjenaVrsta);

      //ČRNI
      // preštej, če ima črni straight four
      if (nizRazsirjenaVrsta.contains("PCCCCP")) statistikaCrni.straightFour++;

      //preštej, če ima črni four in row
      if (nizRazsirjenaVrsta.contains("PCCCCB") || nizRazsirjenaVrsta.contains("BCCCCP")) {
        statistikaCrni.fourInRow++;
      }

      //preštej, če imam črni broken three
      if (nizRazsirjenaVrsta.contains("PCCPCP") || nizRazsirjenaVrsta.contains("PCPCCP")) {
        statistikaCrni.brokenThree++;
      }

      // preštej, če ima črni two in row
      statistikaCrni.twoInRow =
          statistikaCrni.twoInRow + countSubstring("CCP", nizRazsirjenaVrsta);

      // preštej, če ima črni one in row
      statistikaCrni.single =
          statistikaCrni.single + countSubstring("CP", nizRazsirjenaVrsta);
    }
  }
  
  
  /**
   * Metoda razsiriVrsto dano vrsto razširi v njeni smeri (razsiri jo za eno polje)
   * @param vrsta
   * @return razsirjena vrsta, če jo lahko razširi, sicer vrne null
   */
  private Vrsta razsiriVrsto(Vrsta vrsta) {
	  // preberemo smer dane vrste
	  int[] smerVrste = vrsta.smerVrste();
	  int dx = smerVrste[0];
	  int dy = smerVrste[1];

	  int povecanX = vrsta.x[vrsta.x.length - 1] + dx;
	  int povecanY = vrsta.y[vrsta.y.length - 1] + dy;

	  if ((0 <= povecanX && povecanX < Igra.N) && (0 <= povecanY && povecanY < Igra.N)) {

		  int[] razsirjeniX = new int[vrsta.x.length + 1];
		  int[] razsirjeniY = new int[vrsta.y.length + 1];

		  System.arraycopy(vrsta.x, 0, razsirjeniX, 0, vrsta.x.length);
		  razsirjeniX[razsirjeniX.length - 1] = povecanX;

		  System.arraycopy(vrsta.y, 0, razsirjeniY, 0, vrsta.y.length);
		  razsirjeniY[razsirjeniY.length - 1] = povecanY;

		  return new Vrsta(razsirjeniX, razsirjeniY);
	  }
	  return null;
  	}

  /**
   * Metoda countSubstring prejme podniz substring, niza string in vrne število podnizov v nizu.
   * @param substring
   * @param string
   * @return število podnizov v nizu
   */
  private int countSubstring(String substring, String string) {

    int count = 0;
    for (int i = 0; i < string.length(); i++) {
      if (string.substring(i).startsWith(substring)) {
        count++;
      }
    }
    return count;
  }

  /**
   * Metoda vrstaToString vrsto v igri pretvori v niz, ki je sestavljen iz znakov P, B in C.
   * Znak P označuje prazno polje, C - crno polje, B-belo polje
   * @param vrsta
   * @param igra
   * @return niz z znaki P, B, C
   */
  private String vrstaToString(Vrsta vrsta, Igra igra) {
    String niz = "";

    Polje[][] plosca = igra.getPlosca();

    for (int k = 0; k < vrsta.x.length; k++) {
      switch (plosca[vrsta.x[k]][vrsta.y[k]]) {
        case BELO:
          niz += "B";
          break;
        case CRNO:
          niz += "C";
          break;
        case PRAZNO:
          niz += "P";
          break;
      }
    }
    return niz;
  }

  /**
   * Metoda sestejOcene sesteje število različnih pozicij in jih primerno uteži, 
   * vrne oceno s strani igralca, ki želi pogledati svojo statistiko
   * @param statistikaPozicije
   * @return skupna ocena
   */
  private int sestejOcene(StatistikaPozicije statistikaPozicije) {
    return UTEZ_FIVE_IN_ROW * statistikaPozicije.fiveInRow
        + UTEZ_STRAIGHT_FOUR * statistikaPozicije.straightFour
        + UTEZ_FOUR_IN_ROW * statistikaPozicije.fourInRow
        + UTEZ_THREE_IN_ROW * statistikaPozicije.threeInRow
        + UTEZ_BROKEN_THREE * statistikaPozicije.brokenThree
        + UTEZ_TWO_IN_ROW * statistikaPozicije.twoInRow
        + UTEZ_SINGLE * statistikaPozicije.single;
  }

}