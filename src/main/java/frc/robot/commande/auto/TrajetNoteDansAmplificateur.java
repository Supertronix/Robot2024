package frc.robot.commande.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commande.robot.CommandeLanceurAllonger;
import frc.robot.commande.robot.CommandeLanceurOuvrir;
import frc.robot.commande.terrain.CommandeAllerA;
import frc.robot.commande.terrain.CommandeAllerATest;
import frc.robot.mesure.Vecteur3;

public class TrajetNoteDansAmplificateur extends SequentialCommandGroup {
    public TrajetNoteDansAmplificateur()
	{
		// 6.390382310264613 : 2.6388704660591458 : -91.35970102439848 
		addCommands(
			new CommandeAllerA(new Vecteur3(6.4, 2.7, 0), -90),
            new CommandeLanceurOuvrir().andThen(new CommandeLanceurAllonger())
		);
	}
}
