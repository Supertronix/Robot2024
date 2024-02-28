package frc.robot.commande.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commande.robot.*;
import frc.robot.commande.terrain.CommandeAllerA;
import frc.robot.mesure.Vecteur3;

public class TrajetNoteDansScene extends SequentialCommandGroup {
    public TrajetNoteDansScene()
	{
		addCommands(
			new CommandeAllerA(new Vecteur3(0, 0, 0), 0), // TODO : changer les coordonnées
            new CommandeLanceurOuvrir(),
			// TODO : RECULER X PAS
			new CommandeGrimpageRedescendre(),
			// TODO : AVANCER X PAS
			new CommandeLanceurAllonger(),
			new CommandeGrimper()
			// TODO : commande tirer
		);
	}
}
