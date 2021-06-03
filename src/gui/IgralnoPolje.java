package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.JPanel;
import java.awt.*;

import logika.Igra;
import logika.Vrsta;
import splosno.Koordinati;
import vodja.Vodja;

/**
 *  Risanje plošče
 */
//@SuppressWarnings("serial")
public class IgralnoPolje extends JPanel implements MouseListener {

	protected Color barvaIgralca1;
	protected Color barvaIgralca2;
	protected Color barvaOzadja;
	
	public IgralnoPolje() {
		this.barvaIgralca1=Color.WHITE;
		this.barvaIgralca2=Color.BLACK;
		this.barvaOzadja=new Color(210,180,140);
		this.addMouseListener(this);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(600, 600);
	}

	
	// Relativna širina črte
	private final static double LINE_WIDTH = 0.05;
	
	// Širina enega kvadratka
	private double squareWidth() {
		return Math.min(getWidth(), getHeight()) / (Igra.N+1);
	}
	
	private final static double PADDING = 0.06;
	
	private void narisiKrog(Graphics2D g2, int i, int j, double leftMargin, double topMargin, Color barva, boolean rdecaObroba) {
		double w = squareWidth();
		double d = w * (1.0 - 2.0 * PADDING); // premer 
		double x = w * (i-1 + PADDING)+leftMargin - w/2;
		double y = w * (j-1 + PADDING)+topMargin - w/2;
		GradientPaint gradient;
		if (barva==Color.white) gradient = new GradientPaint ((float)x, (float)y, Color.white, (float)(x+w/3), (float)(y+w/3), new Color(200,200,200)); 
		else gradient = new GradientPaint ((float)x, (float)y, new Color(230,230,230), (float)(x+w/3), (float)(y+w/3), barva); 
		g2.setPaint(gradient);
		// g2.setColor(barva);
		g2.fillOval((int)x, (int)y, (int)d , (int)d);
	// 	if (rdecaObroba) g2.setColor(Color.red);
	// 	else g2.setColor(Color.black);
	// 	g2.setStroke(new BasicStroke(3));
	// 	g2.drawOval((int)x, (int)y, (int)d, (int)d);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		this.setBackground(barvaOzadja);

		double w = squareWidth();
		int velikostPolja = Igra.N;
		// narisemo tako da bo na sredini
		double leftMargin = Math.max((getWidth() - velikostPolja*w)/2, w);
		double topMargin = Math.max((getHeight() - velikostPolja*w)/2, w);
		
		// narisemo črte
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		for (int i = 0; i < velikostPolja; i++) {
			g2.drawLine((int)(i * w + leftMargin),
						(int)(topMargin),
						(int)(i * w + leftMargin),
						(int)((velikostPolja-1) * w + topMargin));
			g2.drawLine((int)(leftMargin),
						(int)(i * w + topMargin),
						(int)((velikostPolja-1) * w + leftMargin),
						(int)(i * w + topMargin));
		}
		
		// narisemo odigrane poteze (krogce)
		if (Vodja.igra != null) {
			LinkedList<Koordinati> playedMoves = Vodja.igra.odigranePoteze;
			int n = playedMoves.size();
			for (int i = 0; i < n; i++) {
				if (i == n-1 && i % 2 == 0) narisiKrog(g2, playedMoves.get(i).getX()+1, playedMoves.get(i).getY()+1, leftMargin, topMargin, barvaIgralca1, true);
				else if (i == n-1) narisiKrog(g2, playedMoves.get(i).getX()+1, playedMoves.get(i).getY()+1, leftMargin, topMargin, barvaIgralca2, true);
				else if (i % 2 == 0) narisiKrog(g2, playedMoves.get(i).getX()+1, playedMoves.get(i).getY()+1, leftMargin, topMargin, barvaIgralca1, false);
				else narisiKrog(g2, playedMoves.get(i).getX()+1, playedMoves.get(i).getY()+1, leftMargin, topMargin, barvaIgralca2, false);
			}

		}	
		// če imamo zmagovalno vrsto, potegnemo crto
		Vrsta vrsta = null;
		if (Vodja.igra != null) {
			vrsta = Vodja.igra.zmagovalnaVrsta();
		}
		if (vrsta != null) {
			int x1 = vrsta.x[0];
			int y1 = vrsta.y[0];
			int x2 = vrsta.x[Igra.M-1];
			int y2 = vrsta.y[Igra.M-1];
			g2.setColor(new Color(255, 51, 51));
			g2.setStroke(new BasicStroke((float) (w/8)));
			g2.drawLine((int)(w * x1 + leftMargin), (int)(w * y1 + topMargin), (int)(w * x2 + leftMargin), (int)(w * y2 + topMargin));
		}		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (Vodja.clovekNaVrsti) {
			int w = (int)(squareWidth());
			int velikostPolja = Igra.N;
			int leftMargin = (int)Math.max((getWidth() - velikostPolja*w)/2, w);
			int topMargin = (int)Math.max((getHeight() - velikostPolja*w)/2, w);
			int x = e.getX()-leftMargin+w/2;
			int y = e.getY()-topMargin+w/2;

			int i = x / w ;
			double di = (x % w) / squareWidth() ; // di je med 0 in 1
			int j = y / w ;
			double dj = (y % w) / squareWidth() ; // dj je med 0 in 1
			if (0 <= i && i < velikostPolja &&
					0.5 * LINE_WIDTH < di && di < 1.0 - 0.5 * LINE_WIDTH &&
					0 <= j && j < velikostPolja && 
					0.5 * LINE_WIDTH < dj && dj < 1.0 - 0.5 * LINE_WIDTH) {
				Vodja.clovekovaPoteza (new Koordinati(i, j));
			}
		}
		// TODO: bliznjica za razveljaviPotezo (IZBRISI)
		double w = squareWidth();
		if (e.getX()+w/2>getWidth() && e.getY()+w/2>getHeight()) Vodja.igra.razveljaviPotezo();
		repaint();
	}

	// metode za spremembo barve
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
