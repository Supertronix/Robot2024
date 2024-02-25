package frc.robot.commande.auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commande.robot.CommandeGrimper;
import frc.robot.commande.robot.CommandeLancerSpeaker;
import frc.robot.commande.terrain.CommandeAllerA;
import frc.robot.mesure.Vecteur3;

public class TrajetComplique extends SequentialCommandGroup{
	
	public TrajetComplique()
	{
		
		// EXEMPLE pour la syntaxe comment ajouter des commandes paralleles dans une sequentielle
		ParallelCommandGroup deuxCommandesEnMemeTemps = new ParallelCommandGroup(null);
		deuxCommandesEnMemeTemps.addCommands(new CommandeGrimper(), new CommandeLancerSpeaker());

		addCommands(new CommandeAllerA(new Vecteur3(5,5,5),10), deuxCommandesEnMemeTemps);
	}
	
}
