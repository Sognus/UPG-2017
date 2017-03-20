package cz.zcu.viteja.upg;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFrame;

/**
 * Hlavní tøída aplikace.
 * 
 * @author Jakub Vítek
 * @version 1.00.00
 *
 */
public class Game {

	// Konstanty
	/** pro pøevod mm na m */
	static final double mmToM = 1000.0;
	/** pro pøevod m na mm */
	static final double mToMM = 0.001;

	// Tøídní atributy
	public static TerrainFileHandler terrainFile;
	public static Terrain terrain;

	public static NamedPosition shooter;
	public static NamedPosition target;
	public static ShootingCalculator shootingCalculator;

	public static JFrame frame;
	public static GamePanel gamePanel;

	private static String[] startArgs;

	/**
	 * Hlavní metoda aplikace
	 * 
	 * @param args
	 *            vstupní parametry aplikace
	 */
	public static void main(String[] args) {
		// Uložit vstupní parametry tak, aby bylo možné pøistupovat k nim v celé
		// tøídì
		startArgs = args;

		String fileName = "C:/Users/msogn/Desktop/workspace/UPG/data/rovny1metr_1km_x_1km.ter";

		// Naètení souboru
		loadTerrain(fileName);

		// Získání všech nutných instancí objektù, nastavení nutných promìnných
		initData();

		// Vytvoøení okna
		makeWindow();

		// Hlavní herní cyklus
		gameMainLoop();
	}

	public static void initData() {
		shooter = new NamedPosition(terrainFile.shooterX * terrainFile.deltaX / Constants.mmToM,
				terrainFile.shooterY * terrainFile.deltaY / Constants.mmToM, Constants.SHOOTER, Constants.shooterColor,
				10.0);
		target = new NamedPosition(terrainFile.targetX * terrainFile.deltaX / Constants.mmToM,
				terrainFile.targetY * terrainFile.deltaY / Constants.mmToM, Constants.TARGET, Constants.targetColor,
				10.0);

		terrain = new Terrain(terrainFile.terrain, terrainFile.deltaX, terrainFile.deltaY, terrainFile.rows,
				terrainFile.columns);

		shootingCalculator = new ShootingCalculator(shooter, target);
	}

	public static void gameMainLoop() {

		while (true) {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				double azimuth;
				double distance;
				if (startArgs.length >= 2 && !startArgs.equals(null)) {
					azimuth = Double.valueOf(startArgs[0]);
					distance = Double.valueOf(startArgs[1]);
				} else {

					System.out.print("Zadejte azimut: ");
					azimuth = Double.valueOf(br.readLine());

					System.out.print("Zadejte vzdálenost støelby: ");
					distance = Double.valueOf(br.readLine());
				}

				shootingCalculator.shoot(azimuth, distance);

				gamePanel.setHitSpot(shootingCalculator.getHitSpot());

				if (shootingCalculator.testTargetHit()) {
					System.out.println("ZASAH");
				}

				else {
					System.out.println("VEDLE");
				}

				startArgs = new String[0];
				frame.repaint();

				// Zeptat se znova na hraní

				System.out.print("Hrát znovu <a/n>: ");
				String hrat = br.readLine();

				if (!hrat.toLowerCase().equals("a") && !hrat.toLowerCase().equals("ano")
						&& !hrat.toLowerCase().equals("true")) {
					System.out.println("Hra byla ukonèena");

					frame.setVisible(false);
					frame.dispose();
					break;
				}

			} catch (NumberFormatException | IOException e) {
				System.out.println("Nepodaøil se pøeèíst vstup nebo zadaný vstup nebyl platným èíslem");
				e.printStackTrace();
				break;
			}
		}

	}

	public static void loadTerrain(String filename) {
		terrainFile = new TerrainFileHandler();
		terrainFile.loadTerFile(filename);
		terrainFile.printData();
	}

	public static void makeWindow() {
		frame = new JFrame();
		gamePanel = new GamePanel(terrain, shooter, target);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setLayout(new BorderLayout());
		gamePanel.setSize(Constants.preferedWindowWidth + 20, Constants.preferedWindowHeight + 20);
		frame.add(gamePanel, BorderLayout.CENTER);

		frame.setTitle("Prototyp 1 | J. Vítek | A16B0165P");
		frame.pack();

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setSize(Constants.preferedWindowWidth, Constants.preferedWindowHeight);
	}
}
