package cz.zcu.viteja.upg;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class TerrainFileHandler {

	// Konstanty
	/** pro pøevod mm na m */
	static final double mmToM = 1000.0;

	// Instanèní promìnné
	public int[][] terrain;
	public int columns;
	public int rows;
	public int deltaX;
	public int deltaY;
	public int shooterX;
	public int shooterY;
	public int targetX;
	public int targetY;

	public TerrainFileHandler() {

	}

	public void loadTerFile(String fileName) {
		try {

			File file = new File(fileName);
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			DataInputStream dis = new DataInputStream(bis);

			this.columns = dis.readInt();
			this.rows = dis.readInt();
			this.deltaX = dis.readInt();
			this.deltaY = dis.readInt();
			this.shooterX = dis.readInt();
			this.shooterY = dis.readInt();
			this.targetX = dis.readInt();
			this.targetY = dis.readInt();

			this.terrain = new int[rows][columns];
			int x, y;

			for (y = 0; y < this.rows; ++y) {
				for (x = 0; x < this.columns; ++x) {
					terrain[y][x] = dis.readInt();
				}
			}

			dis.close();
			bis.close();
			fis.close();

		} catch (IOException e) {
			// Soubor nenalezen
			e.printStackTrace();
		}
	}

	/**
	 * Vypíše nadmoøské výšky aktuálního terénu
	 */
	public void printData() {
		int rowsCount = terrain.length;
		int columnsCount = terrain[0].length;

		System.out.println(String.format("Pocet sloupcu: %d, pocet radku: %d", columnsCount, rowsCount));
		System.out.println(
				String.format("Rozestup mezi sloupci %.3f m, mezi radky %.3f m", deltaX / mmToM, deltaY / mmToM));
		System.out.println(String.format("Rozmery oblasti: sirka %.3f m, vyska %.3f m", columnsCount * deltaX / mmToM,
				rowsCount * deltaY / mmToM));
		System.out.println(String.format("Poloha strelce: sloupec %d, radek %d, tj. x = %.3f m, y = %.3f m", shooterX,
				shooterY, shooterX * deltaX / mmToM, shooterY * deltaY / mmToM));

		if (shooterX < 0 || shooterX >= columnsCount || shooterY < 0 || shooterY >= rowsCount) {
			System.out.println("STRELEC JE MIMO MAPU !");
		} else {
			System.out.println(String.format("   nadmorska vyska strelce %.3f m", terrain[shooterY][shooterX] / mmToM));
		}

		System.out.println(String.format("Poloha cile: sloupec %d, radek %d, tj. x = %.3f m, y = %.3f m", targetX,
				targetY, targetX * deltaX / mmToM, targetY * deltaY / mmToM));

		if (targetX < 0 || targetX >= columnsCount || targetY < 0 || targetY >= rowsCount) {
			System.out.println("CIL JE MIMO MAPU !");
		} else {
			System.out.println(String.format("   nadmorska vyska cile %.3f m", terrain[targetY][targetX] / mmToM));
		}

	}

}
