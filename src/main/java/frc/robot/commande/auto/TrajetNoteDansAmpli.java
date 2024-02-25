package frc.robot.commande.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commande.robot.CommandeLancerSpeaker;
import frc.robot.commande.terrain.CommandeAllerA;
import frc.robot.mesure.Vecteur3;

public class TrajetNoteDansAmpli extends SequentialCommandGroup{
	
	public TrajetNoteDansAmpli()
	{
		addCommands(new CommandeAllerA(new Vecteur3(5,5,5),10),new CommandeLancerSpeaker());
	}
	
}
