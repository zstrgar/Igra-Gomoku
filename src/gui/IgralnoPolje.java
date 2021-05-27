package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;


import logika.Igra;
import logika.Polje;
import logika.Vrsta;
import splosno.Koordinati;
import vodja.Vodja;;

/**
 *  TODO
 *  1. plosca na sredino
 *  2. menu
 *  	1. nastavljiva velikost igralne plošče
 *  	2. lastnosti igralcev (ime, človek ali računalnik, kateri algoritem uporablja računalnik, ...), 
 *   	3. lastnosti grafičnega vmesnika (barve žetonov, ...), 
 *  	4. koliko časa naj preteče, preden računalnik odigra potezo ....
 *  3. lepša plošča
 *  4. (polepšaj kodo, komentarji)
 */

//TODO: ustrezna prilagoditev žetonov in plošče ob povečanje/pomanjšanju okna




/**
 *  Risanje plošče
 */
//@SuppressWarnings("serial")
public class IgralnoPolje extends JPanel implements MouseListener {

	protected Color barvaIgralca1;
	protected Color barvaIgralca2;
	protected Color barvaOzadja;
	protected int velikostIgre;
	protected Igra igra;

	
	public IgralnoPolje(int velikostIgre) {
		this.velikostIgre = velikostIgre;
		this.igra = null;
		this.barvaIgralca1=Color.WHITE;
		this.barvaIgralca2=Color.BLACK;
		this.barvaOzadja=Color.LIGHT_GRAY;
		// this.setBackground(barvaOzadja); // nastavimo nizje (glej dol)
		this.addMouseListener(this);
		
	}
	

	public void nastaviIgro(Igra igra){
		this.igra=igra;
		repaint();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(600, 600);
	}

	
	// Relativna širina črte
	private final static double LINE_WIDTH = 0.02;
	
	// Širina enega kvadratka
	private double squareWidth() {
		return Math.min(getWidth(), getHeight()) / velikostIgre;
	}
	
	private final static double PADDING = 0.05;
	
	
	/**
	 * V grafični kontekst {@g2} nariši križec v polje {@(i,j)}
	 * @param g2
	 * @param i
	 * @param j
	 */
	private void narisiKrog(Graphics2D g2, int i, int j, Color barva) {
		double w = squareWidth();
		double d = w * (1.0 - 2.0 * PADDING); // premer 
		double x = w * (i + PADDING);
		double y = w * (j + PADDING);
		g2.setColor(barva);
		g2.fillOval((int)x, (int)y, (int)d , (int)d);
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke(2));
		g2.drawOval((int)x, (int)y, (int)d, (int)d);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		

		double w = squareWidth();	

		this.setBackground(barvaOzadja);

		// narisemo črte
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		for (int i = 0; i < velikostIgre; i++) {
			g2.drawLine((int)(i * w + w/2),
					    (int)(0),
					    (int)(i * w + w/2),
					    (int)(velikostIgre * w));
			g2.drawLine((int)(0),
					    (int)(i * w + w/2),
					    (int)(velikostIgre * w),
					    (int)(i * w + w/2));
		}
		// narisemo odigrane poteze (krogce)
		Polje[][] plosca;;
		if (Vodja.igra != null) {
			plosca = Vodja.igra.getPlosca();
			for (int i = 0; i < velikostIgre; i++) {
				for (int j = 0; j < velikostIgre; j++) {
					switch(plosca[i][j]) {
					case CRNO: narisiKrog(g2, i, j, barvaIgralca2); break;
					case BELO: narisiKrog(g2, i, j, barvaIgralca1); break;
					default: break;
					}
				}
			}
		}
		// če imamo zmagovalno vrsto, potegnemo crto (njeno ozadje pobarvamo)
		Vrsta vrsta = null;
		if (Vodja.igra != null) {
			vrsta = Vodja.igra.zmagovalnaVrsta();
		}
		if (vrsta != null) {
			int x1 = vrsta.x[0];
			int y1 = vrsta.y[0];
			// int x2 = vrsta.x[igra.M-1];
			// int y2 = vrsta.y[igra.M-1];
			// TODO: zacasno
			int x2 = vrsta.x[4];
			int y2 = vrsta.y[4];
			g2.setColor(new Color(255, 51, 51));
			g2.setStroke(new BasicStroke((float) (w/8 )));
			g2.drawLine((int)(w * x1+ w/2), (int)(w * y1+ w/2), (int)(w * x2+ w/2), (int)(w * y2+ w/2));
		}		
	
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (Vodja.clovekNaVrsti) {
			int x = e.getX();
			int y = e.getY();
			int w = (int)(squareWidth());
			//double r = w * (1.0 - 2.0 * PADDING);
			int i = x / w ;
			double di = (x % w) / squareWidth() ;
			int j = y / w ;
			double dj = (y % w) / squareWidth() ;
			if (0 <= i && i < velikostIgre &&
					0.5 * LINE_WIDTH < di && di < 1.0 - 0.5 * LINE_WIDTH &&
					0 <= j && j < velikostIgre && 
					0.5 * LINE_WIDTH < dj && dj < 1.0 - 0.5 * LINE_WIDTH) {
				Vodja.clovekovaPoteza (new Koordinati(i, j));
			}
		}
	}

	public void barva1(Color b) {
		if (b != null) {
			this.barvaIgralca1 = b;
			repaint();		}
	}
	public void barva2(Color b) {
		if (b != null) {
			this.barvaIgralca2 = b;
			repaint();		}
	}
	public void pobarvajOzadje(Color b) {
		if (b != null) {
			this.barvaOzadja = b;
			repaint();		}
	}

	@Override
	public void mousePressed(MouseEvent e) {		
	}

	@Override
	public void mouseReleased(MouseEvent e) {		
	}

	@Override
	public void mouseEntered(MouseEvent e) {		
	}

	@Override
	public void mouseExited(MouseEvent e) {		
	}
	
}
