package logika;

import java.util.Arrays;

public class Vrsta {
	
	// Vrsta na plo��i je predstavljena z dvema tabelama dol�ine N=15.
		// To sta tabeli x in y koordinat.
	public int[] x;
	public int[] y;
		
	public Vrsta(int[] x, int y[]) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "Vrsta [x=" + Arrays.toString(x) + ", y=" + Arrays.toString(y) + "]";
	}
	
	

}
