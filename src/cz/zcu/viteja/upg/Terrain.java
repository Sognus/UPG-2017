package cz.zcu.viteja.upg;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * T��da reprezentuj�c� hern� ter�n. Tato t��da n�m poskytuje p��stup k
 * informac�m jako je nap��klad zji�t�n�, jak� je nadmo�sk� v��ka na dan�ch
 * sou�adnic�ch ter�nu a dal��..
 * 
 * @author Jakub V�tek - A16B0165P
 * @version 1.01.00
 *
 */
public class Terrain {

	/** Hern� ter�n reprezentovan� jako v�cerozm�n� pole nadmo�sk�ch v��ek */
	public int terrain[][];
	/** Rozestup mezi sloupci v milimetrech */
	public int deltaXInMM;
	/** Rozestup mezi ��deky v milimetrech */
	public int deltaYInMM;

	/** Po�et sloupc� v ter�nu */
	private int columnCount;
	/** Po�et ��dk� v ter�nu */
	private int rowCount;

	@SuppressWarnings("unused")
	/**
	 * Pomocn� reference na instanci, kter� zaji�tuje n���t�n� ter�nu ze souboru
	 */
	private TerrainFileHandler fHandler;

	/**
	 * Z�kladn� konstruktor, kter� vstupn� argumenty ulo�� do jejich spr�vn�ch
	 * instanc�.
	 * 
	 * @param terrain
	 *            v�cerozm�rn� pole s nadmo�sk�mi v��kami
	 * @param deltaXInMM
	 *            rozestup mezi sloupci v milimetrech
	 * @param deltaYInMM
	 *            rozestup mezi ��dky v milimetrech
	 */
	public Terrain(int[][] terrain, int deltaXInMM, int deltaYInMM) {
		this.terrain = terrain;
		this.deltaXInMM = deltaXInMM;
		this.deltaYInMM = deltaYInMM;
	}

	/**
	 * Roz���en� konstruktor, kter� nav�c oproti p�vodn�mu konstruktoru jako
	 * parametry po�aduje po�et ��dk� a sloupc� v ter�nu. Byl vyu��v�n hlavn�
	 * pro testovac� ��ely
	 * 
	 * @param terrain
	 *            v�cerozm�rn� pole s nadmo�sk�mi v��kami
	 * @param deltaXInMM
	 *            rozestup mezi sloupci v milimetrech
	 * @param deltaYInMM
	 *            rozestup mezi ��dky v milimetrech
	 * @param rowCount
	 *            po�et ��dk� v ter�nu
	 * @param columnCount
	 *            po�et sloupc� v ter�nu
	 */
	public Terrain(int[][] terrain, int deltaXInMM, int deltaYInMM, int rowCount, int columnCount) {
		this(terrain, deltaXInMM, deltaYInMM);
		this.columnCount = columnCount;
		this.rowCount = rowCount;

	}

	/**
	 * Z�sk� nadmo�skou v��ku na sou�adnich v ter�nu u�en�ch vstupn�mi argumenty
	 * t�to metody.
	 * 
	 * @param x
	 *            sou�adnice na ose X
	 * @param y
	 *            sou�adnice na ose Y
	 * @return nadmo�sk� v��ka v bode [x, y]
	 */
	public double getAltitudeInM(double x, double y) {
		// Z�sk�m sou�adnice v milimetrech
		int mmX = (int) (x * Constants.mToMM);
		int mmY = (int) (y * Constants.mToMM);

		// Vr�t�m nadmo�skou v��ku v metrech
		return (terrain[mmX][mmY] / Constants.mmToM);
	}

	/**
	 * Vykreslov�n� ter�nu. Na z�klad� pou�it� prom�nn� p�ev�d�j�c� metry na
	 * pixely vykresl� v okn� b�lou barvou hern� ter�n p�izp�spoben� aktu�ln�
	 * v��ce a ���ce okna. Na hranic�ch ter�nu je �ernou barvou vykreslena
	 * hranice.
	 * 
	 * @param g2
	 *            grafick� kontext
	 * @param scale
	 *            prom�nn� d�ky kter� lze p�ev�d�t metry na pixely
	 */
	public void draw(Graphics2D g2, double scale) {

		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, (int) (getWidthInM() * scale), (int) (getHeightInM() * scale));
		g2.setColor(Color.black);
		g2.drawRect(0, 0, (int) (getWidthInM() * scale), (int) (getHeightInM() * scale));

	}

	/**
	 * Vr�t� ���ku aktu�ln�ho ter�nu v metrech
	 * 
	 * @return aktu�ln� ���ka ter�nu v metrech
	 */
	public double getWidthInM() {
		return (columnCount * deltaXInMM / 1000.0);

	}

	/**
	 * Vr�t� v��ku aktu�ln�ho ter�nu v metrech
	 * 
	 * @return aktu�ln� v��ka ter�nu v metrech
	 */
	public double getHeightInM() {
		return (rowCount * deltaYInMM / 1000.0);

	}
}
