# Igra-Gomoku
## Projekt pri predmetu Programiranje 2
Avtorja: Gašper Kmetič in Žana Strgar

Igra za dva igralca je sestavljena iz plošče velikosti 15x15. Zmaga igralec, ki prvi postavi 5 žetonov v vrsto (ta je lahko horizontalna, vertikalna ali diagonalna). Več o igri in njenih izvedbah je dostopnega na: [Gomoku](https://en.wikipedia.org/wiki/Gomoku). 

Ideja za ocenjevanje in reduciranje moznih potez sloni na idejah, ki jih avtorja predstavita v članku [Five-In-Row with Local Evaluation and Beam Search](https://courses.cs.washington.edu/courses/cse573/04au/Project/mini1/JA/report.pdf).

Računalniški igralec izbira poteze s pomočjo naključnega algoritma, algoritma Minimax ali AlphaBeta algoritma.

## Navodila
Ob zagonu programa, uporabnik najprej nastavi vrsto obeh igralcev, nato igro začne s klikom na gumb `Začni igro`. Igro vedno začne 1. igralec (z belimi žetoni). Igralec je lahko človek ali računalnik. Nadalje uporabnik lahko igra igro proti računalniku na različnih nivojih. Najenostavnejši nivo je Računalnik - level 1 in je namenjen spoznavanju igre. Najtežji nivo je Računalnik - level 3, namenjen izkušnejšim igralcem. Za spoznavanje taktike lahko uporabnik spremlja igro med dvema računalniškima igralcema.

Uporabnik lahko v nastavitvah `Nastavitve` nastavi hitrost igre, velikost polja, imena igralcev, barvo žetonov in barvo polja.
