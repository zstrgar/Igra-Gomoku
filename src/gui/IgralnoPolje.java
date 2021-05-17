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




/**
 *  Risanje plošče
 */
//@SuppressWarnings("serial")
public class IgralnoPolje extends JPanel implements MouseListener {
	
	public IgralnoPolje() {
		setBackground(Color.gray);
		this.addMouseListener(this);
		
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(600, 600);
	}

	
	// Relativna širina črte
	private final static double LINE_WIDTH = 0.02;
	
	// Širina enega kvadratka
	private double squareWidth() {
		return Math.min(getWidth(), getHeight()) / Igra.N;
	}
	
	private final static double PADDING = 0.05;
	
	/**
	 * V grafični kontekst g2 nariši križec v polje (i,j)
	 * 
	 * @param g2
	 * @param i
	 * @param j
	 */
	private void narisiCrnKrog(Graphics2D g2, int i, int j) {
		double w = squareWidth();
		double d = w * (1.0 - 2.0 * PADDING); // premer 
		double x = w * (i + PADDING);
		double y = w * (j + PADDING);
		g2.setColor(Color.BLACK);
		g2.fillOval((int)x, (int)y, (int)d , (int)d);
	}
	
	/**
	 * V grafični kontekst {@g2} nariši križec v polje {@(i,j)}
	 * @param g2
	 * @param i
	 * @param j
	 */
	private void narisiBelKrog(Graphics2D g2, int i, int j) {
		double w = squareWidth();
		double d = w * (1.0 - 2.0 * PADDING); // premer 
		double x = w * (i + PADDING);
		double y = w * (j + PADDING);
		g2.setColor(Color.WHITE);
		g2.fillOval((int)x, (int)y, (int)d , (int)d);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		double w = squareWidth();

		// če imamo zmagovalno vrsto, njeno ozadje pobarvamo
		Vrsta vrsta = null;
		if (Vodja.igra != null) {
			vrsta = Vodja.igra.zmagovalnaVrsta();
		}
		if (vrsta != null) {
			g2.setColor(new Color(255, 255, 196));
			for (int k = 0; k < Igra.M; k++) {
				int i = vrsta.x[k];
				int j = vrsta.y[k];
				g2.fillRect((int)(w * i), (int)(w * j), (int)w, (int)w);
			}
		}
		
		// narisemo črte
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		for (int i = 0; i < Igra.N; i++) {
			g2.drawLine((int)(i * w + w/2),
					    (int)(0),
					    (int)(i * w + w/2),
					    (int)(Igra.N * w));
			g2.drawLine((int)(0),
					    (int)(i * w + w/2),
					    (int)(Igra.N * w),
					    (int)(i * w + w/2));
		}
		
		Polje[][] plosca;;
		if (Vodja.igra != null) {
			plosca = Vodja.igra.getPlosca();
			for (int i = 0; i < Igra.N; i++) {
				for (int j = 0; j < Igra.N; j++) {
					switch(plosca[i][j]) {
					case CRNO: narisiCrnKrog(g2, i, j); break;
					case BELO: narisiBelKrog(g2, i, j); break;
					default: break;
					}
				}
			}
		}	
		
	}
	
	// TODO mogoče bolje če se preveri v radiju kroga
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
			if (0 <= i && i < Igra.N &&
					0.5 * LINE_WIDTH < di && di < 1.0 - 0.5 * LINE_WIDTH &&
					0 <= j && j < Igra.N && 
					0.5 * LINE_WIDTH < dj && dj < 1.0 - 0.5 * LINE_WIDTH) {
				Vodja.clovekovaPoteza (new Koordinati(i, j));
			}
		}
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
