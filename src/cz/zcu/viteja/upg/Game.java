package cz.zcu.viteja.upg;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFrame;

/**
 * Hlavní vstupní tøída aplikace, která zajištuje základní úkony, kterými jsou
 * napøíklad: naètení herní mapy ze souboru, získání a udržení všech dùležitých
 * instancí na èásti aplikace.
 * 
 * @author Jakub Vítek - A16B0165P
 * @version 1.01.00
 *
 */
public class Game {

	// Konstanty
	/** konstanta pro pøevod mm na m */
	static final double mmToM = 1000.0;
	/** konstanta pro pøevod m na mm */
	static final double mToMM = 0.001;

	// Tøídní atributy
	/** Reference na instanci, která zajišuje naèítání souboru terénu */
	public static TerrainFileHandler terrainFile;
	/** Reference na instanci, která reprezentuje naètený herní terén */
	public static Terrain terrain;

	/**
	 * Reference na instanci, která reprezentuje pozici a další vlastnosti
	 * støelce
	 */
	public static NamedPosition shooter;
	/**
	 * Reference na instanci, která reprezentuje pozici a další vlastnosti cíle
	 */
	public static NamedPosition target;
	/**
	 * Reference na instanci, která na základì vstupních dat dokáže vypoèítat
	 * dopad støely a ovìøit zda støela zasáhla èi minula
	 */
	public static ShootingCalculator shootingCalculator;

	/** Reference na instanci okna aplikace */
	public static JFrame frame;
	/** Reference na instanci herního panelu, který je vykreslován v oknì */
	public static GamePanel gamePanel;

	/**
	 * Reference na vstupní parametry, která je použita, aby bylo možné k
	 * parametrùm z console pøistupovat odkudkoliv ze tøídy aniž by bylo nutné
	 * pøedávat hodnoty jako argument metod
	 */
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

	/**
	 * Získá všechny nutné dùležité instance, bez kterých by aplikace nemohla
	 * pracovat a uloží je do korespondujících statických atributù
	 */
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

	/**
	 * Metoda, která se stará o funkènost hlavního cyklu aplikace. Metoda se
	 * pokusí získat hodnoty konzole parametrù, pokud je nenajde, zeptá se na
	 * všechny potøebné hodnoty uživatele. Hlavní cyklus bìží tak dlouho, dokud
	 * se uživatel nerozhodne ukonèit aplikaci
	 */
	public static void gameMainLoop() {

		System.out.println("-----HRA-----");

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

				System.out.println();
				if (shootingCalculator.testTargetHit()) {
					System.out.println("ZÁSAH! Cíl byl znièen!");
				}

				else {
					System.out.println("VEDLE! Cíl nebyl zasažen!");
				}

				startArgs = new String[0];
				frame.repaint();

				// Zeptat se znova na hraní

				System.out.println();
				System.out.print("Hrát znovu? <ano/ne>: ");
				String hrat = br.readLine();

				if (!hrat.toLowerCase().equals("a") && !hrat.toLowerCase().equals("ano")
						&& !hrat.toLowerCase().equals("true")) {
					System.out.println("-----Hra byla ukonèena-----");

					frame.setVisible(false);
					frame.dispose();
					break;
				}

				System.out.println("----------");

			} catch (NumberFormatException | IOException e) {
				System.out.println("Nepodaøil se pøeèíst vstup nebo zadaný vstup nebyl platným èíslem");
				e.printStackTrace();
				break;
			}
		}

	}

	/**
	 * Na základì vstupního parametru, který reprezentuje jméno/cestu souboru,
	 * ve kterém je v domluveném formátu uložen herní terén, naète všechny
	 * potøebné informace ze souboru a vypíše základní informace o nìm.
	 * 
	 * @param filename
	 *            jméno/cesta k souboru herního terénu
	 */
	public static void loadTerrain(String filename) {
		terrainFile = new TerrainFileHandler();
		terrainFile.loadTerFile(filename);
		terrainFile.printData();
	}

	/**
	 * Vytvoøí nové okno aplikace, do kterého pøidá panel, ve kterém lze
	 * vykreslovat všechny potøebné komponenty.
	 */
	public static void makeWindow() {
		frame = new JFrame();
		gamePanel = new GamePanel(terrain, shooter, target);

		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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
