package cz.zcu.viteja.upg;

public class ShootingCalculator {

	public NamedPosition shooter;
	public NamedPosition target;

	private NamedPosition hitSpot;

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

		System.out.println(
				String.format("sX + posuX = %.5f + %.5f = %.5f)", this.shooter.x, posunX, this.shooter.x + posunX));

		this.hitSpot = new NamedPosition(this.shooter.x + posunX, this.shooter.y - posunY, Constants.HITSPOT,
				Constants.hitspotColor, 60);

	}

	public boolean testTargetHit() {
		if (this.hitSpot.equals(null)) {
			return false;
		}

		return (this.hitSpot.getDistance(target) <= 30);

	}

	public NamedPosition getHitSpot() {
		return this.hitSpot;
	}

}
