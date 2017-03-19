package cz.zcu.viteja.upg;

import java.awt.BorderLayout;

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

	public static JFrame frame;
	public static GamePanel gamePanel;

	/**
	 * Hlavní metoda aplikace
	 * 
	 * @param args
	 *            vstupní parametry aplikace
	 */
	public static void main(String[] args) {
		String fileName = "C:/Users/msogn/Desktop/workspace/UPG/bin/jar/rovny1metr.ter";
		// String fileName =
		// "D:/Users/Sognus/Documents/workspace/UPG/bin/jar/rovny1metr_1km_x_1km.ter";

		// Naètení souboru
		terrainFile = new TerrainFileHandler();
		terrainFile.loadTerFile(fileName);
		terrainFile.printData();

		// Získání dat o støelci a cíli z naèteného souboru
		shooter = new NamedPosition(terrainFile.shooterX / Constants.mmToM, terrainFile.shooterY / Constants.mmToM,
				Constants.SHOOTER, Constants.shooterColor, 10.0);
		target = new NamedPosition(terrainFile.targetX / Constants.mmToM, terrainFile.targetY / Constants.mmToM,
				Constants.TARGET, Constants.targetColor, 10.0);

		double shooterTargetDistance = shooter.getDistance(target);

		terrain = new Terrain(terrainFile.terrain, terrainFile.deltaX, terrainFile.deltaY, terrainFile.rows,
				terrainFile.columns);

		// Vytvoøení okna
		frame = new JFrame();
		gamePanel = new GamePanel(terrain, shooter, target);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setLayout(new BorderLayout());
		gamePanel.setSize(Constants.drawAreaWidth + 20, Constants.drawAreaHeight + 20);
		frame.add(gamePanel, BorderLayout.CENTER);

		frame.setTitle("Prototyp 1 | J. Vítek | A16B0165P");
		frame.pack();

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setSize(Constants.drawAreaWidth + 40, Constants.drawAreaHeight + 75);
	}
}
