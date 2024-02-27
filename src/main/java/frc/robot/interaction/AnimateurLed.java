package frc.robot.interaction;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.I2C;
import frc.robot.Materiel;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AnimateurLed implements Materiel.Affichage{

	private final SendableChooser<String> selecteur;
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
        i2c = new I2C(port, 8); 
		//SmartDashboard.putString("Animation", "0");
		selecteur = new SendableChooser<String>();
		selecteur.setDefaultOption("Aucune", "0");
		selecteur.addOption("Waves", "W");
		selecteur.addOption("Damier", "D");
		selecteur.addOption("5910", "5");
		SmartDashboard.putData("Animation", selecteur);	
	}
	public void choisirAnimation(String choix)
	{
        donnee[0] = choix.getBytes()[0];
        i2c.transaction(donnee, donnee.length, dummy, dummy.length);
	}
	public void choisirAnimationSelonDashboard()
	{
		//String choix = SmartDashboard.getString("Animation", "0");
		String choix = selecteur.getSelected();
		System.out.println("Choix " + choix);
        donnee[0] = choix.getBytes()[0];
        i2c.transaction(donnee, donnee.length, dummy, dummy.length);
	}

}

