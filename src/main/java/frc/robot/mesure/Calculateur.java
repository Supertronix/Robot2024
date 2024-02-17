package frc.robot.mesure;

public class Calculateur {
	
	/** 
	 * @param val
	 * @param min
	 * @param max
	 * @return double
	 */
	public static double clamp(double val, double min, double max) 
	{
	    return Math.max(min, Math.min(max, val));
	}

}