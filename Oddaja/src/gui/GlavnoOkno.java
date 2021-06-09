package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import logika.Igralec;
import vodja.Vodja;
import vodja.VrstaIgralca;


@SuppressWarnings("serial")
public class GlavnoOkno extends JFrame implements ActionListener {
	/**
	 * JPanel
	 */
	protected IgralnoPolje polje;

	
	//Statusna vrstica v spodnjem delu okna
	private JLabel status;
	
	// Izbire v menuju
	
	// nastavitev igralca (lahko je človek, računlanik z random potezami - level1, minimax alg. - level 2, alphabeta alg. - level 3)
	private JMenuItem menuIgralec1Clovek, menuIgralec1Level1, menuIgralec1Level2, menuIgralec1Level3;
	private JMenuItem menuIgralec2Clovek, menuIgralec2Level1, menuIgralec2Level2, menuIgralec2Level3;
	
	// nastavitve
	private JMenuItem menuVelikostIgre, menuImeIgralca1, menuImeIgralca2; 
	
	//hitrost
	private JMenuItem menuHitrostPocasna, menuHitrostZmerna, menuHitrostHitra;
	
	// barve
	private JMenuItem menuBarvaZetonov1,menuBarvaZetonov2, menuBarvaPolja;
	
	// imena igralcev
	private String igralec1 = "BEL";
	private String igralec2 = "ČRN";

	//gumba
	private JButton gumbZacni;
	private JButton gumbRazveljavi;

	
	/**
	 * Ustvari novo glavno okno in prični igrati igro.
	 */
	public GlavnoOkno() {
		
		this.setTitle("Gomoku");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
	
		// menu
		JMenuBar menu_bar = new JMenuBar();
		setJMenuBar(menu_bar);

		JMenu menuNastavitve = dodajMenu(menu_bar, "  Nastavitve  ");

		menu_bar.add(Box.createHorizontalGlue());
		//drop down menuji
		JMenu menuIgralec1 = dodajMenu(menu_bar, " 1. igralec (BEL) ");
		JMenu menuIgralec2 = dodajMenu(menu_bar, " 2. igralec (ČRN) ");
		
		gumbZacni = dodajGumb(menu_bar, "Začni igro");
		gumbRazveljavi = dodajGumb(menu_bar, "Razveljavi");

		
				
		// dodajamo moznosti na menuIgralec1
		menuIgralec1Clovek = dodajMenuItem(menuIgralec1, "Človek");
		menuIgralec1Level1 = dodajMenuItem(menuIgralec1, "Računalnik - level 1");
		menuIgralec1Level2 = dodajMenuItem(menuIgralec1, "Računalnik - level 2");
		menuIgralec1Level3 = dodajMenuItem(menuIgralec1, "Računalnik - level 3");
		
		// dodajamo moznosti na menuIgralec1
		menuIgralec2Clovek = dodajMenuItem(menuIgralec2, "Človek");
		menuIgralec2Level1 = dodajMenuItem(menuIgralec2, "Računalnik - level 1");
		menuIgralec2Level2 = dodajMenuItem(menuIgralec2, "Računalnik - level 2");
		menuIgralec2Level3 = dodajMenuItem(menuIgralec2, "Računalnik - level 3");
		
		//dodajamo moznosti na menu Hitrost igre
		JMenu menuHitrostIgre = dodajMenuVMenu(menuNastavitve, "Hitrost igre");
		menuHitrostPocasna = dodajMenuItem(menuHitrostIgre, "Počasna igra");
		menuHitrostZmerna = dodajMenuItem(menuHitrostIgre, "Zmerna igra");
		menuHitrostHitra = dodajMenuItem(menuHitrostIgre, "Hitra igra");
		
		// dodajamo moznosti na menuNastavitve
		menuVelikostIgre = dodajMenuItem(menuNastavitve, "Nastavi velikost polja...");
		
		// dodajamo moznosti na menu imena igralcev
		JMenu menuImenaIgralcev = dodajMenuVMenu(menuNastavitve, "Nastavi imena igralcev");
		menuImeIgralca1 = dodajMenuItem(menuImenaIgralcev, "Ime 1. igralca...");
		menuImeIgralca2 = dodajMenuItem(menuImenaIgralcev, "Ime 2. igralca...");
		
		// dodajamo moznosti na menuBarve
		JMenu menuBarve = dodajMenuVMenu(menuNastavitve, "Barve");
		menuBarvaZetonov1 = dodajMenuItem(menuBarve, "Barva žetonov 1. igralca...");
		menuBarvaZetonov2 = dodajMenuItem(menuBarve, "Barva žetonov 2. igralca...");
		menuBarvaPolja = dodajMenuItem(menuBarve, "Barva ozadja...");

		
		// igralno polje
		polje = new IgralnoPolje();

		GridBagConstraints polje_layout = new GridBagConstraints();
		polje_layout.gridx = 0;
		polje_layout.gridy = 0;
		polje_layout.anchor = GridBagConstraints.CENTER;
		polje_layout.fill = GridBagConstraints.BOTH;
		polje_layout.weightx = 1.0;	//ko je na 0, je centriran; ko je na 1 se povečuje z oknom
		polje_layout.weighty = 1.0;	
		getContentPane().add(polje, polje_layout);
		
		// statusna vrstica za sporočila
		status = new JLabel();
		status.setFont(new Font(status.getFont().getName(), status.getFont().getStyle(), 20));
		GridBagConstraints status_layout = new GridBagConstraints();
		status_layout.gridx = 0;
		status_layout.gridy = 1;
		status_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status, status_layout);
		
		status.setText("Določi igralca!");
		
	}
	
	/**
	 * Pomožne metode dodajMenu in dodajMenuItem za konstruiranje menuja
	 * @param menubar
	 * @param naslov
	 * @return JMenu
	 */
	public JMenu dodajMenu(JMenuBar menu_bar, String naslov) {
		JMenu menu = new JMenu(naslov);
		menu_bar.add(menu);
		return menu;
	}
	
	public JMenuItem dodajMenuItem(JMenu menu, String naslov) {
		JMenuItem menuitem = new JMenuItem(naslov);
		menu.add(menuitem);
		menuitem.addActionListener(this);
		return menuitem;		
	}
	
	public JMenu dodajMenuVMenu(JMenu menu_kam, String naslov) {
		JMenu menu = new JMenu(naslov);
		menu_kam.add(menu);
		return menu;
	}
	private JButton dodajGumb(JMenuBar menu_bar, String napis) {
		JButton gumb = new JButton(napis);
		gumb.addActionListener(this);
		menu_bar.add(gumb);
		return gumb;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		VrstaIgralca vrstaIgralca1 = null;
		VrstaIgralca vrstaIgralca2 = null;
		

		//nastavimo 1. igralca (belega)
		if (source == menuIgralec1Clovek) {
			vrstaIgralca1 = VrstaIgralca.C;
			Vodja.vrstaIgralca.put(Igralec.BEL, vrstaIgralca1); 
			preimenuj(true, "Človek");
		} else if (source == menuIgralec1Level1) {
			vrstaIgralca1 = VrstaIgralca.R1;
			Vodja.vrstaIgralca.put(Igralec.BEL, vrstaIgralca1); 
			preimenuj(true, "Naključna poteza");
		} else if (source == menuIgralec1Level2) {
			vrstaIgralca1 = VrstaIgralca.R2;
			Vodja.vrstaIgralca.put(Igralec.BEL, vrstaIgralca1); 
			preimenuj(true, "Random MiniMax");
		} else if (source == menuIgralec1Level3) {
			vrstaIgralca1 = VrstaIgralca.R3;
			Vodja.vrstaIgralca.put(Igralec.BEL, vrstaIgralca1); 
			preimenuj(true, "AlphaBeta");
		
		// nastavimo 2.igralca (črnega)
		} else if (source == menuIgralec2Clovek) {
			vrstaIgralca2 = VrstaIgralca.C;
			Vodja.vrstaIgralca.put(Igralec.CRN, vrstaIgralca2);
			preimenuj(false, "Človek");
		} else if (source == menuIgralec2Level1) {
			vrstaIgralca2 = VrstaIgralca.R1; 
			Vodja.vrstaIgralca.put(Igralec.CRN, vrstaIgralca2);
			preimenuj(false, "Naključna poteza");
		} else if (source == menuIgralec2Level2) {
			vrstaIgralca2 = VrstaIgralca.R2; 
			Vodja.vrstaIgralca.put(Igralec.CRN, vrstaIgralca2);
			preimenuj(false, "Random MiniMax");
		} else if (source == menuIgralec2Level3) {
			vrstaIgralca2 = VrstaIgralca.R3;
			Vodja.vrstaIgralca.put(Igralec.CRN, vrstaIgralca2);	
			preimenuj(false, "AlphaBeta");
		} 
		
		//nastavimo Velikost igre	
		else if (source == menuVelikostIgre) {
			int n = 15;
			String i = JOptionPane.showInputDialog("Napišite velikost polja N");
			if (i!=null && !i.equals("")) {
				try {
					if (Integer.parseInt(i) > 2) {
						n = Integer.parseInt(i);
					}
					else {JOptionPane.showMessageDialog(this, "Neveljavna izbira!");}		   
				}
				catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, "Neveljavna izbira!");
				}
			}
			String j = JOptionPane.showInputDialog("Napišite koliko v vrsto");
			if (j!=null && !j.equals("")) {
				try {
					if (2<Integer.parseInt(j) && Integer.parseInt(j) <= n) {
						int m = Integer.parseInt(j);
						Vodja.igramoNovoIgro(n, m);
					}
					else {JOptionPane.showMessageDialog(this, "Neveljavna izbira!");}		   
				}
				catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, "Neveljavna izbira!");
				}
			}
			
		// nastavimo imena igralcev		
		} else if (source == menuImeIgralca1) {
			String i = JOptionPane.showInputDialog("Spremenite ime 1. igralca");
			preimenuj(true, i);
		} else if (source == menuImeIgralca2) {
			String i = JOptionPane.showInputDialog("Spremenite ime 2. igralca");
			preimenuj(false, i);
		}
		
		// nastavimo hitrost igre	
		else if (source == menuHitrostPocasna) {
			Vodja.zamikRacunalnikovePoteze = 5;
		}  else if (source == menuHitrostZmerna) {
			Vodja.zamikRacunalnikovePoteze = 2;
		}  else if (source == menuHitrostHitra) {
			Vodja.zamikRacunalnikovePoteze = 0;
		

		} else if (source == gumbRazveljavi) {
			boolean bool1 = false;
			boolean bool2 = false;
			
			if (Vodja.igra == null || Vodja.vrstaIgralca == null) {
				this.status.setText("Najprej izberite željeno igro!");
			} else if (Vodja.vrstaIgralca.get(Igralec.BEL) == VrstaIgralca.C
					&& Vodja.vrstaIgralca.get(Igralec.CRN) == VrstaIgralca.C) {
				// ko igrata dva cloveka razveljavimo le potezo zadnjega
				bool1 = Vodja.igra.razveljaviPotezo();
				if (bool1) this.status.setText("Poteza uspešno razveljavljena.");
				else this.status.setText("Poteza ni bila razveljavljena. ");
			} else if (Vodja.vrstaIgralca.get(Igralec.BEL) != null
					&& Vodja.vrstaIgralca.get(Igralec.CRN) == VrstaIgralca.C
					&& Vodja.igra.igralecNaPotezi() == Igralec.CRN) {
				// ko igrata clovek racunalnik razveljavimo od obeh
				bool1 = Vodja.igra.razveljaviPotezo();
				bool2 = Vodja.igra.razveljaviPotezo();
				if (bool1 && bool2) this.status.setText("Potezi razveljavljeni. ");
				else this.status.setText("Potezi nista bili razveljavljeni. ");
			} else if (Vodja.vrstaIgralca.get(Igralec.BEL) == VrstaIgralca.C
					&& Vodja.vrstaIgralca.get(Igralec.CRN) != null
					&& Vodja.igra.igralecNaPotezi() == Igralec.BEL) {
				// ko igrata clovek racunalnik razveljavimo od obeh
				bool1 = Vodja.igra.razveljaviPotezo();
				bool2 = Vodja.igra.razveljaviPotezo();
				if (bool1 && bool2) this.status.setText("Potezi razveljavljeni. ");
				else this.status.setText("Potezi nista bili razveljavljeni. ");
			}  else if (Vodja.vrstaIgralca.get(Igralec.BEL) != VrstaIgralca.C 
						&& Vodja.vrstaIgralca.get(Igralec.CRN) != VrstaIgralca.C) { 
				this.status.setText("Ko igrata računalnika, potez ne morete razveljaviti");
			}
			
			this.polje.repaint();
			
		// nastavimo barve
		}else if (source == menuBarvaZetonov1) {
			Color novaBarva = JColorChooser.showDialog(this, "Izberite barvo", polje.barvaIgralca1);
			polje.barva1(novaBarva);
		} else if (source == menuBarvaZetonov2) {
			Color novaBarva = JColorChooser.showDialog(this, "Izberite barvo", polje.barvaIgralca2);
			polje.barva2(novaBarva);
		} else if (source == menuBarvaPolja) {
			Color novaBarva = JColorChooser.showDialog(this, "Izberite barvo", polje.barvaOzadja);
			polje.pobarvajOzadje(novaBarva);			
		}
		
		//s klikom na gumbZacni (Začni igro) se preveri, ali sta oba igralca izbrana
		else if(source == gumbZacni){
			if (!Vodja.vrstaIgralca.containsKey(Igralec.BEL) || !Vodja.vrstaIgralca.containsKey(Igralec.BEL)) {
				status.setText("Pozor! Nastavi vrsto obeh igralcev.");
			}
			else{Vodja.igramoNovoIgro();
			}
		}
	}


	/**
	 * Metoda za preimenovanje igralcev
	 */
	public void preimenuj(boolean b, String niz) {
		if (niz != null && !niz.equals("")) {
		if (b) {
			this.igralec1 = niz;
		}
		else this.igralec2 = niz;
		}
		osveziGUI();
	}

	public void osveziGUI() {
		if (Vodja.igra == null) {
			status.setText("Igra ni v teku.");
		}
		else {
			switch(Vodja.igra.stanje()) {
			case NEODLOCENO: status.setText("Neodločeno!"); break;
			case V_TEKU: 
				if (Vodja.vrstaIgralca == null) break;
				switch(Vodja.igra.igralecNaPotezi()) {
					case BEL:
						status.setText("Na potezi je " + "BEL" + " - " + igralec1); 
						break;
					case CRN:
						status.setText("Na potezi je " + "ČRN" + " - " + igralec2); 
						break;
				}
			break;
			case ZMAGA_BEL: 
				status.setText("Zmagal je BEL - " + 
						igralec1);
				break;
			case ZMAGA_CRN: 
				status.setText("Zmagal je ČRN - " + 
						igralec2);
				break;
			}
		}
		polje.repaint();
	}
	



}
