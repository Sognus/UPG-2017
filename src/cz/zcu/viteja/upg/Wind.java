package cz.zcu.viteja.upg;

import java.util.Random;

/**
 * Tøída reprezentující simulovaný vítr
 * 
 * @author Jakub Vítek (A16B0165P)
 * @version 1.00.00
 *
 */
public class Wind {

	/** Maximální absolutní hodnota, o kterou se mùže zmìnit smìr vìtru */
	private static final double MAX_AZIMUTH_SHIFT = 5;
	/** Maximální absolutní hodnota, o kterou se mùže zmìnit rychlost vìtru */
	private static final double MAX_VELOCITY_SHIFT = 5;

	/** Objekt ze kterého lze získávat pseudonáhodná èísla */
	private Random random;

	/** Smìr vìtru 0 až 2 PI - v radiánech */
	private double azimuth;

	/** Rychlost vìtru */
	private double velocity;

	/** Maximální možná velikost vìtru */
	private double maxVelocity;

	/**
	 * Konstruktor objektu Wind
	 * 
	 * @param maxVelocity
	 *            maximální rychlost vìtru
	 */
	public Wind(double maxVelocity) {
		this.maxVelocity = maxVelocity;

		// Vygenerování poèáteèních dat
		this.random = new Random();
		this.azimuth = 0 + (2.0 - 0) * random.nextDouble();
		this.velocity = 0 + (this.maxVelocity - 0) * random.nextDouble();
	}

	/**
	 * Vygeneruje nové parametry vìtru
	 * 
	 */
	public void generateParams() {
		double velocityDiff = 0 + (Wind.MAX_VELOCITY_SHIFT - (-1 * Wind.MAX_VELOCITY_SHIFT)) * random.nextDouble();
		this.setVelocity(this.velocity + velocityDiff);

		double azimuthDiff = 0 + (Wind.MAX_AZIMUTH_SHIFT - (-1 * Wind.MAX_AZIMUTH_SHIFT)) * random.nextDouble();
		this.setAzimuth(this.azimuth + azimuthDiff);
	}

	/**
	 * Getter pro získání smìru vìtru (radiány)
	 * 
	 * @return smìr vìtru v radiánech
	 */
	public double getAzimuth() {
		return azimuth;
	}

	/**
	 * Setter pro nastavení smìru vìtru (radiány)
	 * 
	 * @param azimuth
	 *            nový smìr vìtru
	 */
	public void setAzimuth(double azimuth) {
		double azimuthFix = azimuth;

		if (azimuthFix >= 2.0)
			azimuthFix = azimuthFix % 2.0;

		this.azimuth = azimuthFix;
	}

	/**
	 * Getter pro získání rychlosti vìtru
	 * 
	 * @return rychlost vìtru
	 */
	public double getVelocity() {
		return velocity;
	}

	/**
	 * Setter pro nastavení rychlosti vìtru
	 * 
	 * @param velocity
	 *            nová rychlost vìtru
	 */
	public void setVelocity(double velocity) {
		double velocityFix = velocity;

		if (velocityFix > maxVelocity)
			velocityFix = maxVelocity;

		if (velocityFix < 0)
			velocityFix = 0;

		this.velocity = velocityFix;
	}

	/**
	 * Getter pro získání maximální velikosti vìtru
	 * 
	 * @return maximální velikost vìtru
	 */
	public double getMaxVelocity() {
		return maxVelocity;
	}

	/**
	 * Setter pro nastavení maximální velikosti vìtru
	 * 
	 * @param maxVelocity
	 *            nová maximální hodnota rychlosti vìtru
	 */
	public void setMaxVelocity(double maxVelocity) {
		this.maxVelocity = maxVelocity;
	}

}
