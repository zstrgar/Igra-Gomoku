package vodja;

public enum VrstaIgralca {
	R1, R2, R3, C; 

	@Override
	public String toString() {
		switch (this) {
		case C: return "človek";
		case R1: return "računalnik level 1";
		case R2: return "računalnik level 2";
		case R3: return "računalnik level 3";
		default: assert false; return "";
		}
	}

}
