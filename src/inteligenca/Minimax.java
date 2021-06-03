package inteligenca;

import java.util.Set;

import logika.Igra;
import logika.Igralec;
import splosno.Koordinati;

public class Minimax extends Inteligenca {

  private static final int ZMAGA = 100; // vrednost zmage
  private static final int PORAZ = -ZMAGA;  // vrednost poraza
  private static final int NEODLOC = 0;  // vrednost neodločene igre

  private int globina;

  public Minimax(int globina) {
    super(2);
    this.globina = globina;
  }

  @Override
  public Koordinati izberiPotezo(Igra igra) {
    OcenjenaPozicija najboljsaPoteza = minimax(igra, this.globina, igra.igralecNaPotezi());
    return najboljsaPoteza.poteza;
  }

  /**
   * Vrne najboljso ocenjeno potezo z vidika igralca jaz
   *
   * @param igra
   * @param globina
   * @param jaz     (igralec)
   * @return najboljsa poteza skupaj z oceno poteze
   */
  public OcenjenaPozicija minimax(Igra igra, int globina, Igralec jaz) {
    // Na začetku še ni najboljše poteze.
    OcenjenaPozicija najboljsaPoteza = null;

    //Med vsemi možnimi potezami iščemo najboljšo.
    Set<Koordinati> moznePoteze = igra.moznePoteze();
    for (Koordinati p : moznePoteze) {
      // Ustvarimo kopijo igre na kateri igramo in poiskušamo najti najboljšo potezo.
      Igra kopijaIgre = new Igra(igra);
      kopijaIgre.odigraj(p);

      int ocena;

      // Ugotovimo, ali je konec, ali je kdo na potezi.
      switch (kopijaIgre.stanje()) {
        case ZMAGA_BEL:
          ocena = (jaz == Igralec.BEL ? ZMAGA : PORAZ);
          break;
        case ZMAGA_CRN:
          ocena = (jaz == Igralec.CRN ? ZMAGA : PORAZ);
          break;
        case NEODLOCENO:
          ocena = NEODLOC;
          break;
        default:
          // Nekdo je na potezi.
          if (globina == 1) {
            OcenjevalecPozicije ocenjevalecPozicije = new OcenjevalecPozicije();
            ocena = ocenjevalecPozicije.oceniPozicijo(kopijaIgre, jaz);
          }
          // Če je globina > 1, rekurzivno naredimo minimax.
          else {
            ocena = minimax(kopijaIgre, globina - 1, jaz).ocena;
          }
      }

      // Če ni kandidata za najboljšo potezo:
      if (najboljsaPoteza == null
          // maximiziramo, če je p moja poteza
          || jaz == igra.igralecNaPotezi() && ocena > najboljsaPoteza.ocena
          // sicer minimiziramo
          || jaz != igra.igralecNaPotezi() && ocena < najboljsaPoteza.ocena) {
        najboljsaPoteza = new OcenjenaPozicija(p, ocena);
      }
    }
    return najboljsaPoteza;
  }
}
