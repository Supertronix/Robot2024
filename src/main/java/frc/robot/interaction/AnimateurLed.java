package frc.robot.interaction;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.I2C;
import frc.robot.Materiel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AnimateurLed implements Materiel.Affichage{

	//public enum ANIMATION{VIDE, WAVE, DAMIER, NUMERO_EQUIPE};
	public static String AUCUNE = "0";
	public static String WAVE = "W";
	public static String DAMIER = "D";
	public static String NUMERO_EQUIPE = "5";

    protected final I2C.Port port = I2C.Port.kOnboard;
    protected I2C i2c;
    protected byte[] donnee = new byte[1];
    protected byte[] dummy = new byte[1];

	public AnimateurLed()
	{
        i2c = new I2C(port, 0x1E); // 0x1E est l'adresse du périphérique
	}
	public void choisirAnimation(String choix)
	{
        donnee[0] = choix.getBytes()[0];
        i2c.transaction(donnee, donnee.length, dummy, dummy.length);
	}
	public void choisirAnimationSelonDashboard()
	{
		String choix = SmartDashboard.getString("Animation", "0");
        donnee[0] = choix.getBytes()[0];
        i2c.transaction(donnee, donnee.length, dummy, dummy.length);
	}

}

