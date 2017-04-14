package cz.zcu.viteja.upg;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

public class CompassPanel extends JPanel implements Observer {

	public Wind wind;

	public CompassPanel(Wind wind) {
		this.wind = wind;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		double stredX = Game.gamePanel.terrain.getWidthInPixels() / 2;
		double stredY = Game.gamePanel.terrain.getHeightInPixels() / 2;
		double prumer = Game.gamePanel.terrain.getHeightInPixels() / 100 * 75;

		Graphics2D g2 = (Graphics2D) g;
		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(hints);

		g2.translate(10, 10);

		if (wind != null) {
			// Vykreslení elipsy
			g2.draw(new Line2D.Double(stredX - 5, stredY, stredX + 5, stredY));
			g2.draw(new Line2D.Double(stredX, stredY + 5, stredX, stredY - 5));
			g2.draw(new Ellipse2D.Double(stredX - prumer / 2, stredY - prumer / 2, prumer, prumer));

			// Nastavení nového fontu
			Font font = new Font("Courier", Font.TRUETYPE_FONT, 28);
			g2.setFont(font);

			// Výpoèet rozmìrù textu na základì fontu
			FontMetrics fm = g2.getFontMetrics();
			int stringHeight = fm.getHeight();
			int stringWidthW = fm.stringWidth("W");
			int stringWidthN = fm.stringWidth("N");
			int stringWidthS = fm.stringWidth("S");

			// Vykreslení ukazatelù svìtových smìrù
			g2.drawString("E", (float) (stredX + prumer / 2) + 5, (float) (stredY + 1 * stringHeight / 4));
			g2.drawString("W", (float) (stredX - prumer / 2) - 5 - stringWidthW,
					(float) (stredY + 1 * stringHeight / 4));
			g2.drawString("N", (float) (stredX - stringWidthN / 2), (float) (stredY - prumer / 2 - 5));
			g2.drawString("S", (float) (stredX - stringWidthS / 2),
					(float) (stredY + prumer / 2 + 5 + stringHeight / 2));

			// Vykreslení støelky kompasu
			AffineTransform at = g2.getTransform();

			g2.translate(stredX, stredY);
			g2.rotate(-wind.getAzimuth());

			double strelkaPrumer = Game.gamePanel.terrain.getHeightInPixels() / 100 * 5;

			Path2D strelkaCervena = new Path2D.Double();
			strelkaCervena.moveTo(0, 0);
			strelkaCervena.lineTo(0, 0 + strelkaPrumer);
			strelkaCervena.lineTo(0 + prumer / 2, 0);
			strelkaCervena.lineTo(0, 0 - strelkaPrumer);
			strelkaCervena.closePath();

			Path2D strelkaModra = new Path2D.Double();
			strelkaModra.moveTo(0, 0);
			strelkaModra.lineTo(0, 0 + strelkaPrumer);
			strelkaModra.lineTo(0 - prumer / 2, 0);
			strelkaModra.lineTo(0, 0 - strelkaPrumer);
			strelkaModra.closePath();

			g2.setColor(Color.RED);
			g2.fill(strelkaCervena);
			g2.setColor(new Color(0, 102, 204, 255));
			g2.fill(strelkaModra);

			g2.setTransform(at);

			// Vykreslení pomocných èar
			g2.setColor(Color.LIGHT_GRAY);
			Stroke dotted = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 1, 2 },
					0);
			g2.setStroke(dotted);

			g2.draw(new Line2D.Double(stredX, stredY, (float) (stredX + prumer / 2) + 5,
					(float) (stredY + 0 * stringHeight / 2)));

			g2.draw(new Line2D.Double(stredX, stredY, (float) (stredX - prumer / 2) - 5,
					(float) (stredY + 0 * stringHeight / 2)));

			g2.draw(new Line2D.Double(stredX, stredY, stredX, stredY - prumer / 2 - 5));

			g2.draw(new Line2D.Double(stredX, stredY, stredX, stredY + prumer / 2 + 5));

		}
	}

	/**
	 * Pokud je zmìnìn jakýkoliv parametr v instanci vìtru, panel bude
	 * pøekreslen
	 */
	@Override
	public void update(Observable o, Object arg) {
		this.repaint();

	}

}
