package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumMap;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import logika.Igralec;
import vodja.Vodja;
import vodja.VrstaIgralca;


//@SuppressWarnings("serial")
public class GlavnoOkno extends JFrame implements ActionListener {
	/**
	 * JPanel
	 */
	private IgralnoPolje polje;

	
	//Statusna vrstica v spodnjem delu okna
	private JLabel status;
	
	// Izbire v menuju
	private JMenuItem menuClovekRacunalnik, menuRacunalnikClovek, menuClovekClovek, menuRacunalnikRacunalnik;
	private JMenuItem menuVelikostIgre, menuImeIgralca, menuAlgoritem, menuCasPoteze;
	private JMenuItem menuBarvaZetonov, menuBarvaPolja;
	

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

		JMenu menuIgra = dodajMenu(menu_bar, "Igra");
		JMenu menuNastavitve = dodajMenu(menu_bar, "Nastavitve");
		JMenu menuPolje = dodajMenu(menu_bar, "Polje");

		menuClovekRacunalnik = dodajMenuItem(menuIgra, "Človek – računalnik");
		menuRacunalnikClovek = dodajMenuItem(menuIgra, "Računalnik – človek");
		menuClovekClovek = dodajMenuItem(menuIgra, "Človek – človek");
		menuRacunalnikRacunalnik = dodajMenuItem(menuIgra, "Računalnik – računalnik");
		
		menuVelikostIgre = dodajMenuItem(menuNastavitve, "Velikost igre...");
		menuImeIgralca = dodajMenuItem(menuNastavitve, "Ime igralca...");
		menuAlgoritem = dodajMenuItem(menuNastavitve, "Algoritem...");
		menuCasPoteze = dodajMenuItem(menuNastavitve, "Nastavi čas poteze ...");
		
		menuBarvaZetonov = dodajMenuItem(menuPolje, "Barva žetonov ...");
		menuBarvaPolja = dodajMenuItem(menuPolje, "Barva polja ...");
		
		
		
		// igralno polje
		polje = new IgralnoPolje();

		GridBagConstraints polje_layout = new GridBagConstraints();
		polje_layout.gridx = 0;
		polje_layout.gridy = 0;
		polje_layout.anchor = GridBagConstraints.CENTER;  //tole sem mislila, da bi lahko dalo na sredo, ampak ni :(
		polje_layout.fill = GridBagConstraints.BOTH;
		polje_layout.weightx = 0;	//ko je na 0, je centriran; ko je na 1 se povečuje z oknom
		polje_layout.weighty = 0;	
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
	 * Pomožni metudi dodajMenu in dodajMenuItem za kontruiranje menuja
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
			JTextField velikostN = new JTextField();
			JTextField vVrsto = new JTextField();
			JComponent[] polja = {
					new JLabel("Vnesi velikost igre N:"), velikostN,
					new JLabel("Vnesi stevilo zetonov:"), vVrsto,
			};
			int izbira = JOptionPane.showConfirmDialog(this,  polja, "Input", JOptionPane.OK_CANCEL_OPTION);
			if (izbira == JOptionPane.OK_OPTION && velikostN.getText().matches("\\d+") && vVrsto.getText().matches("\\d+")) {
				//TODO dokončaj....., pomoje se rabi naštimat da se nekje lahko naszavi velikost itd.
				
			}
			
		} else if (source == menuImeIgralca) {
			
		} else if (source == menuAlgoritem) {
			
		} else if (source == menuCasPoteze) {
			
		} else if (source == menuBarvaZetonov) {
			
		} else if (source == menuBarvaPolja) {
			
		}
	}

	public void osveziGUI() {
		if (Vodja.igra == null) {
			status.setText("Igra ni v teku.");
		}
		else {
			switch(Vodja.igra.stanje()) {
			case NEODLOCENO: status.setText("Neodločeno!"); break;
			case V_TEKU: 
				status.setText("Na potezi je " + Vodja.igra.naPotezi + 
						" - " + Vodja.vrstaIgralca.get(Vodja.igra.naPotezi)); 
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
