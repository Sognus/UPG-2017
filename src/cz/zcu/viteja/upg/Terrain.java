package cz.zcu.viteja.upg;

import java.awt.Color;
import java.awt.Graphics2D;

public class Terrain {

	public int terrain[][];
	public int deltaXInMM;
	public int deltaYInMM;

	private int columnCount;
	private int rowCount;

	private TerrainFileHandler fHandler;

	public Terrain(int[][] terrain, int deltaXInMM, int deltaYInMM) {
		this.terrain = terrain;
		this.deltaXInMM = deltaXInMM;
		this.deltaYInMM = deltaYInMM;
	}

	public Terrain(int[][] terrain, int deltaXInMM, int deltaYInMM, int rowCount, int columnCount) {
		this(terrain, deltaXInMM, deltaYInMM);
		this.columnCount = columnCount;
		this.rowCount = rowCount;

	}

	public double getAltitudeInM(double x, double y) {
		// Získám souøadnice v milimetrech
		int mmX = (int) (x * Constants.mToMM);
		int mmY = (int) (y * Constants.mToMM);

		// Vrátím nadmoøskou výšku v metrech
		return (terrain[mmX][mmY] / Constants.mmToM);
	}

	public void draw(Graphics2D g2, double scale) {

		// g2.setColor(Color.WHITE);
		// g2.fill(new Rectangle2D.Double(10, 10, this.getWidthInM() * scale,
		// this.getHeightInM() * scale));
		// g2.setColor(Color.BLACK);
		// g2.draw(new Rectangle2D.Double(10, 10, this.getWidthInM() * scale,
		// this.getHeightInM() * scale));

		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, (int) (getWidthInM() * scale), (int) (getHeightInM() * scale));
		g2.setColor(Color.black);
		g2.drawRect(0, 0, (int) (getWidthInM() * scale), (int) (getHeightInM() * scale));

	}

	public double getWidthInM() {
		double width = columnCount * deltaXInMM / 1000.0;
		return width;
	}

	public double getHeightInM() {
		double height = rowCount * deltaYInMM / 1000.0;
		return height;
	}
}
