package support;

import java.util.Random;

public class Gremlin {
	private static final float PROBABILITY_OF_DAMAGE_1 = (float) 0.5;
	private static final float PROBABILITY_OF_DAMAGE_2 = (float) 0.3 + PROBABILITY_OF_DAMAGE_1;
	private static final float PROBABILITY_OF_DAMAGE_3 = (float) 0.2 + PROBABILITY_OF_DAMAGE_2;

	private float probabilityOfDamage = 0;

	private Random random;

	public Gremlin(float probabilityOfDamage) throws Exception {
		if (probabilityOfDamage > 1 || probabilityOfDamage < 0) {
			throw new Exception("Invalid probability");
		}

		this.probabilityOfDamage = probabilityOfDamage;

		random = new Random();
	}

	public boolean damagePacket(byte[] input) {
		if (random.nextFloat() >= probabilityOfDamage) {
			return false;
		}

		float packetDamage = random.nextFloat();
		byte damageCount;

		if (packetDamage <= PROBABILITY_OF_DAMAGE_1) {
			damageCount = 1;
		}
		else if (packetDamage <= PROBABILITY_OF_DAMAGE_2) {
			damageCount = 2;
		}
		else if (packetDamage <= PROBABILITY_OF_DAMAGE_3) {
			damageCount = 3;
		}
		else {
			return false;
		}

		for (int i = 0; i < damageCount; i++) {
			int index = random.nextInt(input.length);

			input[index] = (byte) (input[index] * (random.nextFloat() > 0.5 ? 2.0 : 0.5));
		}

		return true;
	}
}
