package frc.robot.commande.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commande.robot.CommandeLancerSpeaker;
import frc.robot.commande.terrain.CommandeAllerA;
import frc.robot.commande.terrain.CommandeAllerATest;
import frc.robot.mesure.Vecteur3;

public class TrajetNoteDansSpeaker extends SequentialCommandGroup {
    public TrajetNoteDansSpeaker()
	{
		addCommands(
			new CommandeAllerATest(new Vecteur3(6.90, 1.48, 0), 180),
            new CommandeLancerSpeaker()
		);
	}
}
