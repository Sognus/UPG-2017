package cz.zcu.viteja.upg;

/**
 * Tøída, která na základì referencí na støelce a cíl a hodnot azimutu a
 * vzdálenosti je schopná vypoèítat souøadnice zásahu a vytvoøit z nich
 * NamedPosition objekt oblasti zásahu
 * 
 * @author Jakub Vítek - A16B0165P
 * @version 1.00.00
 */
public class ShootingCalculator {

	/** Reference na instanci støelce */
	public NamedPosition shooter;
	/** Reference na instanci cíle */
	public NamedPosition target;

	/**
	 * Reference na instanci oblasti zásahu. Nejprve je null, vytvoøí se po
	 * zavolání metody shoot
	 */
	private NamedPosition hitSpot;

	/**
	 * Konstruktor tøídy {@link ShootingCalculator}, který uloží své argumenty
	 * jako korespondující promìnné instance
	 * 
	 * @param shooter
	 *            reference na støelce
	 * @param target
	 *            reference na cíl
	 */
	public ShootingCalculator(NamedPosition shooter, NamedPosition target) {
		this.shooter = shooter;
		this.target = target;
		this.hitSpot = null;
	}

	public void shoot(double azimuth, double distance) {
		// Støelec i target jsou zadány v metrech
		// Posun v metrech

		// Pøevod stupnì 0 až -180 na 180 - -uhel
		double radians = azimuth * (Math.PI / 180);

		double posunX = Math.cos(radians) * distance;
		double posunY = Math.sin(radians) * distance;

		// System.out.println(
		// String.format("sX + posuX = %.5f + %.5f = %.5f)", this.shooter.x,
		// posunX, this.shooter.x + posunX));

		this.hitSpot = new NamedPosition(this.shooter.x + posunX, this.shooter.y - posunY, Constants.HITSPOT,
				Constants.hitspotColor, 60);

	}

	/**
	 * Metoda, která vrací pravdivností hodnotu o tom, zda byl treferen cíl.
	 * Pokud jsme ještì nevystøelili, nemáme žádnou instanci oblasti zásahu,
	 * tudíž jsme nemohli trefit (nevystøelili jsme). Pokud byla støelba
	 * provedena, ovìøí se zda je cíl umístìn uvnitø oblasti zásahu.
	 * 
	 * @return byl zasažen cíl?
	 */
	public boolean testTargetHit() {
		if (this.hitSpot.equals(null)) {
			return false;
		}

		return (this.hitSpot.getDistance(target) <= 30);

	}

	/**
	 * Getter, vrátí aktuální referenci na objekt oblasti zásahu
	 * 
	 * @return oblast zásahu
	 */
	public NamedPosition getHitSpot() {
		return this.hitSpot;
	}

}
