package inteligenca;

public class StatistikaPozicije {
	//stevec razlicnih pozicij zetonov (X moj zeton, - prazen, O zeton nasprotnika)

  int fiveInRow = 0;	// 5 zaporednih zetonov: XXXXX
  int straightFour = 0;	// 4 zaporedni zetoni z obema prostima koncema: -XXXX-
  int fourInRow = 0;	// 4 zaporedni zetoni z enim prostim koncem: OXXXX- ali -XXXXO
  int threeInRow = 0;	// 3 zaporedni zetoni z dvojnim protim koncem: --XXx--
  int brokenThree = 0;	// 3 zetoni med katerimi je en prazen:	-XX-X- ali -X-XX-
  int twoInRow = 0;		// 2 zaporedna zetona -XX-
  int single = 0;		// 1 sam zeton -X-

}
