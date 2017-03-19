package cz.zcu.viteja.upg;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * TODO: Zjistit, jak funguje scale (viz metoda paint)
 * 
 * @author Jakub Vítek
 *
 */
public class GamePanel extends JPanel {

	public Terrain terrain;
	public NamedPosition shooter;
	public NamedPosition target;
	public NamedPosition hitSpot;

	public GamePanel(Terrain terrain, NamedPosition shooter, NamedPosition target) {
		this.terrain = terrain;
		this.shooter = shooter;
		this.target = target;
	}

	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2 = (Graphics2D) g;
		double scale = this.getScale();

		if (terrain != null) {

			terrain.draw(g2, scale);

		}

		if (hitSpot != null) {

			// hitSpot.draw(g2, scale);

		}

		if (shooter != null) {

			shooter.draw(g2, scale);

		}

		if (target != null) {

			target.draw(g2, scale);

		}

	}

	public double getScale() {
		double cosiX = this.getWidth() / this.terrain.getWidthInM();
		double cosiY = this.getHeight() / this.terrain.getHeightInM();

		return Math.min(cosiX, cosiY);
	}

}
