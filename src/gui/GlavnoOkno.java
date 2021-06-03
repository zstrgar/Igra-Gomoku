package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumMap;

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


//@SuppressWarnings("serial")
public class GlavnoOkno extends JFrame implements ActionListener {
	/**
	 * JPanel
	 */
	protected IgralnoPolje polje;

	
	//Statusna vrstica v spodnjem delu okna
	private JLabel status;
	
	// Izbire v menuju
	// kdo igra s kom
	private JMenuItem menuClovekRacunalnik, menuRacunalnikClovek, menuClovekClovek, menuRacunalnikRacunalnik;
	// nastavitve
	private JMenuItem menuVelikostIgre, menuImeIgralca1, menuImeIgralca2, menuZamikRacunalnikovePoteze;
	// inteligence
	private JMenuItem menuAlfaBeta, menuMiniMax;
	// barve
	private JMenuItem menuBarvaZetonov1,menuBarvaZetonov2, menuBarvaPolja;
	// imena igralcev
	private String igralec1 = "BEL";
	private String igralec2 = "CRN";

	private JMenuItem menuRazveljaviPotezo;
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
		

		JMenu menuIgra = dodajMenu(menu_bar, "Nova igra");
		JMenu menuNastavitve = dodajMenu(menu_bar, "Nastavitve");
		


		// dodajamo moznosti na menuIgra (gumb nova igra)
		menuClovekRacunalnik = dodajMenuItem(menuIgra, "Človek – računalnik");
		menuRacunalnikClovek = dodajMenuItem(menuIgra, "Računalnik – človek");
		menuClovekClovek = dodajMenuItem(menuIgra, "Človek – človek");
		menuRacunalnikRacunalnik = dodajMenuItem(menuIgra, "Računalnik – računalnik");
		// dodajamo moznosti na menuNastavitve
		menuVelikostIgre = dodajMenuItem(menuNastavitve, "Nastavi velikost polja");
		// imena igralcev
		JMenu menuImenaIgralcev = dodajMenuVMenu(menuNastavitve, "Nastavi imena igralcev");
		menuImeIgralca1 = dodajMenuItem(menuImenaIgralcev, "Ime začetnega igralca");
		menuImeIgralca2 = dodajMenuItem(menuImenaIgralcev, "Ime drugega igralca");
		// algoritmi
		JMenu menuAlgoritem = dodajMenuVMenu(menuNastavitve, "Zamenjaj algoritem");
		menuAlfaBeta = dodajMenuItem(menuAlgoritem, "AlfaBeta");
		menuMiniMax = dodajMenuItem(menuAlgoritem, "MiniMax");
		dodajMenuItem(menuAlgoritem, "Random move");
		
		menuZamikRacunalnikovePoteze = dodajMenuItem(menuNastavitve, "Nastavi zamik računalnika");
		// dodajamo moznosti na menuBarve
		JMenu menuBarve = dodajMenuVMenu(menuNastavitve, "Barve");
		menuBarvaZetonov1 = dodajMenuItem(menuBarve, "Barva žetonov začetnega igralca");
		menuBarvaZetonov2 = dodajMenuItem(menuBarve, "Barva žetonov drugega igralca");
		menuBarvaPolja = dodajMenuItem(menuBarve, "Barva polja/ozadja");

		menuRazveljaviPotezo = dodajMenuItem(menuNastavitve, "Razveljavi zadnjo potezo!");

		// TODO igram se		
		gumbRazveljavi = dodajGumb(menu_bar, "Razveljavi");

	
		
		
		
		// igralno polje
		polje = new IgralnoPolje();

		GridBagConstraints polje_layout = new GridBagConstraints();
		polje_layout.gridx = 0;
		polje_layout.gridy = 0;
		polje_layout.anchor = GridBagConstraints.CENTER;  //tole sem mislila, da bi lahko dalo na sredo, ampak ni :(
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
		
		status.setText("Izberite igro!");
		
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
		if (source == menuClovekRacunalnik) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.BEL, VrstaIgralca.C); 
			Vodja.vrstaIgralca.put(Igralec.CRN, VrstaIgralca.R);
			Vodja.igramoNovoIgro();
		} else if (source == menuRacunalnikClovek) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.BEL, VrstaIgralca.R); 
			Vodja.vrstaIgralca.put(Igralec.CRN, VrstaIgralca.C);
			Vodja.igramoNovoIgro();
		} else if (source == menuClovekClovek) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.BEL, VrstaIgralca.C); 
			Vodja.vrstaIgralca.put(Igralec.CRN, VrstaIgralca.C);
			Vodja.igramoNovoIgro();
		} else if (source == menuRacunalnikRacunalnik) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.BEL, VrstaIgralca.R); 
			Vodja.vrstaIgralca.put(Igralec.CRN, VrstaIgralca.R);
			Vodja.igramoNovoIgro();
		} else if (source == menuVelikostIgre) {
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
			
		} else if (source == menuImeIgralca1) {
			String i = JOptionPane.showInputDialog("Spremenite ime igralca 1");
			preimenuj(true, i);
		} else if (source == menuImeIgralca2) {
			String i = JOptionPane.showInputDialog("Spremenite ime igralca 2");
			preimenuj(false, i);
			
		// zamenjava algoritma
		} else if (source == menuAlfaBeta) {
			Vodja.racunalnikovaInteligenca.zamenjajAlgoritem(1);
			this.status.setText("Algoritem: AlfaBeta");
		} else if (source == menuMiniMax) {
			Vodja.racunalnikovaInteligenca.zamenjajAlgoritem(2);
			this.status.setText("Algoritem: MiniMax");
			
		} else if (source == menuZamikRacunalnikovePoteze) {
			String i = JOptionPane.showInputDialog("Izberite čas računalnikove poteze v sekundah.");
			if (i!=null && !i.equals("")) {
				try {
					int n = Integer.parseInt(i);
					if (n >= 0) {
						Vodja.zamikRacunalnikovePoteze = n;
					}
					else {JOptionPane.showMessageDialog(this, "Neveljavna izbira!");}
				}
				catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, "Neveljavna izbira!");
				}
			}
		
		} else if (source == menuRazveljaviPotezo) {
			boolean bool1 = false;
			boolean bool2 = false;
			
			if (Vodja.igra == null || Vodja.vrstaIgralca == null) {
				this.status.setText("Najprej izberite željeno igro!");
			} else if (Vodja.vrstaIgralca.get(Igralec.BEL) == VrstaIgralca.R
					&& Vodja.vrstaIgralca.get(Igralec.CRN) == VrstaIgralca.R) {
				this.status.setText("Ko igrata računalnika, potez ne morete razveljaviti");
			} else if (Vodja.vrstaIgralca.get(Igralec.BEL) == VrstaIgralca.C
					&& Vodja.vrstaIgralca.get(Igralec.CRN) == VrstaIgralca.C) {
				bool1 = Vodja.igra.razveljaviPotezo();
				if (bool1) this.status.setText("Poteza uspešno razveljavljena.");
				else this.status.setText("Poteza ni bila razveljavljena. ");
			} else if (Vodja.vrstaIgralca.get(Igralec.BEL) == VrstaIgralca.R
					&& Vodja.vrstaIgralca.get(Igralec.CRN) == VrstaIgralca.C
					&& Vodja.igra.igralecNaPotezi() == Igralec.CRN) {
				bool1 = Vodja.igra.razveljaviPotezo();
				bool2 = Vodja.igra.razveljaviPotezo();
				if (bool1 && bool2) this.status.setText("Potezi razveljavljeni. ");
				else this.status.setText("Potezi nista bili razveljavljeni. ");
			} else if (Vodja.vrstaIgralca.get(Igralec.BEL) == VrstaIgralca.C
					&& Vodja.vrstaIgralca.get(Igralec.CRN) == VrstaIgralca.R
					&& Vodja.igra.igralecNaPotezi() == Igralec.BEL) {
				bool1 = Vodja.igra.razveljaviPotezo();
				bool2 = Vodja.igra.razveljaviPotezo();
				if (bool1 && bool2) this.status.setText("Potezi razveljavljeni. ");
				else this.status.setText("Potezi nista bili razveljavljeni. ");
			}
			
			this.polje.repaint();
			
		// TODO delete
		} else if (source == gumbRazveljavi) {
			boolean bool1 = false;
			boolean bool2 = false;
			
			if (Vodja.igra == null || Vodja.vrstaIgralca == null) {
				this.status.setText("Najprej izberite željeno igro!");
			} else if (Vodja.vrstaIgralca.get(Igralec.BEL) == VrstaIgralca.R
					&& Vodja.vrstaIgralca.get(Igralec.CRN) == VrstaIgralca.R) {
				this.status.setText("Ko igrata računalnika, potez ne morete razveljaviti");
			} else if (Vodja.vrstaIgralca.get(Igralec.BEL) == VrstaIgralca.C
					&& Vodja.vrstaIgralca.get(Igralec.CRN) == VrstaIgralca.C) {
				bool1 = Vodja.igra.razveljaviPotezo();
				if (bool1) this.status.setText("Poteza uspešno razveljavljena.");
				else this.status.setText("Poteza ni bila razveljavljena. ");
			} else if (Vodja.vrstaIgralca.get(Igralec.BEL) == VrstaIgralca.R
					&& Vodja.vrstaIgralca.get(Igralec.CRN) == VrstaIgralca.C
					&& Vodja.igra.igralecNaPotezi() == Igralec.CRN) {
				bool1 = Vodja.igra.razveljaviPotezo();
				bool2 = Vodja.igra.razveljaviPotezo();
				if (bool1 && bool2) this.status.setText("Potezi razveljavljeni. ");
				else this.status.setText("Potezi nista bili razveljavljeni. ");
			} else if (Vodja.vrstaIgralca.get(Igralec.BEL) == VrstaIgralca.C
					&& Vodja.vrstaIgralca.get(Igralec.CRN) == VrstaIgralca.R
					&& Vodja.igra.igralecNaPotezi() == Igralec.BEL) {
				bool1 = Vodja.igra.razveljaviPotezo();
				bool2 = Vodja.igra.razveljaviPotezo();
				if (bool1 && bool2) this.status.setText("Potezi razveljavljeni. ");
				else this.status.setText("Potezi nista bili razveljavljeni. ");
			}
			
			this.polje.repaint();



		} else if (source == menuBarvaZetonov1) {
			Color novaBarva = JColorChooser.showDialog(this, "Izberite barvo", polje.barvaIgralca1);
			polje.barva1(novaBarva);
			
		} else if (source == menuBarvaZetonov2) {
			Color novaBarva = JColorChooser.showDialog(this, "Izberite barvo", polje.barvaIgralca2);
			polje.barva2(novaBarva);
			
		} else if (source == menuBarvaPolja) {
			Color novaBarva = JColorChooser.showDialog(this, "Izberite barvo", polje.barvaOzadja);
			polje.pobarvajOzadje(novaBarva);			
		}
	}


	// funckija za preimenovanje igralcev
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
						status.setText("Na potezi je " + igralec1 + " - " + Vodja.vrstaIgralca.get(Vodja.igra.igralecNaPotezi())); 
						break;
					case CRN:
						status.setText("Na potezi je " + igralec2 + " - " + Vodja.vrstaIgralca.get(Vodja.igra.igralecNaPotezi())); 
						break;
				}
			break;
			case ZMAGA_BEL: 
				status.setText("Zmagal je BEL - " + 
						Vodja.vrstaIgralca.get(Igralec.BEL));
				break;
			case ZMAGA_CRN: 
				status.setText("Zmagal je ČRN - " + 
						Vodja.vrstaIgralca.get(Igralec.CRN));
				break;
			}
		}
		polje.repaint();
	}
	



}
