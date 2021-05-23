package logika;

import java.util.Arrays;

/**
 * vrsta x oziroma y koordinat, tvori se po potezah posameznega igralca, rabimo jih da bomo
 * prepoznal, kdaj bo M v vrsto
 */

public class Vrsta {


  // Vrsta na plosci je predstavljena z dvema tabelama dolzine M.
  // To sta tabeli x in y koordinat.
  public int[] x;
  public int[] y;

  public Vrsta(int[] x, int y[]) {
    this.x = x;
    this.y = y;
  }


  /**
   * Metoda dolociSmer, doloci smer vrste na podlagi izračuna "smernega koeficienta" oz. razlike x
   * in y koordinat v vrsti smer je lahko horizontalna, vertikalna, diagonalna gor ali diag. dol
   */
  public int[] smerVrste() {
    int[] smer = new int[2];
    smer[0] = x[1] - x[0];
    smer[1] = y[1] - y[0];
    return smer;
  }


  @Override
  public String toString() {
    return "Vrsta [x=" + Arrays.toString(x) + ", y=" + Arrays.toString(y) + "]";
  }


}
