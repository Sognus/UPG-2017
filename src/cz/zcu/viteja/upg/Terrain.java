package cz.zcu.viteja.upg;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Tøída reprezentující herní terén. Tato tøída nám poskytuje pøístup k
 * informacím jako je napøíklad zjištìní, jaká je nadmoøská výška na daných
 * souøadnicích terénu a další..
 * 
 * @author Jakub Vítek - A16B0165P
 * @version 1.01.00
 *
 */
public class Terrain {

	/** Herní terén reprezentovaný jako vícerozmìné pole nadmoøských výšek */
	public int terrain[][];
	/** Rozestup mezi sloupci v milimetrech */
	public int deltaXInMM;
	/** Rozestup mezi øádeky v milimetrech */
	public int deltaYInMM;

	/** Poèet sloupcù v terénu */
	private int columnCount;
	/** Poèet øádkù v terénu */
	private int rowCount;

	@SuppressWarnings("unused")
	/**
	 * Pomocná reference na instanci, která zajištuje náèítání terénu ze souboru
	 */
	private TerrainFileHandler fHandler;

	/**
	 * Základní konstruktor, který vstupní argumenty uloží do jejich správných
	 * instancí.
	 * 
	 * @param terrain
	 *            vícerozmìrné pole s nadmoøskými výškami
	 * @param deltaXInMM
	 *            rozestup mezi sloupci v milimetrech
	 * @param deltaYInMM
	 *            rozestup mezi øádky v milimetrech
	 */
	public Terrain(int[][] terrain, int deltaXInMM, int deltaYInMM) {
		this.terrain = terrain;
		this.deltaXInMM = deltaXInMM;
		this.deltaYInMM = deltaYInMM;
	}

	/**
	 * Rozšíøený konstruktor, který navíc oproti pùvodnímu konstruktoru jako
	 * parametry požaduje poèet øádkù a sloupcù v terénu. Byl využíván hlavnì
	 * pro testovací úèely
	 * 
	 * @param terrain
	 *            vícerozmìrné pole s nadmoøskými výškami
	 * @param deltaXInMM
	 *            rozestup mezi sloupci v milimetrech
	 * @param deltaYInMM
	 *            rozestup mezi øádky v milimetrech
	 * @param rowCount
	 *            poèet øádkù v terénu
	 * @param columnCount
	 *            poèet sloupcù v terénu
	 */
	public Terrain(int[][] terrain, int deltaXInMM, int deltaYInMM, int rowCount, int columnCount) {
		this(terrain, deltaXInMM, deltaYInMM);
		this.columnCount = columnCount;
		this.rowCount = rowCount;

	}

	/**
	 * Získá nadmoøskou výšku na souøadnich v terénu uèených vstupními argumenty
	 * této metody.
	 * 
	 * @param x
	 *            souøadnice na ose X
	 * @param y
	 *            souøadnice na ose Y
	 * @return nadmoøská výška v bode [x, y]
	 */
	public double getAltitudeInM(double x, double y) {
		// Získám souøadnice v milimetrech
		int mmX = (int) (x * Constants.mToMM);
		int mmY = (int) (y * Constants.mToMM);

		// Vrátím nadmoøskou výšku v metrech
		return (terrain[mmX][mmY] / Constants.mmToM);
	}

	/**
	 * Vykreslování terénu. Na základì použití promìnné pøevádìjící metry na
	 * pixely vykreslí v oknì bílou barvou herní terén pøizpùspobený aktuální
	 * výšce a šíøce okna. Na hranicích terénu je èernou barvou vykreslena
	 * hranice.
	 * 
	 * @param g2
	 *            grafický kontext
	 * @param scale
	 *            promìnná díky které lze pøevádìt metry na pixely
	 */
	public void draw(Graphics2D g2, double scale) {

		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, (int) (getWidthInM() * scale), (int) (getHeightInM() * scale));
		g2.setColor(Color.black);
		g2.drawRect(0, 0, (int) (getWidthInM() * scale), (int) (getHeightInM() * scale));

	}

	/**
	 * Vrátí šíøku aktuálního terénu v metrech
	 * 
	 * @return aktuální šíøka terénu v metrech
	 */
	public double getWidthInM() {
		return (columnCount * deltaXInMM / 1000.0);

	}

	/**
	 * Vrátí výšku aktuálního terénu v metrech
	 * 
	 * @return aktuální výška terénu v metrech
	 */
	public double getHeightInM() {
		return (rowCount * deltaYInMM / 1000.0);

	}
}
