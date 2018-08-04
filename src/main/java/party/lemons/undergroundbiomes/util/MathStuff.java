package party.lemons.undergroundbiomes.util;

/**
 * Created by Sam on 4/08/2018.
 */
public class MathStuff
{
	final static double EPSILON = 1e-12;

	/**
	 * Maps values from one range to another
	 * @param valueCoord1
	 * @param startCoord1
	 * @param endCoord1
	 * @param startCoord2
	 * @param endCoord2
	 * @return
	 */
	public static double map(double valueCoord1, double startCoord1, double endCoord1, double startCoord2, double endCoord2)
	{
		if (Math.abs(endCoord1 - startCoord1) < EPSILON)
			throw new ArithmeticException("/ 0");

		double offset = startCoord2;
		double ratio = (endCoord2 - startCoord2) / (endCoord1 - startCoord1);
		return ratio * (valueCoord1 - startCoord1) + offset;
	}
}
